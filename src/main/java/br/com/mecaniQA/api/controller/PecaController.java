package br.com.mecaniQA.api.controller;

import br.com.mecaniQA.api.dto.PecaRequestDTO;
import br.com.mecaniQA.api.dto.PecaResponseDTO;
import br.com.mecaniQA.api.exception.DadosInvalidosException;
import br.com.mecaniQA.api.exception.RecursoNaoEncontradoException;
import br.com.mecaniQA.api.service.PecaService;
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

    private final PecaService pecaService = new PecaService();

    @PostMapping
    public ResponseEntity<PecaResponseDTO> cadastrar(@RequestBody PecaRequestDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(pecaService.cadastrar(dto));
        } catch (DadosInvalidosException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<PecaResponseDTO>> listar() {
        return ResponseEntity.ok(pecaService.listar());
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<PecaResponseDTO> buscarPorCodigo(@PathVariable("codigo") Long codigo) {
        try {
            return ResponseEntity.ok(pecaService.buscarPorCodigo(codigo));
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<PecaResponseDTO> atualizar(@PathVariable("codigo") Long codigo,
                                                     @RequestBody PecaRequestDTO dto) {
        try {
            return ResponseEntity.ok(pecaService.atualizar(codigo, dto));
        } catch (DadosInvalidosException e) {
            return ResponseEntity.badRequest().build();
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> excluir(@PathVariable("codigo") Long codigo) {
        try {
            pecaService.excluir(codigo);
            return ResponseEntity.noContent().build();
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
