package ru.cardinalnsk.springjavajuniortest.service.impl;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.cardinalnsk.springjavajuniortest.domain.UserRole;
import ru.cardinalnsk.springjavajuniortest.repository.UserRoleRepository;
import ru.cardinalnsk.springjavajuniortest.service.UserRoleService;

@Service
@AllArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    @Override
    public Optional<UserRole> findByAuthority(String authority) {
        return userRoleRepository.findByAuthority(authority);
    }

    @Override
    public Optional<UserRole> findUserRole() {
        return findByAuthority("ROLE_USER");
    }
}
