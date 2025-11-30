package com.joardo.joardo_construcao.controller;

import com.joardo.joardo_construcao.model.ItemCarrinho;
import com.joardo.joardo_construcao.service.CarrinhoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@Controller
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @GetMapping("/carrinho")
    public String verCarrinho(HttpSession session, Model model) {
        Map<String, Object> usuario = (Map<String, Object>) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";
        int idUsuario = (int) usuario.get("id");
        List<ItemCarrinho> itens = carrinhoService.listarCarrinho(idUsuario);
        double valorTotal = itens.stream().mapToDouble(ItemCarrinho::getTotalPreco).sum();
        model.addAttribute("itens", itens);
        model.addAttribute("valorTotal", valorTotal);
        
        return "carrinho";
    }

    @PostMapping("/carrinho/adicionar/{idProduto}")
    public String adicionar(@PathVariable int idProduto, HttpSession session) {
        Map<String, Object> usuario = (Map<String, Object>) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";
        carrinhoService.adicionarProduto((int) usuario.get("id"), idProduto);
        return "redirect:/carrinho";
    }

    @PostMapping("/carrinho/remover/{idItem}")
    public String remover(@PathVariable int idItem, HttpSession session) {
        if (session.getAttribute("usuarioLogado") == null) return "redirect:/login";
        carrinhoService.removerItem(idItem);
        return "redirect:/carrinho";
    }
}