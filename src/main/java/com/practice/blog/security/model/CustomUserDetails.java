package com.practice.blog.security.model;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class CustomUserDetails implements UserDetails {
	private User user;

	public CustomUserDetails(final User user) {
		this.user = user;
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority((user.getRole().name())));
	}

	@Override
	public boolean isAccountNonExpired() {
		return user.isAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return user.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return user.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return user.isEnabled();
	}

	@Override
	public String toString() {
		return "CustomUserDetails [user=" + user + "]";
	}
}
