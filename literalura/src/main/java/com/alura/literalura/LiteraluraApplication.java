package com.alura.literalura;

import com.alura.literalura.controller.ConsoleController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class LiteraluraApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(LiteraluraApplication.class, args);

		// Ejecutar el controlador de consola después de inicializar el contexto
		ConsoleController consoleController = context.getBean(ConsoleController.class);
		consoleController.run();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
