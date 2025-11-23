package com.orion.mdd.service;

import com.orion.mdd.model.Comment;
import com.orion.mdd.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment add(Comment comment) {
        return commentRepository.save(comment);
    }
}