package com.orion.mdd.controller;

import com.orion.mdd.dto.payload.response.TopicDto;
import com.orion.mdd.service.TopicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/topic", produces = APPLICATION_JSON_VALUE)
public class TopicController {
    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    public ResponseEntity<List<TopicDto>> all() {
        return ResponseEntity.ok(topicService.getAll());
    }
}