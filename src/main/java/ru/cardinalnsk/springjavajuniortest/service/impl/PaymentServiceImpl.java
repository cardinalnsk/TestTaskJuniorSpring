package ru.cardinalnsk.springjavajuniortest.service.impl;

import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import ru.cardinalnsk.springjavajuniortest.controller.payload.request.PayPhoneDto;
import ru.cardinalnsk.springjavajuniortest.controller.payload.response.PayResultDto;
import ru.cardinalnsk.springjavajuniortest.controller.payload.response.PaymentDto;
import ru.cardinalnsk.springjavajuniortest.domain.Payment;
import ru.cardinalnsk.springjavajuniortest.domain.UserAccount;
import ru.cardinalnsk.springjavajuniortest.repository.PaymentRepository;
import ru.cardinalnsk.springjavajuniortest.repository.UserRepository;
import ru.cardinalnsk.springjavajuniortest.service.PaymentService;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    @Override
    public PaymentDto currentBalanceByAuthorityUser() {
        UserAccount user = getAuthorizationUser();

        return new PaymentDto(user.getPhoneNumber(), user.getBalance());
    }

    private UserAccount getAuthorizationUser() {
        String phoneNumber = ((User) SecurityContextHolder.getContext().getAuthentication()
            .getPrincipal()).getUsername();

        return userRepository.findUserByPhoneNumber(phoneNumber)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    @Transactional
    public PayResultDto payPhone(PayPhoneDto payPhoneDto) {
        String phoneNumber = payPhoneDto.phoneNumber();
        Double amount = payPhoneDto.amount();
        return userRepository
            .findUserByPhoneNumber(phoneNumber)
            .map(usr -> {
                BigDecimal subtract = usr.getBalance().subtract(BigDecimal.valueOf(amount));
                if (subtract.doubleValue() < amount) {
                    return new PayResultDto("Недостаточно средств");
                }
                usr.setBalance(subtract);
                Payment savedPayment = paymentRepository.save(getPayment(amount, phoneNumber));
                usr.getPaymentHistory().add(savedPayment);
                userRepository.save(usr);
                String message = "Оплата на номер %s прошла успешно, текущий баланс %.2f";
                return new PayResultDto(
                    message.formatted(phoneNumber, usr.getBalance().doubleValue()));
            })
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public PageImpl<Payment> getHistory(Pageable pageable) {
        UserAccount user = getAuthorizationUser();
        return new PageImpl<>(user.getPaymentHistory());

    }

    private Payment getPayment(Double amount, String phoneNumber) {
        Payment payment = new Payment();
        payment.setBalance(BigDecimal.valueOf(amount));
        payment.setPhoneNumber(phoneNumber);
        payment.setDate(LocalDateTime.now());
        return payment;
    }
}
