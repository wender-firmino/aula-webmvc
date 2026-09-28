# mvc-aula2 — projeto de exemplo (Aula 6 · ponto de partida 21/09/2026)

Projeto Maven + Spring Boot que evolui ao longo das aulas da Unidade 1 e 2.
Este é o **mesmo projeto** desde a Aula 2 — cada aula estende o código
anterior em vez de criar um projeto novo.

Esta branch é o **ponto de partida da Aula 6 (21/09)**. O código é
idêntico ao gabarito da Aula 5 (`aula5-resposta`): a aula de 21/09 cobriu
só teoria e o teste do Controller já existente (GET no navegador, POST via
curl) — não houve código novo para versionar.

## Linha do tempo do projeto

- **Aula 2 (17/08)** — esqueleto Spring Boot, pacotes por camada, `Cliente`
  como POJO simples, "repository" artesanal em lista de memória.
- **Aula 3 (24/08)** — `Cliente` vira entidade JPA, nova entidade `Pedido`
  com relacionamento **1:N**, `ClienteRepository` vira `JpaRepository`.
- **Aula 4 (31/08)** — anotações JPA aprofundadas (`@Table`/`@Column`),
  `cascade`/`orphanRemoval`, `FetchType`, cuidado com `toString()` em
  relacionamentos bidirecionais.
- **Aula 5 (14/09)** — o que muda nesta pasta:
  - **2.3 H2 em modo servidor** — `application.properties` passa a apontar
    para `jdbc:h2:tcp://localhost:9092/./data/mvcdb`; `MvcAula2Application`
    sobe o servidor TCP do H2 antes do Spring Boot, para a aplicação e o
    H2 Console falarem com o mesmo processo, na mesma porta.
  - **2.4 HQL com `@Query`** — `ClienteRepository.buscarPorTrechoDoNome` e
    `PedidoRepository.buscarPedidosDoCliente` (com `JOIN`), expostas em
    `GET /clientes/buscar?trecho=...` e `GET /pedidos/buscar?nomeCliente=...`.
  - **Lombok** — `Cliente` e `Pedido` trocam getters/setters/toString/
    construtores manuais por `@Data + @NoArgsConstructor + @AllArgsConstructor`,
    com `@ToString.Exclude` / `@EqualsAndHashCode.Exclude` na associação
    (evita o loop infinito entre as duas entidades).

## Estrutura de pacotes (por camada)

```
com.curso.app
├── model        Cliente.java, Pedido.java — entidades JPA enxutas com Lombok
├── repository   ClienteRepository, PedidoRepository — JpaRepository + @Query (HQL)
├── service      ClienteService, PedidoService — regra de negócio
├── controller   ClienteController, PedidoController, PedidoConsultaController
├── DataSeeder.java — popula registros de exemplo só se o banco estiver vazio
└── MvcAula2Application.java — sobe o servidor TCP do H2 antes do Spring Boot
```

## Como rodar

1. Abra a pasta na IDE — ela reconhece o `pom.xml` automaticamente.
2. Aguarde o Maven baixar as dependências, **incluindo o Lombok**.
3. **Instale o plugin/extensão Lombok na IDE** (IntelliJ: Settings → Plugins;
   VS Code: extensão "Lombok Annotations Support") e reinicie a IDE — sem
   isso, a IDE mostra erro em `getNome()`/`getEmail()` mesmo com o projeto
   compilando normalmente pelo Maven.
4. Rode a classe `MvcAula2Application`.
5. Confira no log as duas linhas que provam o modo servidor:
   ```
   H2 console available at '/h2-console'. Database available at 'jdbc:h2:tcp://localhost:9092/./data/mvcdb'
   HikariPool-1 - Start completed.
   ```

Ou, pelo terminal, dentro da pasta do projeto:

```
mvn spring-boot:run
```

Se a porta 9092 estiver ocupada (execução anterior ainda rodando), encerre-a
antes de rodar de novo — é o erro "port may be in use" citado na aula.

## Testando o exemplo

- `http://localhost:8080/clientes` → GET, lista os 4 clientes semeados pelo
  `DataSeeder` (Ana Souza, Bruno Lima, Joaquim Silva, Carla Silveira).
- `http://localhost:8080/clientes/buscar?trecho=Silva` → GET, usa a consulta
  HQL com `LIKE %:trecho%` — repare que **Carla Silveira também aparece**
  (o `LIKE` é literal, não entende sobrenomes).
- `http://localhost:8080/pedidos/buscar?nomeCliente=Joaquim%20Silva` → GET,
  usa a consulta HQL com `JOIN`, ordenada por data decrescente.
- `http://localhost:8080/clientes/1/pedidos` → GET, lista os pedidos do
  cliente de id 1.
- `http://localhost:8080/h2-console` → conecte com a URL
  `jdbc:h2:tcp://localhost:9092/./data/mvcdb`, usuário `sa`, senha em
  branco — rode `SELECT * FROM CLIENTES` e confirme que são os mesmos dados
  vistos pela aplicação.
- Depois de parar e subir a aplicação de novo, os dados **continuam lá**
  (arquivo `data/mvcdb.mv.db`) — o `DataSeeder` só semeia se o banco
  estiver vazio.

Para cadastrar um cliente novo:

```
curl -X POST http://localhost:8080/clientes -H "Content-Type: application/json" -d "{\"nome\":\"Carla Dias\",\"email\":\"carla.dias@exemplo.com\"}"
```

## Comandos Maven do dia a dia

```
mvn clean      # remove a pasta target
mvn compile    # compila o código-fonte
mvn test       # roda os testes automatizados (usam H2 em memória, não o modo servidor)
mvn package    # empacota a aplicação (gera o .jar em target/)
mvn spring-boot:run   # roda a aplicação sem precisar da IDE
```

## Aula 6 (S6 · 21/09)

Unidade 3 — Camada de Controle com Spring Web aprofundada: `@RestController`,
mapeamento de rotas e o caminho completo da requisição até o repositório.
Testar o `ClienteController` já existente: GET `/clientes` no navegador,
POST via curl, e o desafio de criar `GET /clientes/{id}` com 404.
Simulado 1 (Unidades 1 e 2) na semana de 21 a 26/09 — a aula teve só os 50
minutos iniciais de conteúdo, o restante foi liberado para o simulado.

## Próxima aula (S7 · 28/09)

Retomada do Lombok em Controller/Service (pendente de 14/09) + Unidade 3:
camada de Serviço (`@Service`, `@Transactional`) e Spring Security
(autenticação com BCrypt, autorização por papel).
