package com.offlinepayment.payment_relay.service;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        if ("admin".equals(username)) {
            return new User(
                    "admin",
                    "{noop}admin123",
                    Collections.emptyList()
            );
        }

        throw new UsernameNotFoundException("User not found");
    }
}