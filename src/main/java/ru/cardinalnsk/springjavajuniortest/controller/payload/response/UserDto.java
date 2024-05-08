package ru.cardinalnsk.springjavajuniortest.controller.payload.response;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record UserDto(
    String firstName,
    String lastName,
    String email,
    String gender,
    LocalDate birthDate
) {

}
