# mvc-aula2 — esqueleto Spring Boot (Aula 2)

Projeto gerado pelo [Spring Initializr](https://start.spring.io) com as
dependências **Spring Web**, **Spring Data JPA**, **H2 Database** e
**Spring Boot DevTools**. Ainda não há nenhuma classe de negócio — este é o
ponto de partida sobre o qual as próximas aulas vão evoluir.

## Como rodar

1. Abra a pasta `mvc-aula2` na IDE (IntelliJ, Eclipse ou VS Code) — ela
   reconhece o `pom.xml` automaticamente.
2. Aguarde o Maven baixar as dependências (primeira vez demora um pouco).
3. Rode a classe `MvcAula2Application` (botão direito → Run, ou o play verde).
4. Confira no log: `Tomcat started on port 8080 (http)`.

Ou, pelo terminal, dentro da pasta do projeto:

```
mvn spring-boot:run
```

## Comandos Maven do dia a dia

```
mvn clean      # remove a pasta target
mvn compile    # compila o código-fonte
mvn test       # roda os testes automatizados
mvn package    # empacota a aplicação (gera o .jar em target/)
mvn spring-boot:run   # roda a aplicação sem precisar da IDE
```
