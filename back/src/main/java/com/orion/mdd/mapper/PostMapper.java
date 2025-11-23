package com.orion.mdd.mapper;

import com.orion.mdd.dto.payload.request.PostRequestDto;
import com.orion.mdd.dto.payload.response.PostResponseDto;
import com.orion.mdd.model.Post;
import com.orion.mdd.service.TopicService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public abstract class PostMapper implements BaseEntityMapper {
    @Autowired
    protected TopicService topicService;

    public abstract List<PostResponseDto> postsToPostResponseDtos(List<Post> posts);

    @Mapping(source = "topic.title", target = "topicTitle")
    public abstract PostResponseDto postToPostResponseDto(Post post);

    @Mappings({
            @Mapping(target = "topic", expression = "java(topicService.getById(postRequestDto.getTopicId()))"),
            @Mapping(ignore = true, target = "createdAt"),
            @Mapping(ignore = true, target = "createdBy"),
            @Mapping(ignore = true, target = "comments")
    })
    public abstract Post postRequestDtoToPost(PostRequestDto postRequestDto);
}