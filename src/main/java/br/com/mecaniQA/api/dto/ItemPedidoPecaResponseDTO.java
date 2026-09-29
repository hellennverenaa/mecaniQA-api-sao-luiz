package br.com.mecaniQA.api.dto;

public class ItemPedidoPecaResponseDTO {

    private Long codigoPeca;
    private Integer quantidade;

    public ItemPedidoPecaResponseDTO() {
    }

    public ItemPedidoPecaResponseDTO(Long codigoPeca, Integer quantidade) {
        this.codigoPeca = codigoPeca;
        this.quantidade = quantidade;
    }

    public Long getCodigoPeca() {
        return codigoPeca;
    }

    public void setCodigoPeca(Long codigoPeca) {
        this.codigoPeca = codigoPeca;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
