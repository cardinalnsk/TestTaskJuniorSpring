package ru.cardinalnsk.springjavajuniortest.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.cardinalnsk.springjavajuniortest.mapper.UserAccountToUserMapper;
import ru.cardinalnsk.springjavajuniortest.service.UserService;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;
    private final UserAccountToUserMapper userAccountToUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findByPhoneNumber(username)
            .map(userAccountToUserMapper::toUser)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
