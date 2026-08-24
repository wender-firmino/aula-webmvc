package com.curso.app;

import com.curso.app.model.Cliente;
import com.curso.app.model.Pedido;
import com.curso.app.repository.ClienteRepository;
import com.curso.app.repository.PedidoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

// Aula 3: como agora os dados vao para o banco H2 de verdade (e nao mais para
// uma lista fixa no codigo), usamos um CommandLineRunner para semear alguns
// registros de exemplo toda vez que a aplicacao sobe.
@Component
public class DataSeeder implements CommandLineRunner {

    private final ClienteRepository clienteRepository;
    private final PedidoRepository pedidoRepository;

    public DataSeeder(ClienteRepository clienteRepository, PedidoRepository pedidoRepository) {
        this.clienteRepository = clienteRepository;
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public void run(String... args) {
        Cliente ana = clienteRepository.save(new Cliente("Ana Souza", "ana.souza@exemplo.com"));
        Cliente bruno = clienteRepository.save(new Cliente("Bruno Lima", "bruno.lima@exemplo.com"));

        pedidoRepository.save(new Pedido(LocalDate.of(2026, 8, 20), new BigDecimal("150.00"), ana));
        pedidoRepository.save(new Pedido(LocalDate.of(2026, 8, 22), new BigDecimal("89.90"), ana));
        pedidoRepository.save(new Pedido(LocalDate.of(2026, 8, 21), new BigDecimal("240.50"), bruno));
    }
}
