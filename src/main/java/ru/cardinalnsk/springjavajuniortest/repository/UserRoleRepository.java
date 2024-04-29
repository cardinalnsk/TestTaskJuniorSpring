package ru.cardinalnsk.springjavajuniortest.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.cardinalnsk.springjavajuniortest.domain.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {


    Optional<UserRole> findByAuthority(String authority);

}
