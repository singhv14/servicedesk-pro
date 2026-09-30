package com.servicedesk.pro.security;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;

    public JwtService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateToken(String email) {

        Instant now = Instant.now();

        JwtClaimsSet claimsSet=JwtClaimsSet.builder()
                .subject(email)
                .issuedAt(now)
                .expiresAt(now.plus(15, ChronoUnit.MINUTES))
                .build();

        JwsHeader header=JwsHeader.with(MacAlgorithm.HS256).build();

        return jwtEncoder.encode(JwtEncoderParameters.from(header, claimsSet)).getTokenValue();
        
    }

}