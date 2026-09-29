package br.com.mecaniQA.api.dto;

import br.com.mecaniQA.api.model.StatusPedidoPecas;

public class PedidoPecasRequestDTO {

    private StatusPedidoPecas status;

    public PedidoPecasRequestDTO() {
    }

    public PedidoPecasRequestDTO(StatusPedidoPecas status) {
        this.status = status;
    }

    public StatusPedidoPecas getStatus() {
        return status;
    }

    public void setStatus(StatusPedidoPecas status) {
        this.status = status;
    }
}
