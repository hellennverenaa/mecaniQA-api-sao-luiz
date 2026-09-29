package br.com.mecaniQA.api.mapper;

import br.com.mecaniQA.api.dto.PecaRequestDTO;
import br.com.mecaniQA.api.dto.PecaResponseDTO;
import br.com.mecaniQA.api.model.Peca;

import java.time.LocalDateTime;

public final class PecaMapper {

    private PecaMapper() {
    }

    public static Peca toModel(Long codigo, PecaRequestDTO dto, LocalDateTime agora) {
        return new Peca(
                codigo,
                dto.getNome(),
                dto.getCodigoBarras(),
                dto.getFornecedorMarca(),
                dto.getQuantidadeEstoque(),
                dto.getPrecoCusto(),
                dto.getPrecoVenda(),
                dto.getCategoria(),
                dto.getTamanho(),
                dto.getCor(),
                agora,
                agora
        );
    }

    public static PecaResponseDTO toResponse(Peca peca) {
        return new PecaResponseDTO(
                peca.getCodigo(),
                peca.getNome(),
                peca.getCodigoBarras(),
                peca.getFornecedorMarca(),
                peca.getQuantidadeEstoque(),
                peca.getPrecoCusto(),
                peca.getPrecoVenda(),
                peca.getCategoria(),
                peca.getTamanho(),
                peca.getCor(),
                peca.getDataCadastro(),
                peca.getDataAtualizacao()
        );
    }
}
