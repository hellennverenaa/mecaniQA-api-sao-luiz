package br.com.mecaniQA.api.dto;

import br.com.mecaniQA.api.model.StatusPedidoPecas;

import java.util.List;

public class PedidoPecasResponseDTO {

    private Long codigo;
    private StatusPedidoPecas status;
    private List<ItemPedidoPecaResponseDTO> itens;

    public PedidoPecasResponseDTO() {
    }

    public PedidoPecasResponseDTO(Long codigo, StatusPedidoPecas status,
                                  List<ItemPedidoPecaResponseDTO> itens) {
        this.codigo = codigo;
        this.status = status;
        this.itens = itens;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public StatusPedidoPecas getStatus() {
        return status;
    }

    public void setStatus(StatusPedidoPecas status) {
        this.status = status;
    }

    public List<ItemPedidoPecaResponseDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoPecaResponseDTO> itens) {
        this.itens = itens;
    }
}
