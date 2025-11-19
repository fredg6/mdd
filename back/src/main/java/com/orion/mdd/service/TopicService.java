package com.orion.mdd.service;

import com.orion.mdd.dto.payload.response.TopicDto;
import com.orion.mdd.mapper.TopicMapper;
import com.orion.mdd.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {
    private final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public List<TopicDto> getAll() {
        var topics = topicRepository.findAll();
        return TopicMapper.INSTANCE.topicsToTopicDtos(topics);
    }
}