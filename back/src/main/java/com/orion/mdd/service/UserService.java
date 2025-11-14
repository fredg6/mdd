package com.orion.mdd.service;

import com.orion.mdd.dto.UserDto;
import com.orion.mdd.exception.FieldsWithValueAlreadyTakenException;
import com.orion.mdd.mapper.UserMapper;
import com.orion.mdd.model.User;
import com.orion.mdd.repository.UserRepository;
import com.orion.mdd.security.jwt.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public String register(UserDto userDto) {
        var user = UserMapper.INSTANCE.userDtoToUser(userDto);
        var fieldsWithValueAlreadyTaken = getFieldsWithValueAlreadyTaken(user);
        if (isNotEmpty(fieldsWithValueAlreadyTaken)) {
            throw new FieldsWithValueAlreadyTakenException(fieldsWithValueAlreadyTaken);
        }
        var rawPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(rawPassword));
        userRepository.save(user);
        return login(user.getEmail(), rawPassword);
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

    public String login(String emailOrUsername, String password) {
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(emailOrUsername, password));
        return jwtUtils.generateJwtToken(authentication);
    }
}