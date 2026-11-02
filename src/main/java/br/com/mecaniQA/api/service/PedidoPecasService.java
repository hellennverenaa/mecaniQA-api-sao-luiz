package br.com.mecaniQA.api.service;

import br.com.mecaniQA.api.dto.AdicionarItemPedidoPecaDTO;
import br.com.mecaniQA.api.dto.AtualizarStatusPedidoPecasDTO;
import br.com.mecaniQA.api.dto.PedidoPecasRequestDTO;
import br.com.mecaniQA.api.dto.PedidoPecasResponseDTO;
import br.com.mecaniQA.api.exception.DadosInvalidosException;
import br.com.mecaniQA.api.exception.RecursoNaoEncontradoException;
import br.com.mecaniQA.api.mapper.PedidoPecasMapper;
import br.com.mecaniQA.api.model.PedidoPecas;
import br.com.mecaniQA.api.repository.PecaRepository;
import br.com.mecaniQA.api.repository.PedidoPecasRepository;

public class PedidoPecasService {

    private final PedidoPecasRepository repository = PedidoPecasRepository.getInstance();
    private final PecaRepository pecaRepository = PecaRepository.getInstance();

    public PedidoPecasResponseDTO criar(PedidoPecasRequestDTO dto) {
        if (dto == null || dto.getStatus() == null) {
            throw new DadosInvalidosException("Status do pedido não informado");
        }

        PedidoPecas pedido = PedidoPecasMapper.toModel(null, dto);
        return PedidoPecasMapper.toResponse(repository.save(pedido));
    }

    public PedidoPecasResponseDTO adicionarItem(Long codigo, AdicionarItemPedidoPecaDTO dto) {
        if (dto == null || dto.getCodigoPeca() == null || dto.getQuantidade() == null ||
                dto.getQuantidade() <= 0) {
            throw new DadosInvalidosException("Peça ou quantidade inválida");
        }

        if (!repository.existsById(codigo)) {
            throw new RecursoNaoEncontradoException("Pedido não encontrado");
        }
        if (!pecaRepository.existsById(dto.getCodigoPeca())) {
            throw new RecursoNaoEncontradoException("Peça não encontrada");
        }

        repository.adicionarItem(codigo, dto.getCodigoPeca(), dto.getQuantidade());
        PedidoPecas pedido = repository.findById(codigo)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado"));
        return PedidoPecasMapper.toResponse(pedido);
    }

    public PedidoPecasResponseDTO atualizarStatus(Long codigo, AtualizarStatusPedidoPecasDTO dto) {
        if (dto == null || dto.getStatus() == null) {
            throw new DadosInvalidosException("Status do pedido não informado");
        }

        PedidoPecas pedido = repository.findById(codigo)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado"));
        pedido.setStatus(dto.getStatus());

        return PedidoPecasMapper.toResponse(repository.update(pedido));
    }
}
