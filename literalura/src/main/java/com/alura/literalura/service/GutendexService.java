package com.alura.literalura.service;

import com.alura.literalura.entity.Autor;
import com.alura.literalura.entity.Libro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GutendexService {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;
    private final RestTemplate restTemplate;
    private static final String GUTENDEX_API_URL = "https://gutendex.com/books";

    public GutendexService(LibroRepository libroRepository, AutorRepository autorRepository, RestTemplate restTemplate) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
        this.restTemplate = restTemplate;
    }

    @Transactional
    public void buscarLibroPorTitulo(String titulo) {
        System.out.println("Buscando en la API...");
        try {
            String encodedTitulo = URLEncoder.encode(titulo.trim(), StandardCharsets.UTF_8);
            String uri = UriComponentsBuilder.fromHttpUrl(GUTENDEX_API_URL)
                    .queryParam("search", encodedTitulo)
                    .toUriString();

            ResponseEntity<GutendexResponse> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    null,
                    GutendexResponse.class
            );

            if (response.getStatusCode().is2xxSuccessful() &&
                    response.getBody() != null &&
                    !response.getBody().getResults().isEmpty()) {

                GutendexResponse.Book book = response.getBody().getResults().get(0);
                Libro libro = convertirBookALibro(book);

                guardarLibroConAutor(libro); // Aseguramos la persistencia de autores y libros
                imprimirLibro(libro);
            } else {
                System.out.println("No se encontraron resultados en la API.");
            }
        } catch (Exception e) {
            System.err.println("Error al realizar la búsqueda: " + e.getMessage());
        }
    }

    @Transactional
    public void guardarLibroConAutor(Libro libro) {
        // Verificar y guardar el autor si no existe
        if (libro.getAutor() != null) {
            Autor autor = libro.getAutor();
            Autor autorExistente = autorRepository.findByNombreIgnoreCase(autor.getNombre());
            if (autorExistente == null) {
                autorRepository.save(autor); // Guardar el autor si no existe
            } else {
                libro.setAutor(autorExistente); // Reasignar autor existente al libro
            }
        }

        // Guardar el libro si no existe
        if (!libroRepository.existsByTituloIgnoreCase(libro.getTitulo())) {
            libroRepository.save(libro);
            System.out.println("Libro guardado en la base de datos.");
        } else {
            System.out.println("El libro ya existe en la base de datos.");
        }
    }

    private Libro convertirBookALibro(GutendexResponse.Book book) {
        Libro libro = new Libro();
        libro.setTitulo(book.getTitle());
        libro.setCantidadDescargas(book.getDownloadCount());
        libro.setIdioma(book.getLanguages().isEmpty() ? "Desconocido" : book.getLanguages().get(0));
        libro.setSinopsis("Sinopsis no disponible en la API");

        if (!book.getAuthors().isEmpty()) {
            Autor autor = convertirPersonAAutor(book.getAuthors().get(0));
            libro.setAutor(autor);
        }
        return libro;
    }

    private Autor convertirPersonAAutor(GutendexResponse.Person person) {
        Autor autor = new Autor();
        autor.setNombre(person.getName());
        autor.setAnioNacimiento(person.getBirthYear());
        autor.setAnioFallecimiento(person.getDeathYear());
        return autor;
    }

    private void imprimirLibro(Libro libro) {
        System.out.println("Libro más relevante encontrado:");
        System.out.println("- Título: " + libro.getTitulo());
        System.out.println("- Autor: " + (libro.getAutor() != null ? libro.getAutor().getNombre() : "Desconocido"));
        System.out.println("- Año de publicación: " + (libro.getAnioPublicacion() != null ? libro.getAnioPublicacion() : "Desconocido"));
        System.out.println("- Idioma: " + libro.getIdioma());
    }

    public List<Libro> obtenerLibrosGuardados() {
        return libroRepository.findAll();
    }

    public List<Autor> obtenerAutoresRegistrados() {
        return autorRepository.findAll();
    }

    public List<Autor> buscarAutoresPorRangoDeAnios(int anioInicio, int anioFin) {
        return autorRepository.findAll().stream()
                .filter(autor -> autor.getAnioNacimiento() != null && autor.getAnioNacimiento() >= anioInicio)
                .filter(autor -> (autor.getAnioFallecimiento() == null || autor.getAnioFallecimiento() > anioFin))
                .collect(Collectors.toList());
    }

    public void eliminarDuplicados() {
        libroRepository.eliminarDuplicados();
    }
}
