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
    public void adicionarProduto(int idUsuario, int idProduto, int quantidadeDesejada) {
        Map<String, Object> produto = produtoDAO.obterProduto(idProduto);
        if (produto != null) {
            int estoqueAtual = (int) produto.get("quantidade_estoque");
            if (estoqueAtual >= quantidadeDesejada) {
                int novoEstoque = estoqueAtual - quantidadeDesejada;
                produtoDAO.atualizarEstoque(idProduto, novoEstoque);
                if (novoEstoque == 0) {
                    produtoDAO.alternarStatusProduto(idProduto, false);
                }
                Integer qtdNoCarrinho = carrinhoDAO.verificarQuantidadeNoCarrinho(idUsuario, idProduto);
                if (qtdNoCarrinho == null) {
                    carrinhoDAO.adicionarItem(idUsuario, idProduto, quantidadeDesejada);
                } else {
                    carrinhoDAO.somarQuantidade(idUsuario, idProduto, quantidadeDesejada);
                }
            }
        }
    }

    @Transactional
    public void removerItem(int idItemCarrinho) {
        ItemCarrinho item = carrinhoDAO.obterItem(idItemCarrinho);
        
        if (item != null) {
            Map<String, Object> produto = produtoDAO.obterProduto(item.getIdProduto());
            int estoqueAtual = (int) produto.get("quantidade_estoque");
            int novoEstoque = estoqueAtual + item.getQuantidade();
            produtoDAO.atualizarEstoque(item.getIdProduto(), novoEstoque);
            if (novoEstoque > 0) {
                produtoDAO.alternarStatusProduto(item.getIdProduto(), true);
            }
            carrinhoDAO.removerItem(idItemCarrinho);
        }
    }

    public List<ItemCarrinho> listarCarrinho(int idUsuario) {
        return carrinhoDAO.listarItensDoUsuario(idUsuario);
    }
    
    public int contarItensNoCarrinho(int idUsuario) {
        return carrinhoDAO.contarItensUnicos(idUsuario);
    }
}