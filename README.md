# API BLOG

Este repositorio es una aplicación web Full-Stack para gestionar posts en un sistema de blog. Permite operaciones CRUD básicas a través de endpoints HTTP y devuelve JSON, además se creó una SPA con React para consumir estos servicios.

## Tecnologías Utilizadas

* **Spring Boot 3.5.3**
* **Java 17**
* **Maven**
* **Spring Data JPA**
* **H2**
* **Lombok**
* **Validation**
* **Spring Security**
* **JWT**
* **JUnit**
* **Mockito**
* **Docker**
* **DockerCompose**
* **React**
* **Typescript**

## Funcionalidades (CRUD Básico)

Esta API proporciona las siguientes funcionalidades principales para la entidad Post:

* **Crear (Create):** Permite añadir nuevos Post.
* **Leer (Read):** Permite obtener Post individuales o una lista de todos los post usando Pagination and sorting.
* **Actualizar (Update):** Permite modificar Post existentes.
* **Eliminar (Delete):** Permite borrar Post.
* **Login:** Permite auntenticar al usuario para acceder a posts.
* **Crear usuarios:** Permite crear usuarios nuevos.

## **Características Destacadas / Funcionalidades Adicionales**

* **Paginación y Ordenamiento (Pageable):** Se utiliza al recibir todos los posts de manera ordenada en paginas.
* **Validación de Datos:** Uso de anotaciones de validación para asegurar que los datos sean correctos en los requests.
* **Manejo de Excepciones Global:** Implementación de un manejo global de excepciones a través de un ControllerAdvice para proporcionar respuestas de error descriptivas para error 404 NOT FOUND.
* **Uso de DTOs:** Aplicación del patrón Data Transfer Object (DTO) para la transferencia de datos Page<Post> con solo los datos pertinentes.

## Endpoints de la API

Los datos se intercambian en formato **JSON**.

### /api/posts

| Método HTTP | Endpoint                       | Descripción                                     | Request Body (JSON)                                    | Response Body (JSON)                                       |
| :---------- | :----------------------------- | :---------------------------------------------- | :------------------------------------------------------- | :----------------------------------------------------------- |
| `GET`       | `/api/posts`        | Obtiene todos los posts.                  | `(Ninguno)`                                              | `[ { "id": 1, "title": "valor", "content": "valor", "author": "valor" } ]`                  |
| `GET`       | `/api/posts/{id}`   | Obtiene un post por su ID.                 | `(Ninguno)`                                              | ` { "id": 1, "title": "valor", "content": "valor", "author": "valor" } `                    |
| `POST`      | `/api/posts`        | Crea un nuevo post.                        | ` { "title": "valor", "content": "valor", "author": "valor" } `             | ` { "id": 2, "title": "valor", "content": "valor", "author": "valor" } `  |
| `PUT`       | `/api/posts/{id}`   | Actualiza un post existente por su ID.     | ` { "title": "valor", "content": "valor", "author": "valor" } `  | ` { "id": 1, "title": "valor", "content": "valor", "author": "valor" } `              |
| `DELETE`    | `/api/posts/{id}`   | Elimina un post por su ID.                 | `(Ninguno)`                                              | `(Ninguno) - Status 204 No Content`                        |
| `GET`       | `/home`        | home publica                  | `(Ninguno)`                                              |  `(Ninguno)`                  |
| `GET`       | `/admin/home`        | home accesible solo para ROLE_ADMIN                  | `(Ninguno)`                                              |  `(Ninguno)`                  |
| `GET`       | `/user/home`        | home accesible para ROLE_USER                  | `(Ninguno)`                                              |  `(Ninguno)`                  |
| `POST`      | `/authenticate`        | Autentica al usuario                        | ` { "username": "valor", "password": "valor" } `             | ` (Ninguno) - Clave JWT` |
| `POST`      | `/register/user`        | Registra nuevo usuario                        | ` { "username": "valor", "password": "valor", "role": "ROLE_ADMIN" OR "ROLE_USER" } `             | ` (Ninguno) - Clave JWT` |

*Nota sobre Paginación y Ordenamiento:*
Puedes usar parámetros de consulta como `/api/posts?page=0&size=10&sort=title,asc` para paginar y ordenar los resultados.


## Configuración y Ejecución

### Requisitos Previos

* Docker

### Pasos para Ejecutar Localmente

1.  **Clonar el repositorio:**
    ```bash
    git clone https://github.com/RodolfoRadaelli/API-blog.git
    cd tu-repositorio
    ```
2.  **Inicializar aplicación**
	* docker compose up --build -d

3.  **Ejecutar la aplicación**
    * Navegador: http://localhost

**ATENCIÓN: La API tiene 2 usuarios por defecto** Pero puedes crear tu propio admin y usuario.

La API esta disponible en `http://localhost:8080`.

## Cómo Probar la API

La API viene cargada con datos por defecto.
    También vienen cargados con dos usuarios:
        Admin: Admin, 1234, ROLE_ADMIN
        Usuario: Mauro123, 1234, ROLE_USER

## Licencia

Este proyecto está bajo la licencia MIT License. Consulta el archivo `LICENSE` para más detalles.

## Contacto

Rodolfo Radaelli - https://www.linkedin.com/in/rodolfo-claudio-radaelli/
