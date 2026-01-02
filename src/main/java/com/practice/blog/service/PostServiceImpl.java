package com.practice.blog.service;

import com.practice.blog.model.Post;
import com.practice.blog.model.PostDto;
import com.practice.blog.repository.PostRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {
	private final PostRepository postRepository;

	@Autowired
	PostServiceImpl(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	@Override
	public Page<PostDto> findAllPost(Pageable pageable) {
		Page<Post> postEntityPage = postRepository.findAll(pageable);
		return postEntityPage.map(this::convertToDto);
	}

	private PostDto convertToDto(Post post) {
		return new PostDto(post.getId(), post.getTitle(), post.getContent(), post.getAuthor());
	}

	@Override
	public Optional<Post> findPostById(Long id) {
		return (Optional<Post>) postRepository.findById(id);
	}

	@Override
	public Post createPost(Post post) {
		return postRepository.save(post);
	}

	@Override
	public Optional<Post> updatePost(Long id, Post postDetails) {
		Optional<Post> oldPost = postRepository.findById(id);

		oldPost.ifPresent(post -> {
			post.setTitle(postDetails.getTitle());
			post.setContent(postDetails.getContent());
			post.setAuthor(postDetails.getAuthor());
			postRepository.save(post);
		});

		return oldPost;
	}

	@Override
	public Boolean deletePost(Long id) {
		Optional<Post> post = postRepository.findById(id);
		if (post.isPresent()) {
			postRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
}
