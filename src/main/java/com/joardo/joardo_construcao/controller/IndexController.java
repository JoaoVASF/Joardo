package com.joardo.joardo_construcao.controller;

import com.joardo.joardo_construcao.model.Usuario;
import com.joardo.joardo_construcao.service.ProdutoService;
import com.joardo.joardo_construcao.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        List<Map<String, Object>> produtos = produtoService.listarProdutosLoja();
        model.addAttribute("produtos", produtos);
        model.addAttribute("usuarioLogado", session.getAttribute("usuarioLogado"));
        return "index";
    }

    @GetMapping("/cadastro")
    public String cadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastro";
    }

    @PostMapping("/cadastrar")
    public String cadastrar(@ModelAttribute Usuario usuario) {
        usuario.setAdmin(false);
        usuarioService.cadastrarUsuario(usuario);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/logar")
    public String logar(@RequestParam("email") String email,
            @RequestParam("senha") String senha,
            HttpSession session,
            Model model) {
        Map<String, Object> usuario = usuarioService.login(email, senha);
        if (usuario != null) {
            session.setAttribute("usuarioLogado", usuario);
            if (usuario.get("e_admin") != null && (boolean) usuario.get("e_admin")) {
                return "redirect:/admin";
            }
            return "redirect:/";
        } else {
            model.addAttribute("erro", "Email ou senha inválidos");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}