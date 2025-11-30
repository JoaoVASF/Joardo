package com.joardo.joardo_construcao.dao;

import com.joardo.joardo_construcao.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class UsuarioDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void inserirUsuario(Usuario user) {
        String sql = "INSERT INTO usuario (nome, email, senha, telefone, e_admin) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getNome(), user.getEmail(), user.getSenha(), user.getTelefone(), user.isAdmin());
    }

    public List<Map<String, Object>> buscarUsuarioPorEmailSenha(String email, String senha) {
        String sql = "SELECT * FROM usuario WHERE email = ? AND senha = ?";
        return jdbcTemplate.queryForList(sql, email, senha);
    }

    public List<Map<String, Object>> listarFuncionarios() {
        String sql = "SELECT * FROM usuario WHERE e_admin = true";
        return jdbcTemplate.queryForList(sql);
    }
}