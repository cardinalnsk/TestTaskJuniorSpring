package ru.cardinalnsk.springjavajuniortest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.cardinalnsk.springjavajuniortest.domain.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
