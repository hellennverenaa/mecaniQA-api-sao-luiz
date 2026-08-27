package br.com.mecaniQA.api.controller;

import br.com.mecaniQA.api.dto.PecaRequestDTO;
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
    public ResponseEntity<Peca> cadastrar(@RequestBody PecaRequestDTO dto) {
        Peca peca = pecaRepository.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(peca);
    }

    @GetMapping
    public ResponseEntity<List<Peca>> listar() {
        return ResponseEntity.ok(pecaRepository.listarTodos());
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Peca> buscarPorCodigo(@PathVariable("codigo") Long codigo) {
        return pecaRepository.buscarPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Peca> atualizar(@PathVariable("codigo") Long codigo,
                                          @RequestBody PecaRequestDTO dto) {
        return pecaRepository.atualizar(codigo, dto)
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
