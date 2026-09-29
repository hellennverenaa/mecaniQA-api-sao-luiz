package br.com.mecaniQA.api.model;

public class ItemPedidoPeca {

    private Peca peca;
    private Integer quantidade;

    public ItemPedidoPeca() {
    }

    public ItemPedidoPeca(Peca peca, Integer quantidade) {
        this.peca = peca;
        this.quantidade = quantidade;
    }

    public Peca getPeca() {
        return peca;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}