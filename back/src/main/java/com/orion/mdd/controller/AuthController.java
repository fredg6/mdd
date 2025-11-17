package com.orion.mdd.controller;

import com.orion.mdd.dto.payload.request.LoginDto;
import com.orion.mdd.dto.payload.request.RefreshTokenDto;
import com.orion.mdd.dto.payload.request.UserRequestDto;
import com.orion.mdd.dto.payload.response.JwtDto;
import com.orion.mdd.dto.payload.response.MessageDto;
import com.orion.mdd.dto.payload.response.TokensDto;
import com.orion.mdd.dto.payload.response.UserResponseDto;
import com.orion.mdd.exception.RefreshTokenException;
import com.orion.mdd.mapper.UserMapper;
import com.orion.mdd.security.service.AuthService;
import com.orion.mdd.security.service.CustomUserDetails;
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
    private final AuthService authService;

    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<TokensDto> register(@Valid @RequestBody UserRequestDto userRequestDto) {
        userService.save(UserMapper.INSTANCE.userRequestDtoToUser(userRequestDto));
        var tokens = authService.login(userRequestDto.username(), userRequestDto.password());

        return ResponseEntity.ok(new TokensDto(tokens.get("jwt"), tokens.get("refreshToken")));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<TokensDto> login(@Valid @RequestBody LoginDto loginDto) {
        var tokens = authService.login(loginDto.emailOrUsername(), loginDto.password());
        return ResponseEntity.ok(new TokensDto(tokens.get("jwt"), tokens.get("refreshToken")));
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageDto> logout(@RequestBody RefreshTokenDto refreshTokenDto) {
        if (StringUtils.isBlank(refreshTokenDto.refreshToken())) {
            throw new RefreshTokenException("Refresh token is required.");
        }
        authService.logout(refreshTokenDto.refreshToken());

        return ResponseEntity.ok().body(new MessageDto("Logged out successfully."));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtDto> refreshToken(@RequestBody RefreshTokenDto refreshTokenDto) {
        if (StringUtils.isBlank(refreshTokenDto.refreshToken())) {
            throw new RefreshTokenException("Refresh token is required.");
        }
        var newJwt = authService.refreshJwt(refreshTokenDto.refreshToken());
        return ResponseEntity.ok().body(new JwtDto(newJwt));
    }

    @GetMapping(value = "/me")
    public ResponseEntity<UserResponseDto> getUserProfile(Authentication authentication) {
        var principal = (CustomUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(userService.getUserByUsername(principal.getUsername()));
    }

    @PatchMapping(value = "/me")
    public ResponseEntity<JwtDto> updateUserProfile(@Valid @RequestBody UserRequestDto userRequestDto, Authentication authentication) {
        var principal = (CustomUserDetails) authentication.getPrincipal();
        var userToUpdate = UserMapper.INSTANCE.userRequestDtoToUser(userRequestDto);
        userToUpdate.setId(principal.getId());
        var updatedUser = userService.save(userToUpdate);
        var newJwt = authService.refreshJwt(updatedUser.getId());

        return ResponseEntity.ok(new JwtDto(newJwt));
    }
}