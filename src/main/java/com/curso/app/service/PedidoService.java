package com.curso.app.service;

import com.curso.app.model.Cliente;
import com.curso.app.model.Pedido;
import com.curso.app.repository.ClienteRepository;
import com.curso.app.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Aula 7 (slide 20): @RequiredArgsConstructor tambem funciona com mais de
// uma dependencia final - o construtor gerado recebe as duas, na ordem
// em que foram declaradas.
@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    // Aula 7 (slide 20-21): clienteRepository.findById devolve um Optional;
    // orElseThrow transforma o caso "cliente ausente" numa excecao com
    // mensagem clara, em vez de devolver null.
    // Aula 7 (slide 22-24): @Transactional garante que buscar o cliente e
    // salvar o pedido sejam tudo ou nada - se algo falhar no meio, o Spring
    // desfaz (rollback) o que ja tinha sido feito.
    @Transactional
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
