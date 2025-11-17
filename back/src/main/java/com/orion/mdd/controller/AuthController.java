package com.orion.mdd.controller;

import com.orion.mdd.dto.payload.request.LoginDto;
import com.orion.mdd.dto.payload.request.RefreshTokenDto;
import com.orion.mdd.dto.payload.request.RegisterDto;
import com.orion.mdd.dto.payload.response.JwtDto;
import com.orion.mdd.dto.payload.response.TokensDto;
import com.orion.mdd.dto.payload.response.UserDto;
import com.orion.mdd.security.jwt.JwtUtils;
import com.orion.mdd.security.repository.RefreshTokenRepository;
import com.orion.mdd.security.service.CustomUserDetails;
import com.orion.mdd.security.service.RefreshTokenService;
import com.orion.mdd.service.UserService;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/auth", produces = APPLICATION_JSON_VALUE)
public class AuthController {
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtils jwtUtils;

    public AuthController(UserService userService, RefreshTokenService refreshTokenService, RefreshTokenRepository refreshTokenRepository, JwtUtils jwtUtils) {
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto registerDto) {
        var tokens = userService.register(registerDto);
        return ResponseEntity.ok(new TokensDto(tokens.get("jwt"), tokens.get("refreshToken")));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<TokensDto> login(@Valid @RequestBody LoginDto loginDto) {
        var tokens = userService.login(loginDto.emailOrUsername(), loginDto.password());
        return ResponseEntity.ok(new TokensDto(tokens.get("jwt"), tokens.get("refreshToken")));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody RefreshTokenDto refreshTokenDto) {
        var refreshToken = refreshTokenDto.refreshToken();
        if (StringUtils.isBlank(refreshToken)) {
            return ResponseEntity.badRequest().body("Refresh token is required.");
        }

        return refreshTokenRepository.findByToken(refreshToken)
                .map(token -> {
                    refreshTokenRepository.delete(token);
                    return ResponseEntity.ok("Logged out successfully.");
                })
                .orElse(ResponseEntity.badRequest().body("Invalid refresh token."));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenDto refreshTokenDto) {
        return refreshTokenRepository.findByToken(refreshTokenDto.refreshToken())
                .map(token -> {
                    if (refreshTokenService.isTokenExpired(token)) {
                        refreshTokenRepository.delete(token);
                        return ResponseEntity.badRequest().body("Refresh token expired. Please login again.");
                    }
                    var newJwt = jwtUtils.buildJwt(token.getUser().getUsername());
                    return ResponseEntity.ok(new JwtDto(newJwt));
                })
                .orElse(ResponseEntity.badRequest().body("Invalid refresh token."));
    }

    @GetMapping(value = "/me")
    public ResponseEntity<UserDto> me(Authentication authentication) {
        var principal = (CustomUserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(userService.getUserByUsername(principal.getUsername()));
    }
}