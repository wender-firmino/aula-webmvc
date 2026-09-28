package com.curso.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

// Aula 3: lado "muitos" do relacionamento 1:N com Cliente.
// @ManyToOne + @JoinColumn sao o que de fato cria a coluna de chave estrangeira (cliente_id) no banco.
@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data;

    private BigDecimal valor;

    // @JsonIgnore evita um segundo problema (alem do toString): sem ele, o Jackson
    // serializaria cliente -> pedidos -> cliente -> pedidos ... em loop infinito ao gerar o JSON.
    @ManyToOne
    @JoinColumn(name = "cliente_id")
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

    @Override
    public String toString() {
        return "Pedido{id=" + id + ", data=" + data + ", valor=" + valor + ", cliente=" + cliente + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pedido)) return false;
        Pedido pedido = (Pedido) o;
        return java.util.Objects.equals(id, pedido.id)
                && java.util.Objects.equals(data, pedido.data)
                && java.util.Objects.equals(valor, pedido.valor)
                && java.util.Objects.equals(cliente, pedido.cliente);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id, data, valor, cliente);
    }
}
