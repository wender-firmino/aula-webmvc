package com.curso.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

// Aula 3: o POJO da Aula 2 virou uma entidade JPA de verdade.
// @Entity + @Id + @GeneratedValue sao o minimo para o Hibernate mapear esta classe para uma tabela.
// Aula 5 (Bloco 3): @Data + @NoArgsConstructor + @AllArgsConstructor substituem os
// getters/setters/toString/construtores que antes eram escritos a mao (62 -> 14 linhas).
@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_completo", length = 120, nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    // mappedBy indica que quem "e dono" da relacao (guarda a chave estrangeira) e o Pedido.
    // cascade = PERSIST: salvar um Cliente com Pedidos novos ja salva os Pedidos junto.
    // orphanRemoval = true: remover um Pedido da lista tambem apaga ele do banco.
    //
    // Cuidado (Aula 5): @Data gera toString/equals/hashCode com TODOS os atributos.
    // Sem @ToString.Exclude / @EqualsAndHashCode.Exclude aqui, Cliente.toString() imprimiria
    // pedidos -> Pedido.toString() imprimiria cliente -> loop infinito (StackOverflowError),
    // o mesmo problema visto na Aula 4 com toString/equals/hashCode manuais.
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Pedido> pedidos = new ArrayList<>();

    public Cliente(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    // Construtor com id: util em testes que simulam um Cliente ja salvo no banco
    // (o id "de verdade" continua sendo gerado pelo @GeneratedValue em producao).
    public Cliente(Long id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }
}
