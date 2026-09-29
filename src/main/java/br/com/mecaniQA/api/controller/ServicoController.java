package br.com.mecaniQA.api.controller;

import br.com.mecaniQA.api.dto.ServicoRequestDTO;
import br.com.mecaniQA.api.dto.ServicoResponseDTO;
import br.com.mecaniQA.api.mapper.ServicoMapper;
import br.com.mecaniQA.api.model.Servico;
import br.com.mecaniQA.api.repository.ServicoRepository;
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

    private final ServicoRepository servicoRepository;

    public ServicoController() {
        this.servicoRepository = ServicoRepository.getInstance();
    }

    @PostMapping
    public ResponseEntity<ServicoResponseDTO> cadastrar(@RequestBody ServicoRequestDTO dto) {
        Servico servico = servicoRepository.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ServicoMapper.toResponse(servico));
    }

    @GetMapping
    public ResponseEntity<List<ServicoResponseDTO>> listar() {
        return ResponseEntity.ok(servicoRepository.listarTodos().stream()
                .map(ServicoMapper::toResponse)
                .toList());
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<ServicoResponseDTO> buscarPorCodigo(@PathVariable("codigo") Long codigo) {
        return servicoRepository.buscarPorCodigo(codigo)
                .map(ServicoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<ServicoResponseDTO> atualizar(@PathVariable("codigo") Long codigo,
                                                        @RequestBody ServicoRequestDTO dto) {
        return servicoRepository.atualizar(codigo, dto)
                .map(ServicoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> excluir(@PathVariable("codigo") Long codigo) {
        if (servicoRepository.excluir(codigo)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
