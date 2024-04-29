package ru.cardinalnsk.springjavajuniortest.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.cardinalnsk.springjavajuniortest.controller.payload.request.RegistrationDto;
import ru.cardinalnsk.springjavajuniortest.service.UserService;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class AuthorizationController {

    private final UserService userService;

    @PostMapping("registration")
    public ResponseEntity<?> registration(@RequestBody RegistrationDto registrationDto) {
        return ResponseEntity
            .ok(userService.registration(registrationDto));
    }




}
