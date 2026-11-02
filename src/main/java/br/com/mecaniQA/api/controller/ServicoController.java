package br.com.mecaniQA.api.controller;

import br.com.mecaniQA.api.dto.ServicoRequestDTO;
import br.com.mecaniQA.api.dto.ServicoResponseDTO;
import br.com.mecaniQA.api.exception.DadosInvalidosException;
import br.com.mecaniQA.api.exception.RecursoNaoEncontradoException;
import br.com.mecaniQA.api.service.ServicoService;
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
@RequestMapping("/api/servicos")
public class ServicoController {

    private final ServicoService servicoService = new ServicoService();

    @PostMapping
    public ResponseEntity<ServicoResponseDTO> cadastrar(@RequestBody ServicoRequestDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(servicoService.cadastrar(dto));
        } catch (DadosInvalidosException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ServicoResponseDTO>> listar() {
        return ResponseEntity.ok(servicoService.listar());
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<ServicoResponseDTO> buscarPorCodigo(@PathVariable("codigo") Long codigo) {
        try {
            return ResponseEntity.ok(servicoService.buscarPorCodigo(codigo));
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<ServicoResponseDTO> atualizar(@PathVariable("codigo") Long codigo,
                                                        @RequestBody ServicoRequestDTO dto) {
        try {
            return ResponseEntity.ok(servicoService.atualizar(codigo, dto));
        } catch (DadosInvalidosException e) {
            return ResponseEntity.badRequest().build();
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> excluir(@PathVariable("codigo") Long codigo) {
        try {
            servicoService.excluir(codigo);
            return ResponseEntity.noContent().build();
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
