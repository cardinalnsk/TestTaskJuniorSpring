package ru.cardinalnsk.springjavajuniortest.service;

import java.util.Optional;
import ru.cardinalnsk.springjavajuniortest.controller.payload.request.RegistrationDto;
import ru.cardinalnsk.springjavajuniortest.controller.payload.response.UserDto;
import ru.cardinalnsk.springjavajuniortest.domain.UserAccount;

public interface UserService {

    UserDto registration(RegistrationDto registrationDto);

    Optional<UserAccount> findByPhoneNumber(String phoneNumber);

}
