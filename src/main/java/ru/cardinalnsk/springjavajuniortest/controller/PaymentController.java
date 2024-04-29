package ru.cardinalnsk.springjavajuniortest.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.cardinalnsk.springjavajuniortest.controller.payload.request.PayPhoneDto;
import ru.cardinalnsk.springjavajuniortest.service.PaymentService;

@RestController
@RequestMapping("/api/payment")
@AllArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/current-balance")
    public ResponseEntity<?> currentBalance() {
        return ResponseEntity
            .ok(paymentService.currentBalanceByAuthorityUser());
    }

    @PostMapping("/pay-phone")
    public ResponseEntity<?> payPhone(@RequestBody PayPhoneDto payPhoneDto) {
        return ResponseEntity
            .ok(paymentService.payPhone(payPhoneDto));
    }
}
