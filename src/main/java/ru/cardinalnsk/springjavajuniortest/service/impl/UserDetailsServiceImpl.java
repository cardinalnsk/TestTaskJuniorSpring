package ru.cardinalnsk.springjavajuniortest.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.cardinalnsk.springjavajuniortest.domain.UserAccount;
import ru.cardinalnsk.springjavajuniortest.service.UserService;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findByPhoneNumber(username)
            .map(this::map)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


    public User map(UserAccount userAccount) {
        return new User(
            userAccount.getPhoneNumber(),
            userAccount.getPassword(),
            userAccount.getRole());
    }
}
