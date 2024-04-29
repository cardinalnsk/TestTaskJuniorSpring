package ru.cardinalnsk.springjavajuniortest.service;

import ru.cardinalnsk.springjavajuniortest.controller.payload.request.PayPhoneDto;
import ru.cardinalnsk.springjavajuniortest.controller.payload.response.PaymentDto;
import ru.cardinalnsk.springjavajuniortest.controller.payload.response.PayResultDto;

public interface PaymentService {

    PaymentDto currentBalanceByAuthorityUser();

    PayResultDto payPhone(PayPhoneDto payPhoneDto);
}
