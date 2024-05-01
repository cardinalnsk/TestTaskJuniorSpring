package ru.cardinalnsk.springjavajuniortest.service;

import java.util.Optional;
import ru.cardinalnsk.springjavajuniortest.controller.payload.request.RegistrationDto;
import ru.cardinalnsk.springjavajuniortest.controller.payload.response.RegistrationResponseDto;
import ru.cardinalnsk.springjavajuniortest.controller.payload.response.UserDto;
import ru.cardinalnsk.springjavajuniortest.domain.UserAccount;

public interface UserService {

    RegistrationResponseDto registration(RegistrationDto registrationDto);

    Optional<UserAccount> findByPhoneNumber(String phoneNumber);

    UserDto getMyAccount();

    UserDto updateMyAccount(UserDto userDto);
}
