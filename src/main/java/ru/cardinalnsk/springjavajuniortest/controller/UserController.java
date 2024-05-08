package ru.cardinalnsk.springjavajuniortest.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.cardinalnsk.springjavajuniortest.aop.Loggable;
import ru.cardinalnsk.springjavajuniortest.controller.payload.response.UserDto;
import ru.cardinalnsk.springjavajuniortest.service.UserService;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
@Loggable
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDto> getMyAccount() {
        return ResponseEntity
            .ok(userService.getMyAccount());
    }


    @PutMapping
    public ResponseEntity<UserDto> updateMyAccount(@RequestBody UserDto userDto) {
        return ResponseEntity
            .ok(userService.updateMyAccount(userDto));
    }

}
