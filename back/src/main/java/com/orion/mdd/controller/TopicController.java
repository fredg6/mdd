package com.orion.mdd.controller;

import com.orion.mdd.dto.payload.response.TopicDto;
import com.orion.mdd.security.service.CustomUserDetails;
import com.orion.mdd.service.TopicService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{id}/subscription")
    public ResponseEntity<?> subscribe(@PathVariable Long id, Authentication authentication) {
        var principal = (CustomUserDetails) authentication.getPrincipal();
        topicService.subscribe(id, principal.getUsername());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/subscription")
    public ResponseEntity<?> unsubscribe(@PathVariable Long id, Authentication authentication) {
        var principal = (CustomUserDetails) authentication.getPrincipal();
        topicService.unsubscribe(id, principal.getUsername());

        return ResponseEntity.ok().build();
    }
}