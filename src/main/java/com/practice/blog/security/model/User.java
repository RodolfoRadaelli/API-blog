package com.practice.blog.security.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Column;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "El nombre de usuario no puede estar vacío.")
	private String username;

	@NotBlank(message = "Debe existir una contraseña.")
	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;

	@Column(nullable = false)
	private boolean enabled = true;

	private boolean accountNonExpired = true;
	private boolean accountNonLocked = true;
	private boolean credentialsNonExpired = true;

	public User(String username, String password, Role role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}
}
