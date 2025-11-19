package com.orion.mdd.mapper;

import com.orion.mdd.dto.payload.response.TopicDto;
import com.orion.mdd.model.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TopicMapper {
    TopicMapper INSTANCE = Mappers.getMapper(TopicMapper.class);

    List<TopicDto> topicsToTopicDtos(List<Topic> topics);
}