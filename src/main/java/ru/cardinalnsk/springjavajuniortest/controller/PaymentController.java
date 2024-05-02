package ru.cardinalnsk.springjavajuniortest.controller;

import java.security.Principal;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/current-balance")
    public ResponseEntity<?> currentBalance(Principal principal) {
        return ResponseEntity
            .ok(paymentService.currentBalanceByAuthorizedUser(principal));
    }

    @PostMapping("/pay-phone")
    public ResponseEntity<?> payPhone(@RequestBody PayPhoneDto payPhoneDto, Principal principal) {
        return ResponseEntity
            .ok(paymentService.payPhone(payPhoneDto, principal));
    }

    @GetMapping("/history")
    public ResponseEntity<?> getHistory(Pageable pageable, Principal principal) {
        return ResponseEntity
            .ok(paymentService.getHistory(pageable, principal));
    }
}
