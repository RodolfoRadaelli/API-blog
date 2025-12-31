package com.practice.blog.security.config;

import com.practice.blog.security.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.practice.blog.security.service.UserService;
import com.practice.blog.security.model.Role;

@Component
public class UserDataSeed implements CommandLineRunner {
	private final UserService userService;

	public UserDataSeed(UserService userService) {
		this.userService = userService;
	}

	@Override
	public void run(String[] args) {
		User user1 = new User("Admin", "1234", Role.valueOf("ROLE_ADMIN"));
		User user2 = new User("Mauro123", "1234", Role.valueOf("ROLE_USER"));

		userService.createUser(user1);
		userService.createUser(user2);
	}
}
