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
//
// Aula 5 (2.3): com o H2 em modo servidor os dados sobrevivem ao restart, entao
// so semeamos se o banco ainda estiver vazio - senao a lista dobraria a cada reinicio.
//
// Aula 5 (2.4): "Carla Silveira" foi acrescentada de proposito - ela tambem "contem"
// Silv, exatamente como no slide da busca por trecho de nome (LIKE e literal, nao
// entende sobrenomes).
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
        if (clienteRepository.count() > 0) {
            return;
        }

        Cliente ana = clienteRepository.save(new Cliente("Ana Souza", "ana.souza@exemplo.com"));
        Cliente bruno = clienteRepository.save(new Cliente("Bruno Lima", "bruno.lima@exemplo.com"));
        Cliente joaquim = clienteRepository.save(new Cliente("Joaquim Silva", "joaquim.silva@exemplo.com"));
        Cliente carla = clienteRepository.save(new Cliente("Carla Silveira", "carla.silveira@exemplo.com"));

        pedidoRepository.save(new Pedido(LocalDate.of(2026, 8, 20), new BigDecimal("150.00"), ana));
        pedidoRepository.save(new Pedido(LocalDate.of(2026, 8, 22), new BigDecimal("89.90"), ana));
        pedidoRepository.save(new Pedido(LocalDate.of(2026, 8, 21), new BigDecimal("240.50"), bruno));
        pedidoRepository.save(new Pedido(LocalDate.of(2026, 9, 1), new BigDecimal("310.00"), joaquim));
        pedidoRepository.save(new Pedido(LocalDate.of(2026, 9, 10), new BigDecimal("75.20"), joaquim));
    }
}
