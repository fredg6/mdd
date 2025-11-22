package com.orion.mdd.mapper;

import com.orion.mdd.dto.payload.request.UserRequestDto;
import com.orion.mdd.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(ignore = true, target = "subscribedTopics")
    User userRequestDtoToUser(UserRequestDto userRequestDto);
}