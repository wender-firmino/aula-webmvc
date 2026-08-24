package com.curso.app.service;

import com.curso.app.model.Cliente;
import com.curso.app.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// Camada de regras de negocio. O Controller nunca fala direto com o Repository:
// ele passa sempre pelo Service.
@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente cadastrar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
}
