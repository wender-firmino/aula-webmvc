package com.curso.app.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

// Aula 3: o POJO da Aula 2 virou uma entidade JPA de verdade.
// @Entity + @Id + @GeneratedValue sao o minimo para o Hibernate mapear esta classe para uma tabela.
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    // mappedBy indica que quem "e dono" da relacao (guarda a chave estrangeira) e o Pedido.
    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos = new ArrayList<>();

    public Cliente() {
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    @Override
    public String toString() {
        return "Cliente{id=" + id + ", nome='" + nome + "', email='" + email + "', pedidos=" + pedidos + "}";
    }
}
