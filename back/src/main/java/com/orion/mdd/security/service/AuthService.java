package com.orion.mdd.security.service;

import com.orion.mdd.exception.RefreshTokenException;
import com.orion.mdd.repository.UserRepository;
import com.orion.mdd.security.jwt.JwtUtils;
import com.orion.mdd.security.model.RefreshToken;
import com.orion.mdd.security.repository.RefreshTokenRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final Long refreshTokenDurationInMs;

    public AuthService(
            RefreshTokenRepository refreshTokenRepository,
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            JwtUtils jwtUtils,
            @Value("${jwt.refreshTokenDurationInMs}") Long refreshTokenDurationInMs) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
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

    public String refreshJwt(Long userId) {
        var maybeRefreshToken = refreshTokenRepository.findByUserId(userId);
        return maybeRefreshToken.map(this::refreshJwt).orElseThrow(() -> new EntityNotFoundException("Refresh token not found for user id: " + userId));
    }

    public String refreshJwt(String refreshToken) {
        var maybeRefreshToken = refreshTokenRepository.findByToken(refreshToken);
        return maybeRefreshToken.map(this::refreshJwt).orElseThrow(() -> new RefreshTokenException("Invalid refresh token."));
    }

    private String refreshJwt(RefreshToken refreshToken) {
        if (isTokenExpired(refreshToken)) {
            refreshTokenRepository.delete(refreshToken);
            throw new RefreshTokenException("Refresh token expired. Please login again.");
        }
        return jwtUtils.buildJwt(refreshToken.getUser().getUsername());
    }

    public Map<String, String> login(String emailOrUsername, String password) {
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(emailOrUsername, password));
        var jwt = jwtUtils.generateJwt(authentication);
        var principal = (CustomUserDetails) authentication.getPrincipal();
        var refreshToken = createRefreshToken(principal.getId());

        return Map.of("jwt", jwt, "refreshToken", refreshToken.getToken());
    }

    public boolean logout(String refreshToken) {
        return refreshTokenRepository.findByToken(refreshToken)
                .map(token -> {
                    refreshTokenRepository.delete(token);
                    return true;
                })
                .orElseThrow(() -> new RefreshTokenException("Invalid refresh token."));
    }

    private boolean isTokenExpired(RefreshToken token) {
        return token.getExpiryDate().isBefore(Instant.now());
    }
}