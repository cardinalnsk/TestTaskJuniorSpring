package ru.cardinalnsk.springjavajuniortest.service.impl;

import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.security.Principal;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.cardinalnsk.springjavajuniortest.controller.payload.request.PayPhoneDto;
import ru.cardinalnsk.springjavajuniortest.controller.payload.response.PayResultDto;
import ru.cardinalnsk.springjavajuniortest.controller.payload.response.PaymentDto;
import ru.cardinalnsk.springjavajuniortest.domain.Payment;
import ru.cardinalnsk.springjavajuniortest.domain.UserAccount;
import ru.cardinalnsk.springjavajuniortest.repository.UserRepository;
import ru.cardinalnsk.springjavajuniortest.service.PaymentService;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final UserRepository userRepository;

    @Override
    public PaymentDto currentBalanceByAuthorizedUser(Principal principal) {
        UserAccount user = getAuthorizationUser(principal);
        return new PaymentDto(user.getPhoneNumber(), user.getBalance());
    }

    private UserAccount getAuthorizationUser(Principal principal) {
        return userRepository.findUserByPhoneNumber(principal.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    @Transactional
    public PayResultDto payPhone(PayPhoneDto payPhoneDto, Principal principal) {
        String phoneNumber = payPhoneDto.phoneNumber();
        Double amount = payPhoneDto.amount();

        UserAccount paymentReceiver = userRepository.findUserByPhoneNumber(phoneNumber)
            .orElseThrow(() -> new RuntimeException("User not found"));

        UserAccount paymentSender = getAuthorizationUser(principal);

        if (paymentSender.getBalance().doubleValue() - amount < 0) {
            return new PayResultDto("Недостаточно средств");
        }

        paymentSender.setBalance(new BigDecimal(paymentSender.getBalance().doubleValue() - amount));
        userRepository.save(paymentSender);

        paymentReceiver.setBalance(
            new BigDecimal(paymentReceiver.getBalance().doubleValue() + amount));
        userRepository.save(paymentReceiver);

        String message = "Оплата на номер %s прошла успешно, ваш текущий баланс %.2f";
        return new PayResultDto(
            message.formatted(phoneNumber, paymentSender.getBalance().doubleValue()));
    }

    @Override
    public PageImpl<Payment> getHistory(Pageable pageable, Principal principal) {
        UserAccount user = getAuthorizationUser(principal);
        return new PageImpl<>(user.getPaymentHistory());

    }

}
