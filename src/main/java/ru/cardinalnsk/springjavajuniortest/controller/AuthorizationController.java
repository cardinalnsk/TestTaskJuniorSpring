package ru.cardinalnsk.springjavajuniortest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.cardinalnsk.springjavajuniortest.controller.payload.request.LoginDto;
import ru.cardinalnsk.springjavajuniortest.controller.payload.request.RegistrationDto;
import ru.cardinalnsk.springjavajuniortest.controller.payload.response.AccessTokenDto;
import ru.cardinalnsk.springjavajuniortest.controller.payload.response.RegistrationResponseDto;
import ru.cardinalnsk.springjavajuniortest.service.UserService;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
@Tag(name = "AUTH")
public class AuthorizationController {

    private final UserService userService;


    @Operation(
        summary = "Registration new user",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Registration new user",
                useReturnTypeSchema = true
            )
        }
    )
    @PostMapping("registration")
    public ResponseEntity<RegistrationResponseDto> registration(
        @RequestBody
        @Valid
        RegistrationDto registrationDto) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(userService.registration(registrationDto));
    }


    @Operation(
        summary = "Login",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Login",
                useReturnTypeSchema = true
            )
        }
    )
    @PostMapping("login/phone")
    public ResponseEntity<AccessTokenDto> login(
        @RequestBody
        @Valid
        LoginDto loginDto) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(userService.login(loginDto));
    }



}
