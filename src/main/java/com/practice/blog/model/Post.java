package com.practice.blog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Post")
public class Post {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank (message = "El titulo no puede estar vacío.")
    private String title;
    
    @NotBlank (message = "El contenido del blog no puede estar en blanco.")
    private String content;
    
    @NotBlank (message = "El autor no puede estar vacío.")
    @Size (max = 50, message = "El autor debe tener un nombre de menos de 50 caracteres" )
    private String author;
     
    public Post(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    } 
}
