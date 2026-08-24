package com.curso.app.controller;

import com.curso.app.model.Cliente;
import com.curso.app.service.ClienteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Camada Controller: recebe a requisicao HTTP, aciona o Model (via Service)
// e devolve a resposta. Este endpoint demonstra o fluxo visto no slide 7.
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // GET http://localhost:8080/clientes
    @GetMapping
    public List<Cliente> listar() {
        return clienteService.listarTodos();
    }

    // POST http://localhost:8080/clientes  (corpo em JSON: {"nome": "...", "email": "..."})
    @PostMapping
    public Cliente cadastrar(@RequestBody Cliente cliente) {
        return clienteService.cadastrar(cliente);
    }
}
