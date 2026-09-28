package com.curso.app.service;

import com.curso.app.model.Cliente;
import com.curso.app.model.Pedido;
import com.curso.app.repository.ClienteRepository;
import com.curso.app.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;

    public PedidoService(PedidoRepository pedidoRepository, ClienteRepository clienteRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public Pedido cadastrar(Long clienteId, Pedido pedido) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente nao encontrado: " + clienteId));
        pedido.setCliente(cliente);
        return pedidoRepository.save(pedido);
    }

    // Aula 5 (2.4): expoe a consulta HQL com JOIN do PedidoRepository para o Controller.
    public List<Pedido> buscarPedidosDoCliente(String nomeCliente) {
        return pedidoRepository.buscarPedidosDoCliente(nomeCliente);
    }
}
