package com.servicedesk.pro.security;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    private final JwtDecoder jwtDecoder = mock(JwtDecoder.class);

    private final JwtAuthenticationFilter filter =
            new JwtAuthenticationFilter(jwtDecoder);

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void validJwtShouldAuthenticateRequest() throws Exception {

        Jwt jwt = mock(Jwt.class);

        when(jwt.getSubject()).thenReturn("alex@example.com");
        when(jwtDecoder.decode("test-token")).thenReturn(jwt);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer test-token");

        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        filter.doFilter(request, response, filterChain);

        assertNotNull(
                SecurityContextHolder.getContext().getAuthentication()
        );

        assertEquals(
                "alex@example.com",
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName()
        );

        verify(jwtDecoder).decode("test-token");
    }

    @Test
    void invalidJwtShouldReturnUnauthorized() throws Exception {

        when(jwtDecoder.decode("invalid-token"))
                .thenThrow(new JwtException("Invalid JWT"));

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer invalid-token");

        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        filter.doFilter(request, response, filterChain);

        assertEquals(
                HttpServletResponse.SC_UNAUTHORIZED,
                response.getStatus()
        );

        assertNull(
                SecurityContextHolder.getContext().getAuthentication()
        );

        assertNull(filterChain.getRequest());
    }

    @Test
    void requestWithoutAuthorizationHeaderShouldContinue() throws Exception {

        MockHttpServletRequest request = new MockHttpServletRequest();

        MockHttpServletResponse response = new MockHttpServletResponse();

        MockFilterChain filterChain = new MockFilterChain();

        filter.doFilter(request, response, filterChain);

        assertNull(
                SecurityContextHolder.getContext().getAuthentication()
        );

        assertEquals(
                HttpServletResponse.SC_OK,
                response.getStatus()
        );

        assertNotNull(filterChain.getRequest());
    }

}