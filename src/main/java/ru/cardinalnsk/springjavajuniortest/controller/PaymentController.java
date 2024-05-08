package ru.cardinalnsk.springjavajuniortest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.security.Principal;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.cardinalnsk.springjavajuniortest.aop.Loggable;
import ru.cardinalnsk.springjavajuniortest.controller.payload.request.PayPhoneDto;
import ru.cardinalnsk.springjavajuniortest.controller.payload.response.PayResultDto;
import ru.cardinalnsk.springjavajuniortest.controller.payload.response.PaymentDto;
import ru.cardinalnsk.springjavajuniortest.domain.Payment;
import ru.cardinalnsk.springjavajuniortest.service.PaymentService;

@RestController
@RequestMapping("/api/payment")
@AllArgsConstructor
@Tag(name = "Payment")
@Loggable
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(
        summary = "Get current balance by authorized user",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                useReturnTypeSchema = true
            )
        },
        security = @SecurityRequirement(name = "Basic")
    )
    @GetMapping("/current-balance")
    public ResponseEntity<PaymentDto> currentBalance(Principal principal) {
        return ResponseEntity
            .ok(paymentService.currentBalanceByAuthorizedUser(principal));
    }


    @Operation(
        summary = "Pay phone",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                useReturnTypeSchema = true
            )
        },
        security = @SecurityRequirement(name = "Basic")
    )
    @PostMapping("/pay-phone")
    public ResponseEntity<PayResultDto> payPhone(
        @Valid @RequestBody PayPhoneDto payPhoneDto,
        Principal principal) {
        return ResponseEntity
            .ok(paymentService.payPhone(payPhoneDto, principal));
    }

    @Operation(
        summary = "Get payment history",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                useReturnTypeSchema = true
            )
        },
        security = @SecurityRequirement(name = "Basic")
    )
    @GetMapping("/history")
    public ResponseEntity<Page<Payment>> getHistory(
        @ParameterObject Pageable pageable,
        Principal principal) {
        return ResponseEntity
            .ok(paymentService.getHistory(pageable, principal));
    }
}
