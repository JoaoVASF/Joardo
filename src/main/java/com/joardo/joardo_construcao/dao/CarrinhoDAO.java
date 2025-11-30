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

    public Integer verificarQuantidadeNoCarrinho(int idUsuario, int idProduto) {
        try {
            String sql = "SELECT quantidade FROM item_carrinho WHERE id_usuario = ? AND id_produto = ?";
            return jdbcTemplate.queryForObject(sql, Integer.class, idUsuario, idProduto);
        } catch (Exception e) {
            return null;
        }
    }

    public void adicionarItem(int idUsuario, int idProduto, int quantidade) {
        String sql = "INSERT INTO item_carrinho (id_usuario, id_produto, quantidade) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, idUsuario, idProduto, quantidade);
    }

    public void somarQuantidade(int idUsuario, int idProduto, int quantidadeParaAdicionar) {
        String sql = "UPDATE item_carrinho SET quantidade = quantidade + ? WHERE id_usuario = ? AND id_produto = ?";
        jdbcTemplate.update(sql, quantidadeParaAdicionar, idUsuario, idProduto);
    }

    public int contarItensUnicos(int idUsuario) {
        String sql = "SELECT COUNT(*) FROM item_carrinho WHERE id_usuario = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, idUsuario);
    }

    public void removerItem(int id) {
        String sql = "DELETE FROM item_carrinho WHERE id = ?";
        jdbcTemplate.update(sql, id);
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

    public ItemCarrinho obterItem(int id) {
        String sql = "SELECT * FROM item_carrinho WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(ItemCarrinho.class), id);
    }

    public void removerProdutoDeTodosOsCarrinhos(int idProduto) {
        String sql = "DELETE FROM item_carrinho WHERE id_produto = ?";
        jdbcTemplate.update(sql, idProduto);
    }

    public int somarQuantidadeTotalEmCarrinhos(int idProduto) {
        String sql = "SELECT COALESCE(SUM(quantidade), 0) FROM item_carrinho WHERE id_produto = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, idProduto);
    }

}