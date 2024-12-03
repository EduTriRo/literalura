package com.alura.literalura.controller;

import com.alura.literalura.entity.Autor;
import com.alura.literalura.entity.Libro;
import com.alura.literalura.service.GutendexService;
import com.alura.literalura.service.LibroService;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Scanner;

@Controller
public class ConsoleController {

    private final GutendexService gutendexService;
    private final LibroService libroService;

    public ConsoleController(GutendexService gutendexService, LibroService libroService) {
        this.gutendexService = gutendexService;
        this.libroService = libroService;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            try {
                System.out.println("__________-----__________-----__________-----__________-----__________");
                System.out.println("Seleccione una opción:");
                System.out.println("1. Buscar libro por título o autor (API Gutendex)");
                System.out.println("2. Mostrar lista de títulos guardados en la base de datos");
                System.out.println("3. Mostrar lista de autores registrados");
                System.out.println("4. Buscar autores vivos entre un rango de años");
                System.out.println("5. Mostrar libros de un autor específico en la base de datos (ingrese solo nombre o apellido)");
                System.out.println("6. Eliminar libros duplicados de la base de datos");
                System.out.println("0. Salir");
                System.out.print("Opción: ");
                String input = scanner.nextLine();

                // Validación de entrada como número
                if (!input.matches("\\d+")) {
                    System.out.println("Por favor, ingrese un número válido.");
                    continue;
                }

                int opcion = Integer.parseInt(input);

                switch (opcion) {
                    case 1 -> {
                        System.out.print("Ingrese el título o autor del libro: ");
                        String titulo = scanner.nextLine();
                        gutendexService.buscarLibroPorTitulo(titulo);
                    }
                    case 2 -> {
                        List<Libro> libros = gutendexService.obtenerLibrosGuardadosOrdenados();
                        if (libros.isEmpty()) {
                            System.out.println("No hay libros guardados en la base de datos.");
                        } else {
                            System.out.println("Lista de libros guardados:");
                            libros.forEach(libro -> System.out.println("- " + libro.getTitulo()));
                        }
                    }
                    case 3 -> {
                        System.out.println("Lista de autores registrados:");
                        List<Autor> autores = gutendexService.obtenerAutoresRegistrados();
                        autores.forEach(autor -> {
                            String nacimiento = (autor.getAnioNacimiento() != null) ? autor.getAnioNacimiento().toString() : "Desconocido";
                            String fallecimiento = (autor.getAnioFallecimiento() != null) ? autor.getAnioFallecimiento().toString() : "Vivo";
                            System.out.println("- (" + nacimiento + " - " + fallecimiento + ") " + autor.getNombre());
                        });
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
                                System.out.println("- ("
                                        + (autor.getAnioNacimiento() != null ? autor.getAnioNacimiento() : "Desconocido")
                                        + " - "
                                        + (autor.getAnioFallecimiento() != null ? autor.getAnioFallecimiento() : "Vivo")
                                        + ") "
                                        + autor.getNombre());
                            });
                        }
                    }
                    case 5 -> {
                        System.out.print("Ingrese una sola palabra del nombre o apellido del autor: ");
                        String entrada = scanner.nextLine().trim();

                        // Validar que la entrada contenga solo una palabra
                        if (entrada.contains(" ")) {
                            System.out.println("Por favor, ingrese solo una palabra (nombre o apellido) para realizar la búsqueda.");
                        } else {
                            List<Autor> autores = gutendexService.buscarAutoresConLibrosPorNombre(entrada.toLowerCase());

                            if (autores.isEmpty()) {
                                System.out.println("No se encontraron autores exactos con la palabra: " + entrada);
                                System.out.println("Buscando coincidencias parciales...");
                                autores = gutendexService.buscarAutoresPorNombre(entrada.toLowerCase());

                                if (autores.isEmpty()) {
                                    System.out.println("No se encontraron coincidencias parciales.");
                                } else {
                                    System.out.println("Autores sugeridos:");
                                    autores.forEach(autor -> System.out.println("- " + autor.getNombre()));
                                }
                            } else {
                                autores.forEach(autor -> {
                                    System.out.println("Libros del autor " + autor.getNombre() + ":");
                                    autor.getLibros().forEach(libro -> System.out.println("- " + libro.getTitulo()));
                                });
                            }
                        }
                    }
                    case 6 -> {
                        System.out.println("Eliminando libros duplicados...");
                        libroService.eliminarDuplicados();
                        System.out.println("Duplicados eliminados correctamente.");
                    }
                    case 0 -> {
                        System.out.println("Saliendo de la aplicación...");
                        salir = true;
                    }
                    default -> System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            } catch (Exception e) {
                System.out.println("Ocurrió un error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
