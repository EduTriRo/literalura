# Literalura

**Literalura** es una aplicación de catálogo de libros construida con **Spring Boot** y **PostgreSQL** que permite buscar y almacenar información de libros y autores, utilizando la API de Gutendex como fuente de datos.

Este proyecto fue desarrollado como parte de un **challenge de los cursos de Alura Latam** y contó con el apoyo de **ChatGPT de OpenAI**.

## Características

- **Búsqueda de libros**: Realiza búsquedas de libros por título y muestra coincidencias parciales.
- **Búsqueda de autores**: Busca autores por nombre y muestra información detallada.
- **Almacenamiento**: Guarda automáticamente las búsquedas exitosas en una base de datos para futuras referencias.
- **Interfaz de consola**: La aplicación se ejecuta en la consola, ofreciendo una experiencia amigable para consultas y visualización de resultados.

## Estructura de Datos

Al buscar un libro o autor, se muestran los siguientes datos:

### Libro
- **Título**
- **Autor** (si está disponible)
- **Año de publicación**
- **Sinopsis** (si está disponible)

### Autor
- **Nombre**
- **Año de nacimiento**
- **Año de fallecimiento** (si aplica, o indica "Aún vive")
- **Libros**: Lista de libros asociados al autor.

## Requisitos

- **Java 17** o superior
- **PostgreSQL** (asegúrate de tener una base de datos llamada `literalura_db` y credenciales configuradas)
- **Spring Boot 3.3.5**
- **API de Gutendex** como fuente de datos externos.

## Configuración

Asegúrate de que los siguientes parámetros en el archivo `application.properties` estén configurados adecuadamente:

```properties
spring.datasource.url=jdbc:postgresql://${DB_HOST}/literalura_db
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true
spring.main.web-application-type=none
