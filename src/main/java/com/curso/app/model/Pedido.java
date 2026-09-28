package com.curso.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

// Aula 3/4: lado "muitos" do relacionamento 1:N com Cliente.
// @ManyToOne + @JoinColumn sao o que de fato cria a coluna de chave estrangeira (cliente_id) no banco.
// Aula 5 (Bloco 3): @Data + @NoArgsConstructor + @AllArgsConstructor no lugar do
// codigo repetitivo escrito a mao.
@Entity
@Table(name = "pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private BigDecimal valor;

    // @JsonIgnore evita o loop infinito na serializacao JSON (cliente -> pedidos -> cliente -> ...).
    // @ToString.Exclude / @EqualsAndHashCode.Exclude evitam o mesmo loop no toString/equals/hashCode
    // que o @Data geraria por padrao ao incluir "cliente".
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Cliente cliente;

    public Pedido(LocalDate data, BigDecimal valor, Cliente cliente) {
        this.data = data;
        this.valor = valor;
        this.cliente = cliente;
    }
}
