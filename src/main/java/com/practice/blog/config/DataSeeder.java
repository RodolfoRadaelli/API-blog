package com.practice.blog.config;

import com.practice.blog.model.Post;
import com.practice.blog.service.PostServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner{
    private final PostServiceImpl postServiceImpl;
    
    DataSeeder(PostServiceImpl postServiceImpl){
        this.postServiceImpl = postServiceImpl;
    }
    
    @Override
    public void run(String[] args){
        Post post1 = new Post("Titulo1", "Cuerpo del Post", "John Smith");
        Post post2 = new Post("Titulo2", "Cuerpo del Post", "Juan Lopez");
        Post post3 = new Post("Titulo3", "Cuerpo del Post", "Magali Perez");
        
        postServiceImpl.createPost(post1);
        postServiceImpl.createPost(post2);
        postServiceImpl.createPost(post3);
    }
}
