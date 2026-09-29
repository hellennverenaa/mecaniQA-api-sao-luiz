package br.com.mecaniQA.api.controller;

import br.com.mecaniQA.api.dto.PecaRequestDTO;
import br.com.mecaniQA.api.dto.PecaResponseDTO;
import br.com.mecaniQA.api.mapper.PecaMapper;
import br.com.mecaniQA.api.model.Peca;
import br.com.mecaniQA.api.repository.PecaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pecas")
public class PecaController {

    private final PecaRepository pecaRepository;

    public PecaController() {
        this.pecaRepository = PecaRepository.getInstance();
    }

    @PostMapping
    public ResponseEntity<PecaResponseDTO> cadastrar(@RequestBody PecaRequestDTO dto) {
        Peca peca = pecaRepository.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(PecaMapper.toResponse(peca));
    }

    @GetMapping
    public ResponseEntity<List<PecaResponseDTO>> listar() {
        return ResponseEntity.ok(pecaRepository.listarTodos().stream()
                .map(PecaMapper::toResponse)
                .toList());
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<PecaResponseDTO> buscarPorCodigo(@PathVariable("codigo") Long codigo) {
        return pecaRepository.buscarPorCodigo(codigo)
                .map(PecaMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<PecaResponseDTO> atualizar(@PathVariable("codigo") Long codigo,
                                                     @RequestBody PecaRequestDTO dto) {
        return pecaRepository.atualizar(codigo, dto)
                .map(PecaMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> excluir(@PathVariable("codigo") Long codigo) {
        if (pecaRepository.excluir(codigo)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
