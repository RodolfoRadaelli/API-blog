package com.practice.blog.controller;

import com.practice.blog.model.Post;
import com.practice.blog.service.PostService;
import com.practice.blog.exception.ResourceNotFoundException;
import com.practice.blog.model.PostDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {
	private final PostService postService;

	@Autowired
	PostController(PostService postService) {
		this.postService = postService;
	}

	@GetMapping
	public ResponseEntity<Page<PostDto>> getAllPosts(
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
		Page<PostDto> postPage = postService.findAllPost(pageable);
		return ResponseEntity.ok(postPage);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Post> getPostById(@PathVariable Long id) {
		Post post = postService.findPostById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No existe el post."));

		return ResponseEntity.ok(post);

	}

	@PostMapping
	public ResponseEntity<Post> createPost(@Valid @RequestBody Post post) {
		Post createdPost = postService.createPost(post);

		return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Post> updatePost(@Valid @PathVariable Long id,
			@RequestBody Post postDetails) {
		Post updatePost = postService.updatePost(id, postDetails)
				.orElseThrow(() -> new ResourceNotFoundException("No existe el post a actualizar."));

		return ResponseEntity.ok(updatePost);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Post> deletePost(@PathVariable Long id) {
		Boolean deletedPost = postService.deletePost(id);
		if (deletedPost) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			throw new ResourceNotFoundException("No existe el post a borrar.");
		}

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleNotValidException(MethodArgumentNotValidException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
