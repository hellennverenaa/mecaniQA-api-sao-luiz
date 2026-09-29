package br.com.mecaniQA.api.dto;

import br.com.mecaniQA.api.model.StatusPedidoPecas;

public class AtualizarStatusPedidoPecasDTO {

    private StatusPedidoPecas status;

    public AtualizarStatusPedidoPecasDTO() {
    }

    public AtualizarStatusPedidoPecasDTO(StatusPedidoPecas status) {
        this.status = status;
    }

    public StatusPedidoPecas getStatus() {
        return status;
    }

    public void setStatus(StatusPedidoPecas status) {
        this.status = status;
    }
}
