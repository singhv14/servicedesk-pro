package com.servicedesk.pro.service;

import com.servicedesk.pro.dto.RegisterRequest;
import com.servicedesk.pro.entity.User;
import com.servicedesk.pro.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegistrationService registrationService;


    @Test
    void registerShouldStoreEncodedPassword() {

        RegisterRequest request = new RegisterRequest();
        request.setName("John");
        request.setEmail("john@example.com");
        request.setPassword("MyPassword123");

        when(userRepository.existsByEmail("john@example.com"))
                .thenReturn(false);

        when(passwordEncoder.encode("MyPassword123"))
                .thenReturn("encoded-password");

        User savedUser = new User();
        savedUser.setName("John");
        savedUser.setEmail("john@example.com");
        savedUser.setPassword("encoded-password");

        when(userRepository.save(any(User.class)))
                .thenReturn(savedUser);

        registrationService.register(request);

        ArgumentCaptor<User> userCaptor =
                ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userCaptor.capture());

        User userPassedToRepository = userCaptor.getValue();

        assertEquals("encoded-password",
                userPassedToRepository.getPassword());

        assertNotEquals("MyPassword123",
                userPassedToRepository.getPassword());
    }

    @Test
    void registerShouldRejectDuplicateEmail() {

        RegisterRequest request = new RegisterRequest();
        request.setName("John");
        request.setEmail("john@example.com");
        request.setPassword("MyPassword123");

        when(userRepository.existsByEmail("john@example.com"))
                .thenReturn(true);

        assertThrows(
                IllegalArgumentException.class,
                () -> registrationService.register(request)
        );

        verify(passwordEncoder, never())
                .encode(anyString());

        verify(userRepository, never())
                .save(any(User.class));
    }

}