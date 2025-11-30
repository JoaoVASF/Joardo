package com.joardo.joardo_construcao.service;

import com.joardo.joardo_construcao.dao.CarrinhoDAO;
import com.joardo.joardo_construcao.dao.ProdutoDAO;
import com.joardo.joardo_construcao.model.ItemCarrinho;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class CarrinhoService {

    @Autowired
    private CarrinhoDAO carrinhoDAO;

    @Autowired
    private ProdutoDAO produtoDAO;

    @Transactional
    public void adicionarProduto(int idUsuario, int idProduto) {
        Map<String, Object> produto = produtoDAO.obterProduto(idProduto);
        int estoqueAtual = (int) produto.get("quantidade_estoque");
        if (estoqueAtual > 0) {
            produtoDAO.atualizarEstoque(idProduto, estoqueAtual - 1);
            carrinhoDAO.adicionarItem(idUsuario, idProduto);
        }
    }

    @Transactional
    public void removerItem(int idItemCarrinho) {
        ItemCarrinho item = carrinhoDAO.obterItem(idItemCarrinho);
        Map<String, Object> produto = produtoDAO.obterProduto(item.getIdProduto());
        int estoqueAtual = (int) produto.get("quantidade_estoque");
        produtoDAO.atualizarEstoque(item.getIdProduto(), estoqueAtual + 1);
        carrinhoDAO.removerItem(idItemCarrinho);
    }

    public List<ItemCarrinho> listarCarrinho(int idUsuario) {
        return carrinhoDAO.listarItensDoUsuario(idUsuario);
    }
}