package com.practice.blog.security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.practice.blog.security.jwt.JwtService;
import com.practice.blog.model.Post;
import com.practice.blog.repository.PostRepository;
import java.io.IOException;

@Component
public class PostAuthorizationFilter extends OncePerRequestFilter {

	private JwtService jwtService;
	private PostRepository postRepository;

	public PostAuthorizationFilter(JwtService jwtService, PostRepository postRepository) {
		this.jwtService = jwtService;
		this.postRepository = postRepository;
	}

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		String uri = request.getRequestURI();
		String method = request.getMethod();

		if (!uri.matches("/api/posts/\\d+") || !(method.equals("PUT") || method.equals("DELETE"))) {
			filterChain.doFilter(request, response);
			return;
		}

		String authHeader = request.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = authHeader.substring(7);
		String username = jwtService.extractUsername(token);
		String role = jwtService.extractRole(token);

		if ("ROLE_ADMIN".equals(role)) {
			filterChain.doFilter(request, response);
			return;
		}

		Long postId = Long.parseLong(uri.substring(uri.lastIndexOf("/") + 1));
		Post post = postRepository.findById(postId).orElse(null);

		if (post == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.getWriter().write("{\"error\":\"Post no encontrado\"}");
			return;
		}

		if (!post.getAuthor().equals(username)) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.setContentType("application/json");
			response.getWriter().write("{\"error\":\"No puedes modificar posts de otros usuarios\"}");
			return;
		}

		filterChain.doFilter(request, response);
	}

}
