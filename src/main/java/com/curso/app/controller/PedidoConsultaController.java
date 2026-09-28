package com.curso.app.controller;

import com.curso.app.model.Pedido;
import com.curso.app.service.PedidoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Aula 5 (2.4): endpoint separado de PedidoController porque esta consulta
// nao depende de um clienteId na URL, e sim do nome do cliente (parametro da HQL com JOIN).
@RestController
@RequestMapping("/pedidos")
public class PedidoConsultaController {

    private final PedidoService pedidoService;

    public PedidoConsultaController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // GET http://localhost:8080/pedidos/buscar?nomeCliente=Ana Souza
    // Usa a consulta HQL com JOIN: SELECT p FROM Pedido p JOIN p.cliente c WHERE c.nome = :nomeCliente ORDER BY p.data DESC
    @GetMapping("/buscar")
    public List<Pedido> buscarPedidosDoCliente(@RequestParam String nomeCliente) {
        return pedidoService.buscarPedidosDoCliente(nomeCliente);
    }
}
