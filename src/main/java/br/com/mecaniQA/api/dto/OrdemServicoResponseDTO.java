package br.com.mecaniQA.api.dto;

import br.com.mecaniQA.api.model.StatusOrdemServico;

public class OrdemServicoResponseDTO {

    private Long codigo;
    private StatusOrdemServico status;

    public OrdemServicoResponseDTO() {
    }

    public OrdemServicoResponseDTO(Long codigo, StatusOrdemServico status) {
        this.codigo = codigo;
        this.status = status;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public StatusOrdemServico getStatus() {
        return status;
    }

    public void setStatus(StatusOrdemServico status) {
        this.status = status;
    }
}
