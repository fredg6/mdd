package com.orion.mdd.dto.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserResponseDto extends BaseEntityDto {
    private String email;
    private String username;
    private Set<TopicDto> subscribedTopics;
}