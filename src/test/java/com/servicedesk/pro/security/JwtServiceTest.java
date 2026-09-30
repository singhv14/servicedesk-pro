package com.servicedesk.pro.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private static final SecretKey TEST_SECRET_KEY =
            new SecretKeySpec(
                    "test-secret-key-for-jwt-testing-32".getBytes(StandardCharsets.UTF_8),
                    "HmacSHA256"
            );

    private JwtEncoder jwtEncoder() {
        return NimbusJwtEncoder
                .withSecretKey(TEST_SECRET_KEY)
                .algorithm(MacAlgorithm.HS256)
                .build();
    }

    private JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder
                .withSecretKey(TEST_SECRET_KEY)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }

    private JwtService jwtService() {
        return new JwtService(jwtEncoder());
    }

    @Test
    void shouldGenerateValidJwt() {
        String token = jwtService().generateToken("alex@example.com");

        Jwt jwt = jwtDecoder().decode(token);

        assertNotNull(token);
        assertEquals("alex@example.com", jwt.getSubject());
        assertNotNull(jwt.getIssuedAt());
        assertNotNull(jwt.getExpiresAt());

        long tokenLifetimeSeconds =
                jwt.getExpiresAt().getEpochSecond()
                        - jwt.getIssuedAt().getEpochSecond();

        assertEquals(15 * 60, tokenLifetimeSeconds);

    }

    @Test
    void shouldRejectTamperedJwt() {
        String token = jwtService().generateToken("alex@example.com");

        String[] parts = token.split("\\.");

        String tamperedPayload = parts[1] + "tampered";

        String tamperedToken = parts[0] + "."
                + tamperedPayload + "."
                + parts[2];

        assertThrows(
                JwtException.class,
                () -> jwtDecoder().decode(tamperedToken)
        );
    }


}