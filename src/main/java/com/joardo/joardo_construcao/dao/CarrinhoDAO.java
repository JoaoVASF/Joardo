package com.joardo.joardo_construcao.dao;

import com.joardo.joardo_construcao.model.ItemCarrinho;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CarrinhoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void adicionarItem(int idUsuario, int idProduto) {
        String sql = "INSERT INTO item_carrinho (id_usuario, id_produto, quantidade) VALUES (?, ?, 1)";
        jdbcTemplate.update(sql, idUsuario, idProduto);
    }

    public List<ItemCarrinho> listarItensDoUsuario(int idUsuario) {
        String sql = """
            SELECT i.id, i.id_usuario, i.id_produto, i.quantidade, 
                   p.nome AS nomeProduto, p.preco AS precoProduto, 
                   (p.preco * i.quantidade) AS totalPreco
            FROM item_carrinho i
            INNER JOIN produto p ON i.id_produto = p.id
            WHERE i.id_usuario = ?
        """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ItemCarrinho.class), idUsuario);
    }

    public void removerItem(int id) {
        String sql = "DELETE FROM item_carrinho WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
    
    public ItemCarrinho obterItem(int id) {
        String sql = "SELECT * FROM item_carrinho WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(ItemCarrinho.class), id);
    }
}