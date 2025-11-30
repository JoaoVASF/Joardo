package com.joardo.joardo_construcao.service;

import com.joardo.joardo_construcao.dao.CarrinhoDAO;
import com.joardo.joardo_construcao.dao.ProdutoDAO;
import com.joardo.joardo_construcao.model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importante para garantir segurança

import java.util.List;
import java.util.Map;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoDAO produtoDAO;

    @Autowired
    private CarrinhoDAO carrinhoDAO;

    @Transactional
    public void alternarStatus(int id, boolean status) {
        
        if (!status) {
            int qtdNoLimbo = carrinhoDAO.somarQuantidadeTotalEmCarrinhos(id);
            if (qtdNoLimbo > 0) {
                Map<String, Object> produto = produtoDAO.obterProduto(id);
                int estoqueAtual = (int) produto.get("quantidade_estoque");
                produtoDAO.atualizarEstoque(id, estoqueAtual + qtdNoLimbo);
                carrinhoDAO.removerProdutoDeTodosOsCarrinhos(id);
            }
        }
        produtoDAO.alternarStatusProduto(id, status);
    }

    public void salvarProduto(Produto produto) {
        if (produto.getImagem() == null || produto.getImagem().isEmpty()) {
             produto.setImagem(null); 
        }
        if (produto.getId() > 0) {
            produtoDAO.atualizarProduto(produto);
        } else {
            produtoDAO.inserirProduto(produto);
        }
    }

    public void deletarProduto(int id) {
        produtoDAO.deletarProduto(id);
    }

    public List<Map<String, Object>> buscarPorNome(String termo) {
        return produtoDAO.buscarPorNome(termo);
    }

    public List<Map<String, Object>> listarProdutosAdmin() {
        return produtoDAO.listarTodosProdutosAdmin();
    }

    public List<Map<String, Object>> listarProdutosLoja() {
        return produtoDAO.listarProdutos();
    }

    public Map<String, Object> obterProduto(int id) {
        return produtoDAO.obterProduto(id);
    }
}