package com.curso.app.controller;

import com.curso.app.model.Cliente;
import com.curso.app.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// Aula 8 (4.2): Controller de PAGINAS (HTML) para Cliente. Convive com o
// ClienteController (API/JSON) no mesmo projeto, em outra rota.
// Regra de sempre: fala com o Service, nunca direto com o Repository.
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

    // GET /paginas/clientes/novo -> formulario com um Cliente vazio (th:object)
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/formulario";
    }

    // GET /paginas/clientes/editar/3 -> MESMO formulario, ja preenchido
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Cliente cliente = clienteService.buscarPorId(id);
        model.addAttribute("cliente", cliente);
        return "clientes/formulario";
    }

    // POST /paginas/clientes -> cadastra (id nulo) ou atualiza (id preenchido).
    // Sucesso: redirect (Post/Redirect/Get - F5 nao reenvia o POST).
    // Erro de regra (e-mail duplicado): NAO redireciona; devolve o formulario
    // com a mensagem "erro" no Model e os dados digitados preservados.
    // Aula 10 (4.3): no sucesso, o flash attribute "sucesso" alimenta o alert
    // do Bootstrap na lista e vale so para o proximo pedido (F5 nao repete).
    @PostMapping
    public String salvar(@ModelAttribute Cliente cliente, Model model,
                         RedirectAttributes ra) {
        try {
            if (cliente.getId() == null) {
                clienteService.cadastrar(cliente);
            } else {
                clienteService.atualizar(cliente.getId(), cliente);
            }
            ra.addFlashAttribute("sucesso", "Cliente salvo com sucesso.");
            return "redirect:/paginas/clientes";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            return "clientes/formulario";
        }
    }

    // POST (nunca GET) para excluir: um GET pode ser disparado por crawler ou
    // pre-carregamento do navegador sem ninguem clicar.
    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        clienteService.excluir(id);
        ra.addFlashAttribute("sucesso", "Cliente excluído.");
        return "redirect:/paginas/clientes";
    }
}
