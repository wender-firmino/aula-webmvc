package com.curso.app.service;

import com.curso.app.model.Cliente;
import com.curso.app.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        clienteService = new ClienteService(clienteRepository);
    }

    @Test
    void listarTodosDeveDelegarParaORepository() {
        Cliente cliente = new Cliente(1L, "Ana Souza", "ana.souza@exemplo.com");
        when(clienteRepository.findAll()).thenReturn(List.of(cliente));

        List<Cliente> resultado = clienteService.listarTodos();

        assertThat(resultado).containsExactly(cliente);
        verify(clienteRepository).findAll();
    }

    @Test
    void cadastrarDeveDelegarParaORepository() {
        Cliente novo = new Cliente(null, "Carlos Mendes", "carlos.mendes@exemplo.com");
        Cliente salvo = new Cliente(3L, "Carlos Mendes", "carlos.mendes@exemplo.com");
        when(clienteRepository.existsByEmail(novo.getEmail())).thenReturn(false);
        when(clienteRepository.save(novo)).thenReturn(salvo);

        Cliente resultado = clienteService.cadastrar(novo);

        assertThat(resultado).isEqualTo(salvo);
        verify(clienteRepository).save(novo);
    }

    // Aula 7 (slide 17): regra de negocio - e-mail duplicado e recusado antes
    // de chamar o Repository.
    @Test
    void cadastrarDeveRecusarEmailJaCadastrado() {
        Cliente novo = new Cliente(null, "Carlos Mendes", "carlos.mendes@exemplo.com");
        when(clienteRepository.existsByEmail(novo.getEmail())).thenReturn(true);

        assertThatThrownBy(() -> clienteService.cadastrar(novo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("E-mail ja cadastrado");

        verify(clienteRepository, never()).save(novo);
    }
}
