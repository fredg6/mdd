package com.orion.mdd.controller;

import com.orion.mdd.dto.JwtDto;
import com.orion.mdd.dto.LoginRequestDto;
import com.orion.mdd.dto.UserDto;
import com.orion.mdd.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/auth", produces = APPLICATION_JSON_VALUE)
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDto userDto) {
        var jwt = userService.register(userDto);
        return ResponseEntity.ok(new JwtDto(jwt));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        var jwt = userService.login(loginRequestDto.emailOrUsername(), loginRequestDto.password());
        return ResponseEntity.ok(new JwtDto(jwt));
    }
}