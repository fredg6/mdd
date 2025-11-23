package com.orion.mdd.controller;

import com.orion.mdd.dto.payload.request.CommentRequestDto;
import com.orion.mdd.dto.payload.response.CommentResponseDto;
import com.orion.mdd.mapper.BaseEntityMapper;
import com.orion.mdd.mapper.CommentMapper;
import com.orion.mdd.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/comment", produces = APPLICATION_JSON_VALUE)
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    public CommentController(CommentService commentService, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.commentMapper = commentMapper;
    }

    @PostMapping
    public ResponseEntity<CommentResponseDto> add(@Valid @RequestBody CommentRequestDto commentRequestDto) {
        var commentToCreate = commentMapper.commentRequestDtoToComment(commentRequestDto);
        var createdComment = commentService.add(commentToCreate);

        return ResponseEntity.ok((CommentResponseDto) BaseEntityMapper.INSTANCE.baseEntityToBaseEntityDto(createdComment));
    }
}