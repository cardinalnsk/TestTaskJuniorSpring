package ru.cardinalnsk.springjavajuniortest.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.cardinalnsk.springjavajuniortest.domain.UserAccount;

@Repository
public interface UserRepository extends JpaRepository<UserAccount, Long> {


    Optional<UserAccount> findUserByPhoneNumber(String phoneNumber);

}
