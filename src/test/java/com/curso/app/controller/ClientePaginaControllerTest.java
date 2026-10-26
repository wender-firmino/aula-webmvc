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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Test
    void cadastroComSucessoRedirecionaParaALista() throws Exception {
        mockMvc.perform(post("/paginas/clientes")
                        .param("nome", "Carla").param("email", "carla@exemplo.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/paginas/clientes"));
        verify(clienteService).cadastrar(any(Cliente.class));
    }

    @Test
    void emailDuplicadoDevolveFormularioComMensagem() throws Exception {
        doThrow(new IllegalArgumentException("E-mail ja cadastrado"))
                .when(clienteService).cadastrar(any(Cliente.class));

        mockMvc.perform(post("/paginas/clientes")
                        .param("nome", "Carla").param("email", "carla@exemplo.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("clientes/formulario"))
                .andExpect(model().attribute("erro", "E-mail ja cadastrado"));
    }

    @Test
    void edicaoChamaAtualizarEExclusaoRedireciona() throws Exception {
        mockMvc.perform(post("/paginas/clientes")
                        .param("id", "3").param("nome", "Ana").param("email", "ana@exemplo.com"))
                .andExpect(redirectedUrl("/paginas/clientes"));
        verify(clienteService).atualizar(org.mockito.ArgumentMatchers.eq(3L), any(Cliente.class));

        mockMvc.perform(post("/paginas/clientes/excluir/3"))
                .andExpect(redirectedUrl("/paginas/clientes"));
        verify(clienteService).excluir(3L);
    }

    // Aula 10: o alert verde do Bootstrap depende deste flash attribute
    @Test
    void sucessoEnviaMensagemPorFlashAttribute() throws Exception {
        mockMvc.perform(post("/paginas/clientes")
                        .param("nome", "Carla").param("email", "carla@exemplo.com"))
                .andExpect(flash().attribute("sucesso", "Cliente salvo com sucesso."));

        mockMvc.perform(post("/paginas/clientes/excluir/3"))
                .andExpect(flash().attribute("sucesso", "Cliente excluído."));
    }

    @Test
    void listaCarregaBootstrapENavbar() throws Exception {
        when(clienteService.listarTodos()).thenReturn(List.of());

        mockMvc.perform(get("/paginas/clientes"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("bootstrap@5.3.8")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("navbar")));
    }
}
