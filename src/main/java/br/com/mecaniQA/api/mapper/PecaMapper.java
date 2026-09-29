package br.com.mecaniQA.api.mapper;

import br.com.mecaniQA.api.dto.PecaResponseDTO;
import br.com.mecaniQA.api.model.Peca;

public final class PecaMapper {

    private PecaMapper() {
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
