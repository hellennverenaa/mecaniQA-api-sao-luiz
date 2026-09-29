package br.com.mecaniQA.api.mapper;

import br.com.mecaniQA.api.dto.ServicoRequestDTO;
import br.com.mecaniQA.api.dto.ServicoResponseDTO;
import br.com.mecaniQA.api.model.Servico;

import java.time.LocalDateTime;

public final class ServicoMapper {

    private ServicoMapper() {
    }

    public static Servico toModel(Long codigo, ServicoRequestDTO dto, LocalDateTime agora) {
        return new Servico(
                codigo,
                dto.getNome(),
                dto.getTempoEstimadoMinutos(),
                dto.getCustoTabelado(),
                agora,
                agora
        );
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
