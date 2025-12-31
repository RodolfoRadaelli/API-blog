package com.practice.blog.security.service;

import com.practice.blog.security.model.User;

public interface UserService {
	User createUser(User user);

	User findById(Long id);

	void lockUser(Long id);

	void enableUser(Long id);
}
