package com.joardo.joardo_construcao.model;

public class ItemCarrinho {
    private int id;
    private int idUsuario;
    private int idProduto;
    private int quantidade;
    private String nomeProduto;
    private double precoProduto;
    private double totalPreco;
    
    public ItemCarrinho() {}
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public int getIdProduto() { return idProduto; }
    public void setIdProduto(int idProduto) { this.idProduto = idProduto; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    public String getNomeProduto() { return nomeProduto; }
    public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }
    public double getPrecoProduto() { return precoProduto; }
    public void setPrecoProduto(double precoProduto) { this.precoProduto = precoProduto; }
    public double getTotalPreco() { return totalPreco; }
    public void setTotalPreco(double totalPreco) { this.totalPreco = totalPreco; }
}