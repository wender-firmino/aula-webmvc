package com.curso.app.controller;

import com.curso.app.model.Pedido;
import com.curso.app.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Aula 7 (slide 6): @RequiredArgsConstructor no lugar do construtor manual.
@RestController
@RequestMapping("/clientes/{clienteId}/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

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
