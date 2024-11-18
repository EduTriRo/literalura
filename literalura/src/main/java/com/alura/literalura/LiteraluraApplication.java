package com.alura.literalura;

import com.alura.literalura.controller.ConsoleController;
import com.alura.literalura.service.LibroService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class LiteraluraApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(LiteraluraApplication.class, args);

        // Eliminar duplicados al iniciar la aplicación
        LibroService libroService = context.getBean(LibroService.class);
        libroService.eliminarDuplicados();

        // Ejecutar el controlador de consola después de inicializar el contexto
        ConsoleController consoleController = context.getBean(ConsoleController.class);
        consoleController.run();
    }
}
