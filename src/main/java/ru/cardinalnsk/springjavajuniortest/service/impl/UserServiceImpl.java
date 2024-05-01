package ru.cardinalnsk.springjavajuniortest.service.impl;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
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
import ru.cardinalnsk.springjavajuniortest.repository.UserRepository;
import ru.cardinalnsk.springjavajuniortest.service.UserRoleService;
import ru.cardinalnsk.springjavajuniortest.service.UserService;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passEncoder;

    @Override
    public RegistrationResponseDto registration(RegistrationDto registrationDto) {
        UserRole userRole = userRoleService.findUserRole()
            .orElseThrow(() -> new RuntimeException("User role not found"));

        UserAccount user = UserAccount.builder()
            .balance(BigDecimal.valueOf(1000))
            .username(registrationDto.firstName())
            .phoneNumber(registrationDto.phoneNumber())
            .password(passEncoder.encode(registrationDto.password()))
            .role(Set.of(userRole))
            .build();

        UserAccount savedUser = userRepository.save(user);

        String token = getAuthorizationToken(registrationDto);

        return RegistrationResponseDto.builder()
            .userId(savedUser.getId())
            .balance(savedUser.getBalance())
            .phoneNumber(savedUser.getPhoneNumber())
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
        user.setEmail(userDto.email());
        user.setGender(Gender.valueOf(userDto.gender()));
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setBirthDate(userDto.birthday());
        return user;
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
