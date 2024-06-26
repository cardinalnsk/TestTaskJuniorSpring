package ru.cardinalnsk.springjavajuniortest.controller.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.ToString;
import lombok.ToString.Exclude;
import lombok.Value;

@ToString
@Value
public class LoginDto{
    @NotBlank
    @Pattern(regexp = "^(\\+7)(\\d{10})$", message = "Invalid phone number format, pattern '+71231231212'")
    @JsonProperty("phoneNumber")
    String phoneNumber;
    @JsonProperty("password")
    @Exclude
    String password;

}
