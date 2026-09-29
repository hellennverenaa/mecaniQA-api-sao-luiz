package br.com.mecaniQA.api.controller;

import br.com.mecaniQA.api.dto.AdicionarItemPedidoPecaDTO;
import br.com.mecaniQA.api.dto.AtualizarStatusPedidoPecasDTO;
import br.com.mecaniQA.api.dto.PedidoPecasRequestDTO;
import br.com.mecaniQA.api.dto.PedidoPecasResponseDTO;
import br.com.mecaniQA.api.mapper.PedidoPecasMapper;
import br.com.mecaniQA.api.model.PedidoPecas;
import br.com.mecaniQA.api.repository.PedidoPecasRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pedidos-pecas")
public class PedidoPecasController {

    private final PedidoPecasRepository pedidoPecasRepository;

    public PedidoPecasController() {
        this.pedidoPecasRepository = PedidoPecasRepository.getInstance();
    }

    @PostMapping
    public ResponseEntity<PedidoPecasResponseDTO> criar(
            @RequestBody PedidoPecasRequestDTO dto) {
        if (dto.getStatus() == null) {
            return ResponseEntity.badRequest().build();
        }

        PedidoPecas pedido = pedidoPecasRepository.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PedidoPecasMapper.toResponse(pedido));
    }

    @PostMapping("/{codigo}/itens")
    public ResponseEntity<PedidoPecasResponseDTO> adicionarItem(
            @PathVariable("codigo") Long codigo,
            @RequestBody AdicionarItemPedidoPecaDTO dto) {
        if (dto.getCodigoPeca() == null || dto.getQuantidade() == null || dto.getQuantidade() <= 0) {
            return ResponseEntity.badRequest().build();
        }

        return pedidoPecasRepository.adicionarItem(codigo, dto)
                .map(PedidoPecasMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{codigo}/status")
    public ResponseEntity<PedidoPecasResponseDTO> atualizarStatus(
            @PathVariable("codigo") Long codigo,
            @RequestBody AtualizarStatusPedidoPecasDTO dto) {
        if (dto.getStatus() == null) {
            return ResponseEntity.badRequest().build();
        }

        return pedidoPecasRepository.atualizarStatus(codigo, dto.getStatus())
                .map(PedidoPecasMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
