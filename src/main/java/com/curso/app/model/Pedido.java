package com.curso.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

// Aula 3/4: lado "muitos" do relacionamento 1:N com Cliente.
// @ManyToOne + @JoinColumn sao o que de fato cria a coluna de chave estrangeira (cliente_id) no banco.
@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private BigDecimal valor;

    // @JsonIgnore evita um segundo problema (alem do toString): sem ele, o Jackson
    // serializaria cliente -> pedidos -> cliente -> pedidos ... em loop infinito ao gerar o JSON.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id", nullable = false)
    @JsonIgnore
    private Cliente cliente;

    public Pedido() {
    }

    public Pedido(LocalDate data, BigDecimal valor, Cliente cliente) {
        this.data = data;
        this.valor = valor;
        this.cliente = cliente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    // Nao incluimos "cliente" aqui de proposito: evita o loop infinito com Cliente.toString().
    @Override
    public String toString() {
        return "Pedido{id=" + id + ", data=" + data + ", valor=" + valor + "}";
    }
}
