package com.curso.app;

import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

// @SpringBootApplication liga as tres anotacoes que "ativam" o Spring Boot:
// @SpringBootConfiguration + @EnableAutoConfiguration + @ComponentScan
@SpringBootApplication
public class MvcAula2Application {

	public static void main(String[] args) throws SQLException {
		// Aula 5 (2.3): sobe o processo TCP do H2 (porta 9092) ANTES do Spring Boot,
		// para que o datasource (jdbc:h2:tcp://localhost:9092/./data/mvcdb) e o
		// H2 Console consigam se conectar ao mesmo servidor desde o primeiro instante.
		Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092").start();

		SpringApplication.run(MvcAula2Application.class, args);
	}

}
