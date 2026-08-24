package com.curso.app.controller;

import com.curso.app.model.Cliente;
import com.curso.app.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClienteService clienteService;

    @Test
    void listarDeveRetornarClientesEStatus200() throws Exception {
        Cliente cliente = new Cliente(1L, "Ana Souza", "ana.souza@exemplo.com");
        when(clienteService.listarTodos()).thenReturn(List.of(cliente));

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].nome").value("Ana Souza"))
                .andExpect(jsonPath("$[0].email").value("ana.souza@exemplo.com"));
    }

    @Test
    void cadastrarDeveRetornarClienteCriadoEStatus200() throws Exception {
        Cliente entrada = new Cliente(null, "Carlos Mendes", "carlos.mendes@exemplo.com");
        Cliente salvo = new Cliente(3L, "Carlos Mendes", "carlos.mendes@exemplo.com");
        when(clienteService.cadastrar(any(Cliente.class))).thenReturn(salvo);

        mockMvc.perform(post("/clientes")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.nome").value("Carlos Mendes"));
    }
}
