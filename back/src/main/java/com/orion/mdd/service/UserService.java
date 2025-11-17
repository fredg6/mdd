package com.orion.mdd.service;

import com.orion.mdd.dto.payload.request.RegisterDto;
import com.orion.mdd.dto.payload.response.UserDto;
import com.orion.mdd.exception.FieldsWithValueAlreadyTakenException;
import com.orion.mdd.exception.NotFoundException;
import com.orion.mdd.mapper.UserMapper;
import com.orion.mdd.model.User;
import com.orion.mdd.repository.UserRepository;
import com.orion.mdd.security.jwt.JwtUtils;
import com.orion.mdd.security.service.CustomUserDetails;
import com.orion.mdd.security.service.RefreshTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public UserService(UserRepository userRepository, RefreshTokenService refreshTokenService, BCryptPasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.refreshTokenService = refreshTokenService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public Map<String, String> register(RegisterDto registerDto) {
        var user = UserMapper.INSTANCE.registerDtoToUser(registerDto);
        var fieldsWithValueAlreadyTaken = getFieldsWithValueAlreadyTaken(user);
        if (isNotEmpty(fieldsWithValueAlreadyTaken)) {
            throw new FieldsWithValueAlreadyTakenException(fieldsWithValueAlreadyTaken);
        }
        var rawPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(rawPassword));
        userRepository.save(user);

        return login(user.getUsername(), rawPassword);
    }

    private List<String> getFieldsWithValueAlreadyTaken(User user) {
        var fieldsWithValueAlreadyTaken = new ArrayList<String>();
        if (userRepository.existsByEmail(user.getEmail())) {
            fieldsWithValueAlreadyTaken.add(User.EMAIL_COLUMN_NAME);
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            fieldsWithValueAlreadyTaken.add(User.USERNAME_COLUMN_NAME);
        }

        return fieldsWithValueAlreadyTaken;
    }

    public Map<String, String> login(String emailOrUsername, String password) {
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(emailOrUsername, password));
        var jwt = jwtUtils.generateJwt(authentication);
        var principal = (CustomUserDetails) authentication.getPrincipal();
        var refreshToken = refreshTokenService.createRefreshToken(principal.getId());

        return Map.of("jwt", jwt, "refreshToken", refreshToken.getToken());
    }

    public UserDto getUserByUsername(String username) {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found with username: " + username));

        return UserMapper.INSTANCE.userToUserDto(user);
    }
}