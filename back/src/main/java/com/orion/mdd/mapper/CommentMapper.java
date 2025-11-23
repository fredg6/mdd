package com.orion.mdd.mapper;

import com.orion.mdd.dto.payload.request.CommentRequestDto;
import com.orion.mdd.model.Comment;
import com.orion.mdd.service.PostService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public abstract class CommentMapper implements BaseEntityMapper {
    @Autowired
    protected PostService postService;

    @Mappings({
            @Mapping(target = "post", expression = "java(postService.getById(commentRequestDto.getPostId()))"),
            @Mapping(ignore = true, target = "createdAt"),
            @Mapping(ignore = true, target = "createdBy")
    })
    public abstract Comment commentRequestDtoToComment(CommentRequestDto commentRequestDto);
}