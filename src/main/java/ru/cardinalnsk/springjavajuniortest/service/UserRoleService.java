package ru.cardinalnsk.springjavajuniortest.service;

import java.util.Optional;
import ru.cardinalnsk.springjavajuniortest.domain.UserRole;

public interface UserRoleService {

    Optional<UserRole> findByAuthority(String authority);

    Optional<UserRole> findUserRole();
}
