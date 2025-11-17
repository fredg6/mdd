package com.orion.mdd.mapper;

import com.orion.mdd.dto.payload.request.RegisterDto;
import com.orion.mdd.dto.payload.response.UserDto;
import com.orion.mdd.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(ignore = true, target = "subscribedTopics")
    User registerDtoToUser(RegisterDto registerDto);

    UserDto userToUserDto(User user);
}