package com.joardo.joardo_construcao.controller;

import com.joardo.joardo_construcao.model.Produto;
import com.joardo.joardo_construcao.service.ProdutoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class AdminController {

    @Autowired
    private ProdutoService produtoService;

    private boolean isAdmin(HttpSession session) {
        Map<String, Object> usuario = (Map<String, Object>) session.getAttribute("usuarioLogado");
        return usuario != null && usuario.get("e_admin") != null && (boolean) usuario.get("e_admin");
    }

    @GetMapping("/admin")
    public String admin(HttpSession session) {
        if (!isAdmin(session))
            return "redirect:/login";
        return "admin";
    }

    @GetMapping("/cadastroProduto")
    public String cadastroProduto(Model model, HttpSession session) {
        if (!isAdmin(session))
            return "redirect:/login";
        model.addAttribute("produto", new Produto());
        return "cadastroProduto";
    }

    @PostMapping("/salvarProduto")
    public String salvarProduto(@ModelAttribute Produto produto, HttpSession session) {
        if (!isAdmin(session))
            return "redirect:/login";
        produto.setAtivo(true);
        produto.setImagem("https://via.placeholder.com/150");
        produtoService.cadastrarProduto(produto);
        return "redirect:/admin";
    }
}