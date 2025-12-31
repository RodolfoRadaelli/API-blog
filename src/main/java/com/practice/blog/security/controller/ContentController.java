package com.practice.blog.security.controller;

import com.practice.blog.security.service.UserDetailsServiceImpl;
import com.practice.blog.security.jwt.JwtService;
import com.practice.blog.security.dto.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContentController {

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final UserDetailsServiceImpl userDetailsServiceImpl;

	public ContentController(AuthenticationManager authenticationManager, JwtService jwtService,
			UserDetailsServiceImpl userDetailsServiceImpl) {
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.userDetailsServiceImpl = userDetailsServiceImpl;
	}

	@GetMapping("/home")
	public String handleWelcome() {
		return "Welcome to home!";
	}

	@GetMapping("/admin/home")
	public String handleAdminHome() {
		return "Welcome to ADMIN home!";
	}

	@GetMapping("/user/home")
	public String handleUserHome() {
		return "Welcome to USER home!";
	}

	@PostMapping("/authenticate")
	public String authenticateAndGetToken(@RequestBody LoginForm loginForm) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(
						loginForm.username(), loginForm.password()));
		if (authentication.isAuthenticated()) {
			return jwtService
					.createToken(userDetailsServiceImpl.loadUserByUsername(loginForm.username()));
		} else {
			throw new UsernameNotFoundException("Invalid credentials");
		}
	}
}
