package ru.cardinalnsk.springjavajuniortest.controller.payload.response;


import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationResponseDto {

    private Long userId;
    private String phoneNumber;
    private BigDecimal balance;
    private String token;

}
