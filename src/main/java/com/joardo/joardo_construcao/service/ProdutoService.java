package com.joardo.joardo_construcao.service;

import com.joardo.joardo_construcao.dao.ProdutoDAO;
import com.joardo.joardo_construcao.model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoDAO produtoDAO;

    public void cadastrarProduto(Produto prod) {
        produtoDAO.inserirProduto(prod);
    }

    public List<Map<String, Object>> listarProdutosLoja() {
        return produtoDAO.listarProdutos();
    }

    public List<Map<String, Object>> listarProdutosAdmin() {
        return produtoDAO.listarTodosProdutosAdmin();
    }

    public Map<String, Object> obterProduto(int id) {
        return produtoDAO.obterProduto(id);
    }

    public void salvarProduto(Produto produto) {
        if (produto.getImagem() == null || produto.getImagem().isEmpty()) {
            produto.setImagem("https://via.placeholder.com/150");
        }
        if (produto.getId() > 0) {
            produtoDAO.atualizarProduto(produto);
        } else {
            produtoDAO.inserirProduto(produto);
        }
    }

    public List<Map<String, Object>> buscarPorNome(String termo) {
        return produtoDAO.buscarPorNome(termo);
    }
    
    public void alternarStatus(int id, boolean status) {
        produtoDAO.alternarStatusProduto(id, status);
    }

    public void deletarProduto(int id) {
        produtoDAO.deletarProduto(id);
    }
}