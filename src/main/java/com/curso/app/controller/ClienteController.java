package com.curso.app.controller;

import com.curso.app.model.Cliente;
import com.curso.app.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Camada Controller: recebe a requisicao HTTP, aciona o Model (via Service)
// e devolve a resposta. Este endpoint demonstra o fluxo visto no slide 7.
// Aula 7 (slide 6): @RequiredArgsConstructor troca o construtor manual pelo
// gerado pelo Lombok, a partir do atributo final abaixo.
@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

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

    // Aula 5 (2.4): GET http://localhost:8080/clientes/buscar?trecho=Silva
    // Usa a consulta HQL @Query("SELECT c FROM Cliente c WHERE c.nome LIKE %:trecho%").
    @GetMapping("/buscar")
    public List<Cliente> buscarPorTrechoDoNome(@RequestParam String trecho) {
        return clienteService.buscarPorTrechoDoNome(trecho);
    }

    // Aula 7 (slide 54, 57-58): DELETE http://localhost:8080/clientes/1
    // O SecurityConfig exige papel ADMIN para este metodo (hasRole("ADMIN")).
    // Sem o papel certo, o Spring Security devolve 403 antes mesmo de chegar aqui.
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        clienteService.excluir(id);
    }
}
