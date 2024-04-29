package ru.cardinalnsk.springjavajuniortest.service.impl;

import java.math.BigDecimal;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import ru.cardinalnsk.springjavajuniortest.controller.payload.request.PayPhoneDto;
import ru.cardinalnsk.springjavajuniortest.controller.payload.response.PayResultDto;
import ru.cardinalnsk.springjavajuniortest.controller.payload.response.PaymentDto;
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
        String phoneNumber = ((User) SecurityContextHolder.getContext().getAuthentication()
            .getPrincipal()).getUsername();

        UserAccount userNotFound = userRepository.findUserByPhoneNumber(phoneNumber)
            .orElseThrow(() -> new RuntimeException("User not found"));

        return new PaymentDto(userNotFound.getPhoneNumber(), userNotFound.getBalance());
    }

    @Override
    public PayResultDto payPhone(PayPhoneDto payPhoneDto) {
        String phoneNumber = payPhoneDto.phoneNumber();
        Double amount = payPhoneDto.amount();
        return userRepository
            .findUserByPhoneNumber(phoneNumber)
            .map(usr -> {
                if (usr.getBalance().subtract(BigDecimal.valueOf(amount)).doubleValue() < amount) {
                    return new PayResultDto("Недостаточно средств");
                }
                usr.setBalance(usr.getBalance().subtract(BigDecimal.valueOf(amount)));
                userRepository.save(usr);
                String message = "Оплата на номер %s прошла успешно, текущий баланс %f";
                return new PayResultDto(message.formatted(phoneNumber, usr.getBalance().doubleValue()));
            }).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
