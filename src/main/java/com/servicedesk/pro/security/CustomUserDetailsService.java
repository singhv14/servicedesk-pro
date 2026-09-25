package com.servicedesk.pro.security;

import com.servicedesk.pro.entity.User;
import com.servicedesk.pro.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        Optional<User> byEmail = userRepository.findByEmail(username);

        if (byEmail.isPresent()) {

            User user = byEmail.get();
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getEmail())
                    .password(user.getPassword())
                    .build();

        }else  {

            throw new UsernameNotFoundException(username);

        }
    }
}
