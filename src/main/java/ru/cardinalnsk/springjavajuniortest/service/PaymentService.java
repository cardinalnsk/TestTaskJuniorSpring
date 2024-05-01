package ru.cardinalnsk.springjavajuniortest.service;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.cardinalnsk.springjavajuniortest.controller.payload.request.PayPhoneDto;
import ru.cardinalnsk.springjavajuniortest.controller.payload.response.PaymentDto;
import ru.cardinalnsk.springjavajuniortest.controller.payload.response.PayResultDto;
import ru.cardinalnsk.springjavajuniortest.domain.Payment;

public interface PaymentService {

    PaymentDto currentBalanceByAuthorityUser();

    PayResultDto payPhone(PayPhoneDto payPhoneDto);

    PageImpl<Payment> getHistory(Pageable pageable);
}
