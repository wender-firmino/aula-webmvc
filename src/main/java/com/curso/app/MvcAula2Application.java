package com.curso.app;

import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

// @SpringBootApplication liga as tres anotacoes que "ativam" o Spring Boot:
// @SpringBootConfiguration + @EnableAutoConfiguration + @ComponentScan
@SpringBootApplication
public class MvcAula2Application {

	public static void main(String[] args) {
		// Aula 5 (2.3): sobe o processo TCP do H2 (porta 9092) ANTES do Spring Boot,
		// para que o datasource (jdbc:h2:tcp://localhost:9092/./data/mvcdb) e o
		// H2 Console consigam se conectar ao mesmo servidor desde o primeiro instante.
		// Precisa continuar aqui no main() (e nao como @Bean do Spring): o Spring
		// nao garante que um @Bean rode antes do datasource tentar conectar.
		try {
			// -ifNotExists: por seguranca, o H2 recusa criar um banco novo a
			// pedido de um cliente remoto - so permite ABRIR um banco que ja
			// existe. Como ./data/mvcdb.mv.db ainda nao existe na primeira vez,
			// essa flag autoriza o servidor a cria-lo.
			Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092", "-ifNotExists").start();
		} catch (SQLException portaJaEmUso) {
			// O DevTools reinicia a aplicacao dentro da MESMA JVM (thread
			// "restartedMain"), entao o main() roda de novo a cada restart -
			// mas o servidor H2 da execucao anterior continua de pe (ele nao
			// faz parte do contexto do Spring, entao ninguem o fecha). Se a
			// porta 9092 ja estiver em uso, e esse servidor anterior; podemos
			// seguir e reaproveita-lo em vez de travar o restart.
			System.out.println("Servidor H2 (porta 9092) ja esta em execucao - reaproveitando.");
		}

		SpringApplication.run(MvcAula2Application.class, args);
	}

}
