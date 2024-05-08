package ru.cardinalnsk.springjavajuniortest.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.cardinalnsk.springjavajuniortest.controller.payload.request.PayPhoneDto;
import ru.cardinalnsk.springjavajuniortest.controller.payload.response.PayResultDto;
import ru.cardinalnsk.springjavajuniortest.controller.payload.response.PaymentDto;
import ru.cardinalnsk.springjavajuniortest.domain.Payment;
import ru.cardinalnsk.springjavajuniortest.domain.UserAccount;
import ru.cardinalnsk.springjavajuniortest.repository.PaymentRepository;
import ru.cardinalnsk.springjavajuniortest.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    UserRepository userRepository;
    @Mock
    Principal principal;
    @Mock
    PaymentRepository paymentRepository;

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
            .paymentHistory(new ArrayList<>())
            .balance(BigDecimal.TEN)
            .phoneNumber("54321")
            .build();

        Payment payment = Payment.builder()
            .amount(BigDecimal.TEN)
            .phoneNumber("12345")
            .build();


        Optional<UserAccount> optionalSender = Optional.of(paymentSender);
        Optional<UserAccount> optionalReceiver = Optional.of(paymentReceiver);
        when(principal.getName()).thenReturn("54321");
        when(userRepository.findUserByPhoneNumber(paymentReceiver.getPhoneNumber())).thenReturn(
            optionalReceiver);
        when(userRepository.findUserByPhoneNumber(paymentSender.getPhoneNumber())).thenReturn(
            optionalSender);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));
        PayResultDto actual = paymentService.payPhone(payPhoneDto, principal);
        PayResultDto expected = new PayResultDto(
            "Оплата на номер %s прошла успешно, ваш текущий баланс %.2f".formatted(
                paymentReceiver.getPhoneNumber(), paymentSender.getBalance()));
        verify(userRepository, times(1)).save(paymentSender);
        verify(userRepository, times(1)).save(paymentReceiver);

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

        verify(userRepository, times(0)).save(paymentSender);
        assertNotNull(actual);
        assertEquals(expected, actual);
        assertEquals(BigDecimal.valueOf(10), paymentSender.getBalance());
        assertEquals(BigDecimal.valueOf(10), paymentReceiver.getBalance());

    }


}
