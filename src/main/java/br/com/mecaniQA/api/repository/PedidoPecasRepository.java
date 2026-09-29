package br.com.mecaniQA.api.repository;

import br.com.mecaniQA.api.dto.AdicionarItemPedidoPecaDTO;
import br.com.mecaniQA.api.dto.PedidoPecasRequestDTO;
import br.com.mecaniQA.api.mapper.PedidoPecasMapper;
import br.com.mecaniQA.api.model.ItemPedidoPeca;
import br.com.mecaniQA.api.model.Peca;
import br.com.mecaniQA.api.model.PedidoPecas;
import br.com.mecaniQA.api.model.StatusPedidoPecas;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PedidoPecasRepository {

    private static PedidoPecasRepository INSTANCE;

    private final List<PedidoPecas> pedidos = new ArrayList<>();
    private final PecaRepository pecaRepository;
    private Long proximoCodigo = 1L;

    private PedidoPecasRepository() {
        this.pecaRepository = PecaRepository.getInstance();
    }

    public static synchronized PedidoPecasRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PedidoPecasRepository();
        }

        return INSTANCE;
    }

    public synchronized PedidoPecas salvar(PedidoPecasRequestDTO dto) {
        PedidoPecas pedido = PedidoPecasMapper.toModel(proximoCodigo++, dto);
        pedidos.add(pedido);
        return pedido;
    }

    public Optional<PedidoPecas> buscarPorCodigo(Long codigo) {
        return pedidos.stream()
                .filter(pedido -> pedido.getCodigo().equals(codigo))
                .findFirst();
    }

    public synchronized Optional<PedidoPecas> adicionarItem(Long codigo,
                                                             AdicionarItemPedidoPecaDTO dto) {
        Optional<PedidoPecas> pedidoEncontrado = buscarPorCodigo(codigo);
        Optional<Peca> pecaEncontrada = pecaRepository.buscarPorCodigo(dto.getCodigoPeca());

        if (pedidoEncontrado.isEmpty() || pecaEncontrada.isEmpty()) {
            return Optional.empty();
        }

        pedidoEncontrado.get().getItens().add(
                new ItemPedidoPeca(pecaEncontrada.get(), dto.getQuantidade())
        );
        return pedidoEncontrado;
    }

    public synchronized Optional<PedidoPecas> atualizarStatus(Long codigo,
                                                               StatusPedidoPecas status) {
        Optional<PedidoPecas> pedidoEncontrado = buscarPorCodigo(codigo);
        pedidoEncontrado.ifPresent(pedido -> pedido.setStatus(status));
        return pedidoEncontrado;
    }
}
