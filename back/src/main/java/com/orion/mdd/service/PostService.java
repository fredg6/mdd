package com.orion.mdd.service;

import com.orion.mdd.model.Post;
import com.orion.mdd.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post add(Post post) {
        return postRepository.save(post);
    }

    public Post getById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + id));
    }
}