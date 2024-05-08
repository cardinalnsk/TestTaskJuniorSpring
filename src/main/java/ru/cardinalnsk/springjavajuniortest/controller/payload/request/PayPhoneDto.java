package ru.cardinalnsk.springjavajuniortest.controller.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

public record PayPhoneDto(
    @Pattern(regexp = "^(\\+7)(\\d{10})$", message = "Invalid phone number format, pattern '+71231231212'")
    @JsonProperty("phoneNumber")
    String phoneNumber,
    @PositiveOrZero
    Double amount
) {

}
