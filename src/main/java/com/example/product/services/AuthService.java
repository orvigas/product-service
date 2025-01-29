package com.example.product.services;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.product.models.RefreshToken;
import com.example.product.repositories.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenService tokenService;
    private final AuthenticationManager manager;
    private final RefreshTokenRepository refreshTokenRepository;

    public Map<String, Object> authenticate(final Map<String, String> login) {
        return tokenService.authorize(manager.authenticate(
                new UsernamePasswordAuthenticationToken(login.get("username"), login.get("password"))));

    }

    public Map<String, Object> refreshToken(final UUID refreshToken) {

        return refreshTokenRepository
                .findByIdAndExpiresAtAfter(refreshToken, Instant.now()).map(RefreshToken::getUser).map(user -> {
                    SecurityContextHolder.getContext()
                            .setAuthentication(new UsernamePasswordAuthenticationToken(user, null,
                                    user.getAuthorities()));
                    return tokenService
                            .authorize(SecurityContextHolder.getContext().getAuthentication());
                })
                .orElseThrow();

    }

    public void revokeRefreshToken(final UUID refreshToken) {
        refreshTokenRepository.deleteById(refreshToken);
    }

}
