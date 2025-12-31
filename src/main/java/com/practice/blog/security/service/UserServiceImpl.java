package com.practice.blog.security.service;

import com.practice.blog.security.model.User;
import com.practice.blog.security.model.Role;
import com.practice.blog.security.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

	public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
	}

	@Override
	public User createUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	public User findById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
	}

	public void lockUser(Long id) {
		User user = findById(id);
		user.setAccountNonLocked(false);
		userRepository.save(user);
	}

	public void enableUser(Long id) {
		User user = findById(id);
		user.setAccountNonLocked(true);
		user.setEnabled(true);
		userRepository.save(user);
	}
}
