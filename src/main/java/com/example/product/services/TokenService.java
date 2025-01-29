package com.example.product.services;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.example.product.dtos.UserDto;
import com.example.product.models.RefreshToken;
import com.example.product.repositories.RefreshTokenRepository;
import com.example.product.repositories.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {

    @Value("${spring.application.name}")
    private String appName;

    private static final String TOKEN_TYPE = "Bearer";

    private final ObjectMapper objectMapper;
    private final JwtEncoder encoder;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public Map<String, Object> authorize(final Authentication authentication) {

        return userRepository.findByUsername(authentication.getName()).map(user -> {

            final var userDto = objectMapper.convertValue(UserDto.from(user), new TypeReference<Map<String, Object>>() {});
            final var now = Instant.now();
            final var accessTokenExpiresIn = now.plus(12, ChronoUnit.HOURS);
            final var refreshTokenExpiresIn = now.plus(7, ChronoUnit.DAYS);
            final var claims = JwtClaimsSet.builder()
                    .issuer(appName)
                    .issuedAt(now)
                    .expiresAt(accessTokenExpiresIn)
                    .subject(authentication.getName())
                    .claim("user_data", userDto)
                    .build();

            final var refreshToken = refreshTokenRepository
                    .save(RefreshToken.builder().createdAt(now).expiresAt(refreshTokenExpiresIn).user(user).build());

            final var result = new HashMap<String, Object>();
            result.put("access_token", encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue());
            result.put("token_type", TOKEN_TYPE);
            result.put("expires_in", Duration.between(now, accessTokenExpiresIn));
            result.put("refresh_token", refreshToken.getId());
            return result;

        }).orElseThrow();

    }
}