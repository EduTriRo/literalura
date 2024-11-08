package com.alura.literalura.service;

import com.alura.literalura.entity.Autor;
import com.alura.literalura.entity.Libro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GutendexService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    public void buscarLibrosPorTitulo(String titulo) {
        Optional<Libro> libroOptional = libroRepository.findByTituloContainingIgnoreCase(titulo)
                .stream()
                .findFirst();

        if (libroOptional.isPresent()) {
            Libro libro = libroOptional.get();
            System.out.println("Libro más cercano encontrado:");
            System.out.println("- Título: " + libro.getTitulo());
            System.out.println("- Autor: " + libro.getNombreAutor());
            System.out.println("- Año de publicación: " + (libro.getAnioPublicacion() != null ? libro.getAnioPublicacion() : "Desconocido"));
            System.out.println("- Sinopsis: " + libro.getSinopsis());
        } else {
            System.out.println("No se encontraron resultados para el libro: " + titulo);
        }
    }

    public void buscarAutorPorNombre(String nombreAutor) {
        Optional<Autor> autorOptional = autorRepository.findByNombreContainingIgnoreCase(nombreAutor);

        if (autorOptional.isPresent()) {
            Autor autor = autorOptional.get();
            System.out.println("Autor encontrado:");
            System.out.println("- Nombre: " + autor.getNombre());
            System.out.println("- Año de nacimiento: " + (autor.getAnioNacimiento() != null ? autor.getAnioNacimiento() : "Desconocido"));
            System.out.println("- Año de defunción: " + (autor.getAnioFallecimiento() != null ? autor.getAnioFallecimiento() : "Aún vive"));
            System.out.println("- Libros:");
            for (Libro libro : autor.getLibros()) {
                System.out.println("  - " + libro.getTitulo() + " (" + (libro.getAnioPublicacion() != null ? libro.getAnioPublicacion() : "Año desconocido") + ")");
            }
        } else {
            System.out.println("No se encontraron resultados para el autor: " + nombreAutor);
        }
    }
}
