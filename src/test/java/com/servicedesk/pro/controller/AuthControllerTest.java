package com.servicedesk.pro.controller;

import com.servicedesk.pro.dto.LoginRequest;
import com.servicedesk.pro.entity.User;
import com.servicedesk.pro.service.RegistrationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RegistrationService registrationService;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @Test
    void registerShouldReturnCreatedUserWithoutPassword() throws Exception {

        User user = new User();
        user.setName("John");
        user.setEmail("john@example.com");

        when(registrationService.register(any()))
                .thenReturn(user);

        mockMvc.perform(
                        post("/api/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                        {
                            "name": "John",
                            "email": "john@example.com",
                            "password": "MyPassword123"
                        }
                        """)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    void loginShouldReturnOkForValidCredentials() throws Exception {

        LoginRequest request = new LoginRequest();
        request.setEmail("alex@example.com");
        request.setPassword("MyPassword123");

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        "alex@example.com",
                        null,
                        List.of()
                );

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "email": "alex@example.com",
                                "password": "MyPassword123"
                            }
                            """))
                .andExpect(status().isOk());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void loginShouldReturnUnauthorizedForInvalidCredentials() throws  Exception {

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "email": "alex@example.com",
                                "password": "WrongPassword"
                            }
                            """))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void loginShouldStoreAuthenticationInSecurityContext() throws Exception {

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        "alex@example.com",
                        null,
                        List.of()
                );

        when(authenticationManager.authenticate(
                any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        mockMvc.perform(
                        post("/api/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                            {
                                "email": "alex@example.com",
                                "password": "MyPassword123"
                            }
                            """)
                )
                .andExpect(status().isOk());

        Authentication storedAuthentication =
                SecurityContextHolder.getContext().getAuthentication();

        assertThat(storedAuthentication).isSameAs(authentication);
    }

}
