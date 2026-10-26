package com.curso.app.controller;

import com.curso.app.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// Aula 8 (4.2): Controller de PAGINAS (HTML) para Cliente. Convive com o
// ClienteController (API/JSON) no mesmo projeto, em outra rota.
// Regra de sempre: fala com o Service, nunca direto com o Repository.
// Aula 9 (19/10): ponto de partida - so a listagem existe; formulario,
// edicao e exclusao sao feitos em aula.
@Controller
@RequestMapping("/paginas/clientes")
@RequiredArgsConstructor
public class ClientePaginaController {

    private final ClienteService clienteService;

    // GET /paginas/clientes -> lista (Model entrega a lista ao th:each)
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", clienteService.listarTodos());
        return "clientes/lista";
    }
}
