package br.com.mecaniQA.api.dto;

import br.com.mecaniQA.api.model.StatusOrdemServico;

public class OrdemServicoRequestDTO {

    private StatusOrdemServico status;

    public OrdemServicoRequestDTO() {
    }

    public OrdemServicoRequestDTO(StatusOrdemServico status) {
        this.status = status;
    }

    public StatusOrdemServico getStatus() {
        return status;
    }

    public void setStatus(StatusOrdemServico status) {
        this.status = status;
    }
}
