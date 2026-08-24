# mvc-aula2 — projeto de exemplo (Aulas 2, 3 e 4)

Projeto Maven + Spring Boot que evolui ao longo das aulas da Unidade 1 e 2.
Este é o **mesmo projeto** desde a Aula 2 — cada aula estende o código
anterior em vez de criar um projeto novo.

## Linha do tempo do projeto

- **Aula 2 (17/08)** — esqueleto Spring Boot, pacotes por camada, `Cliente`
  como POJO simples, "repository" artesanal em lista de memória.
- **Aula 3 (24/08)** — `Cliente` vira entidade JPA (`@Entity`, `@Id`,
  `@GeneratedValue`), nova entidade `Pedido` com relacionamento **1:N**
  (`@OneToMany` / `@ManyToOne` + `@JoinColumn`), `ClienteRepository` vira
  uma interface `JpaRepository` de verdade.
- **Aula 4 (31/08)** — anotações JPA aprofundadas: `@Table`/`@Column`,
  `cascade`/`orphanRemoval`, `FetchType` (LAZY x EAGER), e cuidado com
  `toString()` em relacionamentos bidirecionais.

## Estrutura de pacotes (por camada)

```
com.curso.app
├── model        Cliente.java, Pedido.java — entidades JPA com relacionamento 1:N
├── repository   ClienteRepository, PedidoRepository — interfaces JpaRepository
├── service      ClienteService, PedidoService — regra de negócio
├── controller   ClienteController, PedidoController — recebem a requisição HTTP
└── DataSeeder.java — popula alguns registros de exemplo ao iniciar a aplicação
```

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

## Testando o exemplo

- `http://localhost:8080/clientes` → GET, lista os clientes (2 pré-cadastrados
  pelo `DataSeeder` a cada reinício, já que o banco é em memória).
- `http://localhost:8080/clientes/1/pedidos` → GET, lista os pedidos
  cadastrados (associados ao cliente de id 1).
- `http://localhost:8080/h2-console` → console web do banco H2 em memória
  (JDBC URL: `jdbc:h2:mem:testdb`, usuário `sa`, senha em branco) — rode
  `SELECT * FROM CLIENTES` e `SELECT * FROM PEDIDOS` para ver a coluna
  `cliente_id` (a chave estrangeira criada pelo `@JoinColumn`).
- `http://localhost:8080` → dá "Whitelabel Error Page" (404) — é esperado.

Para cadastrar um cliente novo:

```
curl -X POST http://localhost:8080/clientes -H "Content-Type: application/json" -d "{\"nome\":\"Carla Dias\",\"email\":\"carla.dias@exemplo.com\"}"
```

Para cadastrar um pedido para o cliente de id 1:

```
curl -X POST http://localhost:8080/clientes/1/pedidos -H "Content-Type: application/json" -d "{\"data\":\"2026-08-24\",\"valor\":199.90}"
```

## Comandos Maven do dia a dia

```
mvn clean      # remove a pasta target
mvn compile    # compila o código-fonte
mvn test       # roda os testes automatizados
mvn package    # empacota a aplicação (gera o .jar em target/)
mvn spring-boot:run   # roda a aplicação sem precisar da IDE
```

## Próximo passo (Aula 5 · 2.3)

Trocar o H2 em memória por H2 como **servidor**, com os dados persistidos em
arquivo entre execuções, e aprofundar o H2 Console com consultas SQL
personalizadas (HQL, na Aula 5 seguinte — 2.4).
