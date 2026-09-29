package br.com.mecaniQA.api.mapper;

import br.com.mecaniQA.api.dto.OrdemServicoRequestDTO;
import br.com.mecaniQA.api.dto.OrdemServicoResponseDTO;
import br.com.mecaniQA.api.model.OrdemServico;

public final class OrdemServicoMapper {

    private OrdemServicoMapper() {
    }

    public static OrdemServico toModel(Long codigo, OrdemServicoRequestDTO dto) {
        return OrdemServico.builder()
                .codigo(codigo)
                .status(dto.getStatus())
                .build();
    }

    public static OrdemServicoResponseDTO toResponse(OrdemServico ordemServico) {
        return new OrdemServicoResponseDTO(
                ordemServico.getCodigo(),
                ordemServico.getStatus()
        );
    }
}
