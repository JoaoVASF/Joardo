package com.joardo.joardo_construcao.service;

import com.joardo.joardo_construcao.dao.UsuarioDAO;
import com.joardo.joardo_construcao.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioDAO usuarioDAO;

    public void cadastrarUsuario(Usuario user) {

    if (usuarioDAO.existeEmail(user.getEmail())) {
        throw new RuntimeException("Este email já está cadastrado.");
    }

    usuarioDAO.inserirUsuario(user);
}


    public Map<String, Object> login(String email, String senha) {
        List<Map<String, Object>> usuarios = usuarioDAO.buscarUsuarioPorEmailSenha(email, senha);

        if (!usuarios.isEmpty()) {
            return usuarios.get(0);
        }
        return null;
    }
}