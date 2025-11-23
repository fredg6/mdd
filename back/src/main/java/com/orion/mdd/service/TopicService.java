package com.orion.mdd.service;

import com.orion.mdd.dto.payload.response.TopicDto;
import com.orion.mdd.mapper.TopicMapper;
import com.orion.mdd.model.Topic;
import com.orion.mdd.repository.TopicRepository;
import com.orion.mdd.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    public TopicService(TopicRepository topicRepository, UserRepository userRepository) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    public Topic getById(Long id) {
        return topicRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Topic not found with id: " + id));
    }

    public List<TopicDto> getAll() {
        var topics = topicRepository.findAll();
        return TopicMapper.INSTANCE.topicsToTopicDtos(topics);
    }

    public boolean subscribe(Long id, String username) {
        var topic = topicRepository.getReferenceById(id);
        var user = userRepository.findByUsername(username).get();
        user.getSubscribedTopics().add(topic);
        userRepository.save(user);

        return true;
    }

    public boolean unsubscribe(Long id, String username) {
        var topic = topicRepository.getReferenceById(id);
        var user = userRepository.findByUsername(username).get();
        if (user.getSubscribedTopics().remove(topic)) {
            userRepository.save(user);
        }

        return true;
    }
}