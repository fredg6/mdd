package com.orion.mdd.security.service;

import com.orion.mdd.repository.UserRepository;
import com.orion.mdd.security.model.RefreshToken;
import com.orion.mdd.security.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final Long refreshTokenDurationInMs;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository, @Value("${jwt.refreshTokenDurationInMs}") Long refreshTokenDurationInMs) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
        this.refreshTokenDurationInMs = refreshTokenDurationInMs;
    }

    public RefreshToken createRefreshToken(Long userId) {
        var token = RefreshToken.builder()
                .user(userRepository.findById(userId).get())
                .expiryDate(Instant.now().plusMillis(refreshTokenDurationInMs))
                .token(UUID.randomUUID().toString())
                .build();

        return refreshTokenRepository.save(token);
    }

    public boolean isTokenExpired(RefreshToken token) {
        return token.getExpiryDate().isBefore(Instant.now());
    }
}