package com.orion.mdd.dto.payload.response;

import com.orion.mdd.model.Topic;

import java.util.Set;

public record UserResponseDto(String email, String username, Set<Topic> subscribedTopics) {
}