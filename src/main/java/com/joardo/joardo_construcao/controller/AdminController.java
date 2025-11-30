package com.joardo.joardo_construcao.controller;

import com.joardo.joardo_construcao.model.Produto;
import com.joardo.joardo_construcao.service.ProdutoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProdutoService produtoService;

    private boolean isAdmin(HttpSession session) {
        Map<String, Object> user = (Map<String, Object>) session.getAttribute("usuarioLogado");
        return user != null && (boolean) user.get("e_admin");
    }

    @GetMapping("") 
    public String painelGeral(HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        return "admin"; 
    }

@GetMapping("/produtos")
    public String gerenciarProdutos(@RequestParam(value = "busca", required = false) String busca,
                                    Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        List<Map<String, Object>> produtos;
        if (busca != null && !busca.isEmpty()) {
            produtos = produtoService.buscarPorNome(busca);
            model.addAttribute("termoBusca", busca);
        } else {
            produtos = produtoService.listarProdutosAdmin();
        }
        model.addAttribute("produtos", produtos);
        return "gerenciarProdutos";
    }

    @GetMapping("/produtos/novo")
    public String novoProduto(Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("produto", new Produto());
        return "cadastroProduto";
    }

    @GetMapping("/produtos/editar/{id}")
    public String editarProduto(@PathVariable int id, Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        Map<String, Object> prodMap = produtoService.obterProduto(id);
        Produto prod = new Produto();
        prod.setId((int) prodMap.get("id"));
        prod.setNome((String) prodMap.get("nome"));
        prod.setDescricao((String) prodMap.get("descricao"));
        prod.setPreco(((Number) prodMap.get("preco")).doubleValue());
        prod.setQuantidadeEstoque((int) prodMap.get("quantidade_estoque"));
        prod.setAtivo((boolean) prodMap.get("ativo"));
        model.addAttribute("produto", prod);
        return "cadastroProduto";
    }

    @PostMapping("/produtos/salvar")
    public String salvar(@ModelAttribute Produto produto, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        produtoService.salvarProduto(produto);
        return "redirect:/admin/produtos";
    }

    @GetMapping("/produtos/status/{id}")
    public String alterarStatus(@PathVariable int id, @RequestParam boolean ativo, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        produtoService.alternarStatus(id, ativo);
        return "redirect:/admin/produtos";
    }

    @PostMapping("/produtos/deletar/{id}")
    public String deletarProduto(@PathVariable int id, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        produtoService.deletarProduto(id);
        return "redirect:/admin/produtos";
    }
}