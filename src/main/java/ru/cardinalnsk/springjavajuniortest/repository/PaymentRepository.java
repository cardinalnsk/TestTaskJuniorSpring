package ru.cardinalnsk.springjavajuniortest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.cardinalnsk.springjavajuniortest.domain.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query(
        value = """
        SELECT * FROM payment p
        JOIN user_entity_payment_history ua
            ON ua.user_entity_id = :userId
                   AND ua.payment_history_id = p.id
        """,
        nativeQuery = true
    )
    Page<Payment> findPaymentHistoryByUserId(Long userId, Pageable pageable);

}
