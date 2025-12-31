package com.practice.blog.security.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginForm(
		@NotBlank(message = "El nombre de usuario no puede estar vacío.") String username,
		@NotBlank(message = "La contraseña no puede estar vacio.") String password) {
}
