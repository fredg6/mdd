package com.orion.mdd.mapper;

import com.orion.mdd.dto.payload.response.BaseEntityDto;
import com.orion.mdd.dto.payload.response.PostDto;
import com.orion.mdd.dto.payload.response.TopicDto;
import com.orion.mdd.dto.payload.response.UserResponseDto;
import com.orion.mdd.model.BaseEntity;
import com.orion.mdd.model.Post;
import com.orion.mdd.model.Topic;
import com.orion.mdd.model.User;
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
            @SubclassMapping(source = Post.class, target = PostDto.class)
    })
    BaseEntityDto baseEntityToBaseEntityDto(BaseEntity baseEntity);
}