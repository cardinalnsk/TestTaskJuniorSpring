package ru.cardinalnsk.springjavajuniortest.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.cardinalnsk.springjavajuniortest.controller.payload.request.PayPhoneDto;
import ru.cardinalnsk.springjavajuniortest.controller.payload.response.PayResultDto;
import ru.cardinalnsk.springjavajuniortest.controller.payload.response.PaymentDto;
import ru.cardinalnsk.springjavajuniortest.domain.UserAccount;
import ru.cardinalnsk.springjavajuniortest.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    UserRepository userRepository;
    @Mock
    Principal principal;

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Test
    void curentBalanceByAuthorizedUser() {
        PaymentDto expected = new PaymentDto("12345", BigDecimal.TEN);
        UserAccount user = UserAccount.builder()
            .balance(BigDecimal.TEN)
            .phoneNumber("12345")
            .build();

        Optional<UserAccount> optionalUser = Optional.of(user);
        when(principal.getName()).thenReturn("12345");
        when(userRepository.findUserByPhoneNumber(principal.getName())).thenReturn(optionalUser);
        PaymentDto actual = paymentService.currentBalanceByAuthorizedUser(principal);

        assertNotNull(actual);
        assertEquals(expected, actual);

    }


    @Test
    void payPhone() {
        PayPhoneDto payPhoneDto = new PayPhoneDto("12345", 1.0);
        UserAccount paymentReceiver = UserAccount.builder()
            .balance(BigDecimal.TEN)
            .phoneNumber("12345")
            .build();

        UserAccount paymentSender = UserAccount.builder()
            .balance(BigDecimal.TEN)
            .phoneNumber("54321")
            .build();

        Optional<UserAccount> optionalSender = Optional.of(paymentSender);
        Optional<UserAccount> optionalReceiver = Optional.of(paymentReceiver);
        when(principal.getName()).thenReturn("54321");
        when(userRepository.findUserByPhoneNumber(paymentReceiver.getPhoneNumber())).thenReturn(
            optionalReceiver);
        when(userRepository.findUserByPhoneNumber(paymentSender.getPhoneNumber())).thenReturn(
            optionalSender);
        PayResultDto actual = paymentService.payPhone(payPhoneDto, principal);
        PayResultDto expected = new PayResultDto(
            "Оплата на номер %s прошла успешно, ваш текущий баланс %.2f".formatted(
                paymentReceiver.getPhoneNumber(), paymentSender.getBalance()));

        assertNotNull(actual);
        assertEquals(expected, actual);
        assertEquals(BigDecimal.valueOf(9), paymentSender.getBalance());
        assertEquals(BigDecimal.valueOf(11), paymentReceiver.getBalance());

    }

    @Test
    void payPhoneThenNotEnoughBalance() {
        PayPhoneDto payPhoneDto = new PayPhoneDto("12345", 100.0);
        UserAccount paymentReceiver = UserAccount.builder()
            .balance(BigDecimal.TEN)
            .phoneNumber("12345")
            .build();

        UserAccount paymentSender = UserAccount.builder()
            .balance(BigDecimal.TEN)
            .phoneNumber("54321")
            .build();

        Optional<UserAccount> optionalSender = Optional.of(paymentSender);
        Optional<UserAccount> optionalReceiver = Optional.of(paymentReceiver);
        when(principal.getName()).thenReturn("54321");
        when(userRepository.findUserByPhoneNumber(paymentReceiver.getPhoneNumber())).thenReturn(
            optionalReceiver);
        when(userRepository.findUserByPhoneNumber(paymentSender.getPhoneNumber())).thenReturn(
            optionalSender);
        PayResultDto actual = paymentService.payPhone(payPhoneDto, principal);
        PayResultDto expected = new PayResultDto("Недостаточно средств");

        assertNotNull(actual);
        assertEquals(expected, actual);
        assertEquals(BigDecimal.valueOf(10), paymentSender.getBalance());
        assertEquals(BigDecimal.valueOf(10), paymentReceiver.getBalance());

    }


}
