package com.curso.app.repository;

import com.curso.app.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Aula 5 (2.4): JOIN em HQL navega pela associacao ja mapeada (p.cliente,
    // um @ManyToOne da Aula 4) - nao pela chave estrangeira cliente_id do banco.
    // Se a associacao nao existisse na classe Pedido, este JOIN nao existiria em HQL.
    @Query("SELECT p FROM Pedido p JOIN p.cliente c " +
            "WHERE c.nome = :nomeCliente ORDER BY p.data DESC")
    List<Pedido> buscarPedidosDoCliente(@Param("nomeCliente") String nomeCliente);
}
