package ru.cardinalnsk.springjavajuniortest.controller.payload.request;

public record PayPhoneDto(
    String phoneNumber,
    Double amount
) {

}
