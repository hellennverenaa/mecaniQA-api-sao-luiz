package br.com.mecaniQA.api.mapper;

import br.com.mecaniQA.api.dto.ItemPedidoPecaResponseDTO;
import br.com.mecaniQA.api.dto.PedidoPecasRequestDTO;
import br.com.mecaniQA.api.dto.PedidoPecasResponseDTO;
import br.com.mecaniQA.api.model.ItemPedidoPeca;
import br.com.mecaniQA.api.model.PedidoPecas;

import java.util.ArrayList;
import java.util.List;

public final class PedidoPecasMapper {

    private PedidoPecasMapper() {
    }

    public static PedidoPecas toModel(Long codigo, PedidoPecasRequestDTO dto) {
        return new PedidoPecas(codigo, dto.getStatus(), new ArrayList<>());
    }

    public static PedidoPecasResponseDTO toResponse(PedidoPecas pedido) {
        List<ItemPedidoPecaResponseDTO> itens = pedido.getItens().stream()
                .map(PedidoPecasMapper::toItemResponse)
                .toList();

        return new PedidoPecasResponseDTO(
                pedido.getCodigo(),
                pedido.getStatus(),
                itens
        );
    }

    private static ItemPedidoPecaResponseDTO toItemResponse(ItemPedidoPeca item) {
        return new ItemPedidoPecaResponseDTO(
                item.getPeca().getCodigo(),
                item.getQuantidade()
        );
    }
}
