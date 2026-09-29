package br.com.mecaniQA.api.controller;

import br.com.mecaniQA.api.dto.AtualizarStatusOrdemServicoDTO;
import br.com.mecaniQA.api.dto.OrdemServicoRequestDTO;
import br.com.mecaniQA.api.dto.OrdemServicoResponseDTO;
import br.com.mecaniQA.api.mapper.OrdemServicoMapper;
import br.com.mecaniQA.api.model.OrdemServico;
import br.com.mecaniQA.api.repository.OrdemServicoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ordens-servico")
public class OrdemServicoController {

    private final OrdemServicoRepository ordemServicoRepository;

    public OrdemServicoController() {
        this.ordemServicoRepository = OrdemServicoRepository.getInstance();
    }

    @PostMapping
    public ResponseEntity<OrdemServicoResponseDTO> criar(
            @RequestBody OrdemServicoRequestDTO dto) {
        if (dto.getStatus() == null) {
            return ResponseEntity.badRequest().build();
        }

        OrdemServico ordemServico = ordemServicoRepository.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(OrdemServicoMapper.toResponse(ordemServico));
    }

    @PutMapping("/{codigo}/status")
    public ResponseEntity<OrdemServicoResponseDTO> atualizarStatus(
            @PathVariable("codigo") Long codigo,
            @RequestBody AtualizarStatusOrdemServicoDTO dto) {
        if (dto.getStatus() == null) {
            return ResponseEntity.badRequest().build();
        }

        return ordemServicoRepository.atualizarStatus(codigo, dto.getStatus())
                .map(OrdemServicoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
