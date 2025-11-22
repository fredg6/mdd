package com.orion.mdd.mapper;

import com.orion.mdd.dto.payload.response.PostDto;
import com.orion.mdd.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PostMapper extends BaseEntityMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    List<PostDto> postsToPostDtos(List<Post> posts);
}