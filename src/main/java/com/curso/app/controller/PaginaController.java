package com.curso.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Aula 8 (4.1): diferente do @RestController, o valor devolvido aqui e o NOME
// de um template em src/main/resources/templates/, nao o corpo da resposta.
@Controller
public class PaginaController {

    // GET http://localhost:8080/boas-vindas -> templates/boas-vindas.html
    @GetMapping("/boas-vindas")
    public String boasVindas() {
        return "boas-vindas";
    }
}
