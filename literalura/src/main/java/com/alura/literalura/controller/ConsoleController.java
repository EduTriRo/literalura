package com.alura.literalura.controller;

import com.alura.literalura.entity.Autor;
import com.alura.literalura.entity.Libro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.service.GutendexService;
import com.alura.literalura.service.LibroService;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Scanner;

@Controller
public class ConsoleController {

    private final GutendexService gutendexService;
    private final LibroService libroService;
    private final AutorRepository autorRepository; // Inyectamos AutorRepository

    public ConsoleController(GutendexService gutendexService, LibroService libroService, AutorRepository autorRepository) {
        this.gutendexService = gutendexService;
        this.libroService = libroService;
        this.autorRepository = autorRepository; // Asignamos el repositorio inyectado
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("__________-----__________-----__________-----__________-----__________");
            System.out.println("Seleccione una opción:");
            System.out.println("1. Buscar libro por título (API Gutendex)");
            System.out.println("2. Mostrar lista de títulos guardados en la base de datos");
            System.out.println("3. Mostrar lista de autores registrados");
            System.out.println("4. Buscar autores vivos entre un rango de años");
            System.out.println("5. Mostrar libros de un autor específico en la base de datos");
            System.out.println("6. Eliminar libros duplicados de la base de datos");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea

            switch (opcion) {
                case 1 -> {
                    System.out.print("Ingrese el título del libro: ");
                    String titulo = scanner.nextLine();
                    gutendexService.buscarLibroPorTitulo(titulo);
                }
                case 2 -> {
                    List<Libro> libros = gutendexService.obtenerLibrosGuardados();
                    if (libros.isEmpty()) {
                        System.out.println("No hay libros guardados en la base de datos.");
                    } else {
                        System.out.println("Lista de libros guardados:");
                        libros.forEach(libro -> System.out.println("- " + libro.getTitulo()));
                    }
                }
                case 3 -> {
                    List<Autor> autores = gutendexService.obtenerAutoresRegistrados();
                    if (autores.isEmpty()) {
                        System.out.println("No hay autores registrados en la base de datos.");
                    } else {
                        System.out.println("Lista de autores registrados:");
                        autores.forEach(autor -> System.out.println("- " + autor.getNombre()));
                    }
                }
                case 4 -> {
                    System.out.print("Ingrese el año de inicio: ");
                    int anioInicio = Integer.parseInt(scanner.nextLine());
                    System.out.print("Ingrese el año de fin: ");
                    int anioFin = Integer.parseInt(scanner.nextLine());

                    List<Autor> autoresVivos = gutendexService.buscarAutoresPorRangoDeAnios(anioInicio, anioFin);
                    if (autoresVivos.isEmpty()) {
                        System.out.println("No hay autores vivos en el rango especificado.");
                    } else {
                        System.out.println("Autores vivos encontrados:");
                        autoresVivos.forEach(autor -> {
                            System.out.println("- " + autor.getNombre() + " (Nacimiento: " +
                                    autor.getAnioNacimiento() + ", Fallecimiento: " +
                                    (autor.getAnioFallecimiento() != null ? autor.getAnioFallecimiento() : "Vivo") + ")");
                        });
                    }
                }
                case 5 -> {
                    System.out.print("Ingrese el nombre del autor: ");
                    String nombreAutor = scanner.nextLine();
                    List<Autor> autores = gutendexService.buscarAutoresConLibros(nombreAutor);

                    if (autores.isEmpty()) {
                        System.out.println("No se encontró ningún autor con el nombre: " + nombreAutor);
                    } else {
                        autores.forEach(autor -> {
                            System.out.println("Libros del autor " + autor.getNombre() + ":");
                            autor.getLibros().forEach(libro -> System.out.println("- " + libro.getTitulo()));
                        });
                    }
                    System.out.println("__________-----__________-----__________-----__________-----__________");
                }


                case 6 -> {
                    System.out.println("Eliminando libros duplicados...");
                    libroService.eliminarDuplicados(); // Usar el servicio correcto
                    System.out.println("Duplicados eliminados correctamente.");
                }
                case 0 -> System.out.println("Saliendo de la aplicación...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }
}
