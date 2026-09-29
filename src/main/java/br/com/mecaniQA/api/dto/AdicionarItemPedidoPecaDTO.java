package br.com.mecaniQA.api.dto;

public class AdicionarItemPedidoPecaDTO {

    private Long codigoPeca;
    private Integer quantidade;

    public AdicionarItemPedidoPecaDTO() {
    }

    public AdicionarItemPedidoPecaDTO(Long codigoPeca, Integer quantidade) {
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
