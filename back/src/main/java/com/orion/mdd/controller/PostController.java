package com.orion.mdd.controller;

import com.orion.mdd.dto.payload.request.PostRequestDto;
import com.orion.mdd.dto.payload.response.PostResponseDto;
import com.orion.mdd.mapper.BaseEntityMapper;
import com.orion.mdd.mapper.PostMapper;
import com.orion.mdd.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/post", produces = APPLICATION_JSON_VALUE)
public class PostController {
    private final PostService postService;
    private final PostMapper postMapper;

    public PostController(PostService postService, PostMapper postMapper) {
        this.postService = postService;
        this.postMapper = postMapper;
    }

    @PostMapping
    public ResponseEntity<PostResponseDto> add(@Valid @RequestBody PostRequestDto postRequestDto) {
        var postToCreate = postMapper.postRequestDtoToPost(postRequestDto);
        var createdPost = postService.add(postToCreate);

        return ResponseEntity.ok((PostResponseDto) BaseEntityMapper.INSTANCE.baseEntityToBaseEntityDto(createdPost));
    }
}