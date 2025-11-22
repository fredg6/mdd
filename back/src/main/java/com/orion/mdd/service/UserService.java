package com.orion.mdd.service;

import com.orion.mdd.dto.payload.response.PostDto;
import com.orion.mdd.dto.payload.response.UserResponseDto;
import com.orion.mdd.exception.FieldsWithValueAlreadyTakenException;
import com.orion.mdd.mapper.BaseEntityMapper;
import com.orion.mdd.mapper.PostMapper;
import com.orion.mdd.model.Post;
import com.orion.mdd.model.User;
import com.orion.mdd.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User save(User user) {
        validate(user);
        var rawPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(rawPassword));
        return userRepository.save(user);
    }

    private boolean validate(User user) {
        var fieldsWithValueAlreadyTaken = getFieldsWithValueAlreadyTaken(user);
        if (isNotEmpty(fieldsWithValueAlreadyTaken)) {
            throw new FieldsWithValueAlreadyTakenException(fieldsWithValueAlreadyTaken);
        }

        return true;
    }

    private List<String> getFieldsWithValueAlreadyTaken(User user) {
        var fieldsWithValueAlreadyTaken = new ArrayList<String>();
        if (userRepository.existsByEmail(user.getEmail())) {
            var existingUser = userRepository.findByEmail(user.getEmail()).get();
            if (!existingUser.getId().equals(user.getId())) {
                fieldsWithValueAlreadyTaken.add(User.EMAIL_COLUMN_NAME);
            }
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            var existingUser = userRepository.findByUsername(user.getUsername()).get();
            if (!existingUser.getId().equals(user.getId())) {
                fieldsWithValueAlreadyTaken.add(User.USERNAME_COLUMN_NAME);
            }
        }

        return fieldsWithValueAlreadyTaken;
    }

    public UserResponseDto getUserByUsername(String username) {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));

        return (UserResponseDto) BaseEntityMapper.INSTANCE.baseEntityToBaseEntityDto(user);
    }

    public List<PostDto> getUserFeed(Sort.Direction sortDirection, String username) {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));
        var unsortedUserFeed = new ArrayList<Post>();
        user.getSubscribedTopics().forEach(t -> unsortedUserFeed.addAll(t.getPosts()));
        var sortedUserFeed = unsortedUserFeed.stream().sorted(
                Sort.Direction.ASC.equals(sortDirection) ? Comparator.comparing(Post::getCreatedAt) : Comparator.comparing(Post::getCreatedAt).reversed()
        ).toList();

        return PostMapper.INSTANCE.postsToPostDtos(sortedUserFeed);
    }
}