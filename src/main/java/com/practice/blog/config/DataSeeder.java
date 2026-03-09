package com.practice.blog.config;

import com.practice.blog.model.Post;
import com.practice.blog.service.PostServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {
	private final PostServiceImpl postServiceImpl;

	public DataSeeder(PostServiceImpl postServiceImpl) {
		this.postServiceImpl = postServiceImpl;
	}

	@Override
	public void run(String[] args) {
		Post post1 = new Post("Reglas del blog", "Respeten a los otros usuarios.", "Admin");
		Post post2 = new Post("Nuevo yo",
				"Hoy luego de ayudar a un tio, me llegó la revelación de comenzar mi propio emprendimiento.",
				"Mauro123");
		Post post3 = new Post("Vendo Limones", "Comencé vendiendo limones en la vereda de mi casa", "Mauro123");

		postServiceImpl.createPost(post1);
		postServiceImpl.createPost(post2);
		postServiceImpl.createPost(post3);
	}
}
