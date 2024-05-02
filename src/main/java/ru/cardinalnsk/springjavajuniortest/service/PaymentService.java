package ru.cardinalnsk.springjavajuniortest.service;

import java.security.Principal;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.cardinalnsk.springjavajuniortest.controller.payload.request.PayPhoneDto;
import ru.cardinalnsk.springjavajuniortest.controller.payload.response.PaymentDto;
import ru.cardinalnsk.springjavajuniortest.controller.payload.response.PayResultDto;
import ru.cardinalnsk.springjavajuniortest.domain.Payment;

public interface PaymentService {

    PaymentDto currentBalanceByAuthorizedUser(Principal principal);

    PayResultDto payPhone(PayPhoneDto payPhoneDto, Principal principal);

    PageImpl<Payment> getHistory(Pageable pageable, Principal principal);
}
