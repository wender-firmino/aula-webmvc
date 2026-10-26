package com.curso.app.controller;

import com.curso.app.model.Cliente;
import com.curso.app.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Aula 8: testa as paginas Thymeleaf sem subir o banco (Service simulado).
// addFilters = false: o foco aqui e a camada de visao, nao o Spring Security.
@WebMvcTest(ClientePaginaController.class)
@AutoConfigureMockMvc(addFilters = false)
class ClientePaginaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Test
    void listarMostraClientesNaPagina() throws Exception {
        when(clienteService.listarTodos())
                .thenReturn(List.of(new Cliente(1L, "Ana Souza", "ana@exemplo.com")));

        mockMvc.perform(get("/paginas/clientes"))
                .andExpect(status().isOk())
                .andExpect(view().name("clientes/lista"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Ana Souza")));
    }
}
