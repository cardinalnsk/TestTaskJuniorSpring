package ru.cardinalnsk.springjavajuniortest.service.impl;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.cardinalnsk.springjavajuniortest.controller.payload.request.RegistrationDto;
import ru.cardinalnsk.springjavajuniortest.controller.payload.response.RegistrationResponseDto;
import ru.cardinalnsk.springjavajuniortest.controller.payload.response.UserDto;
import ru.cardinalnsk.springjavajuniortest.domain.Gender;
import ru.cardinalnsk.springjavajuniortest.domain.UserAccount;
import ru.cardinalnsk.springjavajuniortest.domain.UserRole;
import ru.cardinalnsk.springjavajuniortest.exception.UserAlreadyExistException;
import ru.cardinalnsk.springjavajuniortest.repository.UserRepository;
import ru.cardinalnsk.springjavajuniortest.service.UserRoleService;
import ru.cardinalnsk.springjavajuniortest.service.UserService;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passEncoder;

    @Override
    public RegistrationResponseDto registration(RegistrationDto registrationDto) {
        UserRole userRole = userRoleService.findUserRole()
            .orElseThrow(() -> new RuntimeException("User role not found"));

        Optional<UserAccount> userAccountOptional = findByPhoneNumber(
            registrationDto.phoneNumber());

        if (userAccountOptional.isPresent()) {
            String message = "User with phone number: %s already exists";
            log.error(message.formatted(registrationDto.phoneNumber()));
            throw new UserAlreadyExistException(message.formatted(registrationDto.phoneNumber()));
        }

        UserAccount userAccount = UserAccount.builder()
            .balance(BigDecimal.valueOf(1000))
            .username(registrationDto.username())
            .phoneNumber(registrationDto.phoneNumber())
            .password(passEncoder.encode(registrationDto.password()))
            .role(Set.of(userRole))
            .build();

        userAccount = userRepository.save(userAccount);

        String token = getAuthorizationToken(registrationDto);

        return RegistrationResponseDto.builder()
            .userId(userAccount.getId())
            .balance(userAccount.getBalance())
            .phoneNumber(userAccount.getPhoneNumber())
            .token(token)
            .build();
    }

    private String getAuthorizationToken(RegistrationDto registrationDto) {
        byte[] loginPassword = "%s:%s".formatted(registrationDto.phoneNumber(),
            registrationDto.password()).getBytes();
        return "Basic %s".formatted(Base64.getEncoder().encodeToString(loginPassword));
    }

    @Override
    public Optional<UserAccount> findByPhoneNumber(String phoneNumber) {
        return userRepository.findUserByPhoneNumber(phoneNumber);
    }

    @Override
    public UserDto getMyAccount() {
        return findUserAccountByAuthorize()
            .map(this::mapUserAccountToUserDto)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }


    private Optional<UserAccount> findUserAccountByAuthorize() {
        String phoneNumber = ((User) SecurityContextHolder.getContext().getAuthentication()
            .getPrincipal()).getUsername();
        return userRepository.findUserByPhoneNumber(phoneNumber);
    }

    @Override
    public UserDto updateMyAccount(UserDto userDto) {
        UserAccount userAccount = findUserAccountByAuthorize()
            .map(usr -> updateUserAccount(userDto, usr))
            .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.save(userAccount);
        return mapUserAccountToUserDto(userAccount);
    }

    private UserAccount updateUserAccount(UserDto userDto, UserAccount user) {
        return user.toBuilder()
            .email(userDto.email())
            .gender(Gender.valueOf(userDto.gender()))
            .firstName(userDto.firstName())
            .lastName(userDto.lastName())
            .birthDate(userDto.birthday())
            .build();
    }


    private UserDto mapUserAccountToUserDto(UserAccount usr) {
        return UserDto.builder()
            .email(usr.getEmail())
            .birthday(usr.getBirthDate())
            .gender(usr.getGender() != null ? usr.getGender().name() : null)
            .lastName(usr.getLastName())
            .firstName(usr.getFirstName())
            .build();
    }
}
