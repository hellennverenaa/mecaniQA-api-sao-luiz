package br.com.mecaniQA.api.controller;

import br.com.mecaniQA.api.dto.AdicionarItemPedidoPecaDTO;
import br.com.mecaniQA.api.dto.AtualizarStatusPedidoPecasDTO;
import br.com.mecaniQA.api.dto.PedidoPecasRequestDTO;
import br.com.mecaniQA.api.dto.PedidoPecasResponseDTO;
import br.com.mecaniQA.api.exception.DadosInvalidosException;
import br.com.mecaniQA.api.exception.RecursoNaoEncontradoException;
import br.com.mecaniQA.api.service.PedidoPecasService;
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

    private final PedidoPecasService pedidoPecasService = new PedidoPecasService();

    @PostMapping
    public ResponseEntity<PedidoPecasResponseDTO> criar(
            @RequestBody PedidoPecasRequestDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(pedidoPecasService.criar(dto));
        } catch (DadosInvalidosException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{codigo}/itens")
    public ResponseEntity<PedidoPecasResponseDTO> adicionarItem(
            @PathVariable("codigo") Long codigo,
            @RequestBody AdicionarItemPedidoPecaDTO dto) {
        try {
            return ResponseEntity.ok(pedidoPecasService.adicionarItem(codigo, dto));
        } catch (DadosInvalidosException e) {
            return ResponseEntity.badRequest().build();
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{codigo}/status")
    public ResponseEntity<PedidoPecasResponseDTO> atualizarStatus(
            @PathVariable("codigo") Long codigo,
            @RequestBody AtualizarStatusPedidoPecasDTO dto) {
        try {
            return ResponseEntity.ok(pedidoPecasService.atualizarStatus(codigo, dto));
        } catch (DadosInvalidosException e) {
            return ResponseEntity.badRequest().build();
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
