package com.orion.mdd.mapper;

import com.orion.mdd.dto.payload.response.*;
import com.orion.mdd.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassMapping;
import org.mapstruct.SubclassMappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BaseEntityMapper {
    BaseEntityMapper INSTANCE = Mappers.getMapper(BaseEntityMapper.class);

    @SubclassMappings({
            @SubclassMapping(source = User.class, target = UserResponseDto.class),
            @SubclassMapping(source = Topic.class, target = TopicDto.class),
            @SubclassMapping(source = Post.class, target = PostResponseDto.class),
            @SubclassMapping(source = Comment.class, target = CommentResponseDto.class)
    })
    BaseEntityDto baseEntityToBaseEntityDto(BaseEntity baseEntity);
}