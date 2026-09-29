package br.com.mecaniQA.api.mapper;

import br.com.mecaniQA.api.dto.ServicoResponseDTO;
import br.com.mecaniQA.api.model.Servico;

public final class ServicoMapper {

    private ServicoMapper() {
    }

    public static ServicoResponseDTO toResponse(Servico servico) {
        return new ServicoResponseDTO(
                servico.getCodigo(),
                servico.getNome(),
                servico.getTempoEstimadoMinutos(),
                servico.getCustoTabelado(),
                servico.getDataCriacao(),
                servico.getDataAtualizacao()
        );
    }
}
