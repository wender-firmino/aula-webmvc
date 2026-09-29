package com.curso.app.service;

import com.curso.app.model.Cliente;
import com.curso.app.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

// Camada de regras de negocio. O Controller nunca fala direto com o Repository:
// ele passa sempre pelo Service.
// Aula 7 (slide 8): @RequiredArgsConstructor tambem simplifica o Service.
@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    // Aula 7 (slide 17, 3.2): regra de negocio - e-mail duplicado e recusado
    // aqui, nao no Repository (que so salva o que o Service mandar) nem no
    // Controller. clienteRepository.existsByEmail e um Query Method (slide 17).
    public Cliente cadastrar(Cliente cliente) {
        boolean existe = clienteRepository.existsByEmail(cliente.getEmail());
        if (existe) {
            throw new IllegalArgumentException("E-mail ja cadastrado");
        }
        return clienteRepository.save(cliente);
    }

    // Aula 5 (2.4): expoe a consulta HQL do ClienteRepository para o Controller.
    public List<Cliente> buscarPorTrechoDoNome(String trecho) {
        return clienteRepository.buscarPorTrechoDoNome(trecho);
    }

    // Aula 7 (slide 54, 57-58): endpoint que o SecurityConfig protege com
    // hasRole("ADMIN") - deleteById e um metodo pronto do JpaRepository.
    public void excluir(Long id) {
        clienteRepository.deleteById(id);
    }
}
