package com.alura.literalura.controller;

import com.alura.literalura.service.GutendexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Controller
public class ConsoleController {

    @Autowired
    private GutendexService gutendexService;

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Buscar libro por título");
            System.out.println("2. Buscar autor por nombre");
            System.out.println("0. Salir");

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    System.out.print("Ingrese el título del libro: ");
                    String titulo = scanner.nextLine();
                    gutendexService.buscarLibrosPorTitulo(titulo);
                    break;
                case "2":
                    System.out.print("Ingrese el nombre del autor: ");
                    String nombreAutor = scanner.nextLine();
                    gutendexService.buscarAutorPorNombre(nombreAutor);
                    break;
                case "0":
                    System.out.println("Saliendo de la aplicación...");
                    return;
                default:
                    System.out.println("Opción no válida. Por favor, ingrese un número.");
            }
        }
    }
}
