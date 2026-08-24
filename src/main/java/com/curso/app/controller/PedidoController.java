package com.curso.app.controller;

import com.curso.app.model.Pedido;
import com.curso.app.service.PedidoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes/{clienteId}/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // GET http://localhost:8080/clientes/1/pedidos  (lista todos os pedidos cadastrados)
    @GetMapping
    public List<Pedido> listar() {
        return pedidoService.listarTodos();
    }

    // POST http://localhost:8080/clientes/1/pedidos  (corpo: {"data": "2026-08-24", "valor": 150.00})
    @PostMapping
    public Pedido cadastrar(@PathVariable Long clienteId, @RequestBody Pedido pedido) {
        return pedidoService.cadastrar(clienteId, pedido);
    }
}
