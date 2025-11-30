package com.joardo.joardo_construcao.dao;

import com.joardo.joardo_construcao.model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ProdutoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void inserirProduto(Produto prod) {
        String sql = "INSERT INTO produto (nome, descricao, imagem, preco, quantidade_estoque, ativo) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, prod.getNome(), prod.getDescricao(), prod.getImagem(), prod.getPreco(),
                prod.getQuantidadeEstoque(), true);
    }

    public List<Map<String, Object>> listarProdutos() {
        String sql = "SELECT * FROM produto WHERE ativo = true";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> listarTodosProdutosAdmin() {
        String sql = "SELECT * FROM produto ORDER BY id";
        return jdbcTemplate.queryForList(sql);
    }

    public Map<String, Object> obterProduto(int id) {
        String sql = "SELECT * FROM produto WHERE id = ?";
        List<Map<String, Object>> produtos = jdbcTemplate.queryForList(sql, id);
        if (produtos.isEmpty()) {
            return null;
        }
        return produtos.get(0);
    }

    public void atualizarEstoque(int id, int novaQuantidade) {
        String sql = "UPDATE produto SET quantidade_estoque = ? WHERE id = ?";
        jdbcTemplate.update(sql, novaQuantidade, id);
    }

    public void alternarStatusProduto(int id, boolean ativo) {
        String sql = "UPDATE produto SET ativo = ? WHERE id = ?";
        jdbcTemplate.update(sql, ativo, id);
    }
}