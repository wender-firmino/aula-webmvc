package com.curso.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication liga as tres anotacoes que "ativam" o Spring Boot:
// @SpringBootConfiguration + @EnableAutoConfiguration + @ComponentScan
@SpringBootApplication
public class MvcAula2Application {

	public static void main(String[] args) {
		SpringApplication.run(MvcAula2Application.class, args);
	}

}
