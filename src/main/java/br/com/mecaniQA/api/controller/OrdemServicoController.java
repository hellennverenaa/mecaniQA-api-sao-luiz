package br.com.mecaniQA.api.controller;

import br.com.mecaniQA.api.dto.AtualizarStatusOrdemServicoDTO;
import br.com.mecaniQA.api.dto.OrdemServicoRequestDTO;
import br.com.mecaniQA.api.dto.OrdemServicoResponseDTO;
import br.com.mecaniQA.api.exception.DadosInvalidosException;
import br.com.mecaniQA.api.exception.RecursoNaoEncontradoException;
import br.com.mecaniQA.api.service.OrdemServicoService;
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

    private final OrdemServicoService ordemServicoService = new OrdemServicoService();

    @PostMapping
    public ResponseEntity<OrdemServicoResponseDTO> criar(
            @RequestBody OrdemServicoRequestDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(ordemServicoService.criar(dto));
        } catch (DadosInvalidosException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{codigo}/status")
    public ResponseEntity<OrdemServicoResponseDTO> atualizarStatus(
            @PathVariable("codigo") Long codigo,
            @RequestBody AtualizarStatusOrdemServicoDTO dto) {
        try {
            return ResponseEntity.ok(ordemServicoService.atualizarStatus(codigo, dto));
        } catch (DadosInvalidosException e) {
            return ResponseEntity.badRequest().build();
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
