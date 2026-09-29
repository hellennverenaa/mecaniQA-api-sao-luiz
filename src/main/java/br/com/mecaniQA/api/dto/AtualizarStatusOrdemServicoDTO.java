package br.com.mecaniQA.api.dto;

import br.com.mecaniQA.api.model.StatusOrdemServico;

public class AtualizarStatusOrdemServicoDTO {

    private StatusOrdemServico status;

    public AtualizarStatusOrdemServicoDTO() {
    }

    public AtualizarStatusOrdemServicoDTO(StatusOrdemServico status) {
        this.status = status;
    }

    public StatusOrdemServico getStatus() {
        return status;
    }

    public void setStatus(StatusOrdemServico status) {
        this.status = status;
    }
}
