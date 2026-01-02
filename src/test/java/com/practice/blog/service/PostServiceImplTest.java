package com.practice.blog.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import com.practice.blog.service.PostServiceImpl;
import com.practice.blog.repository.PostRepository;
import com.practice.blog.model.Post;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PostServiceImplTest {

	@Mock
	PostRepository repoTest;

	@InjectMocks
	PostServiceImpl serviceTest;

	private Long id;
	private Post oldPost;

	@BeforeEach
	public void setUp() {
		id = 1L;
		oldPost = new Post("Titulo", "blabla", "Mauro");
		oldPost.setId(id);

	}

	@DisplayName("Update Post Tests")
	@Nested
	class UpdatePostTest {
		@Test
		public void CanPostUpdateWithPostPresent() {
			// Given
			Post postDetails = new Post("TituloNuevo", "Tralala", "Santiago");

			when(repoTest.findById(id)).thenReturn(Optional.of(oldPost));
			when(repoTest.save(any(Post.class))).thenReturn(oldPost);

			// When
			Optional<Post> result = serviceTest.updatePost(id, postDetails);

			// Then
			assertTrue(result.isPresent());
			assertEquals("TituloNuevo", oldPost.getTitle());
			verify(repoTest).save(oldPost);
		}

		@Test
		public void CanNotPostUpdateWithPostAbsent() {
			// Given
			Post postDetails = new Post("TituloNuevo", "Tralala", "Santiago");

			when(repoTest.findById(id)).thenReturn(Optional.empty());

			// When
			Optional<Post> result = serviceTest.updatePost(id, postDetails);

			// Then
			assertTrue(result.isEmpty());
			verify(repoTest).findById(id);
			verify(repoTest, never()).save(any());
		}

	}

	@DisplayName("Delete Post Tests")
	@Nested
	class deletePostTest {
		@Test
		public void canDeletePostPresent() {
			// Given

			when(repoTest.findById(id)).thenReturn(Optional.of(oldPost));

			// When
			Boolean result = serviceTest.deletePost(id);

			// Then
			assertTrue(result);
			verify(repoTest).findById(id);
			verify(repoTest).deleteById(id);
		}

		@Test
		public void canNotDeleteNoPostAbsent() {
			// Given
			when(repoTest.findById(id)).thenReturn(Optional.empty());

			// When
			Boolean result = serviceTest.deletePost(id);

			// Then
			assertFalse(result);
			verify(repoTest).findById(id);
			verify(repoTest, never()).deleteById(id);

		}
	}

}
