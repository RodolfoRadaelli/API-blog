package com.practice.blog.service;

import com.practice.blog.model.Post;
import com.practice.blog.model.PostDto;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PostService{
    
    Page<PostDto> findAllPost(Pageable pageable);
    Optional<Post> findPostById(Long id);
    Post createPost (Post post);
    Optional<Post> updatePost(Long id, Post postDetails);
    Boolean deletePost(Long id);
}
