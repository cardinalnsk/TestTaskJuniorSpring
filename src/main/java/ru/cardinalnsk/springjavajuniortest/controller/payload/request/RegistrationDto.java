package ru.cardinalnsk.springjavajuniortest.controller.payload.request;

public record RegistrationDto(
    String firstName,
    String phoneNumber,
    String password
) {


}
