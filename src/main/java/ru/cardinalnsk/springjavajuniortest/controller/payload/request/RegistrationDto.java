package ru.cardinalnsk.springjavajuniortest.controller.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegistrationDto(
    @JsonProperty("username")
    String username,
    @NotBlank
    @Pattern(regexp = "^((\\+7)[\\- ]?)(\\(?\\d{3}\\)?[\\- ]?)[\\d\\- ]{7}$", message = "Invalid phone number format, pattern '+71231231212'")
    @JsonProperty("phoneNumber")
    String phoneNumber,
    @Size(min = 6, max = 20, message = "Invalid password length 6-20 characters")
    @JsonProperty("password")
    String password
) {


}
