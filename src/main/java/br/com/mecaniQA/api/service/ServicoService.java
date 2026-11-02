package br.com.mecaniQA.api.service;

import br.com.mecaniQA.api.dto.ServicoRequestDTO;
import br.com.mecaniQA.api.dto.ServicoResponseDTO;
import br.com.mecaniQA.api.exception.DadosInvalidosException;
import br.com.mecaniQA.api.exception.RecursoNaoEncontradoException;
import br.com.mecaniQA.api.mapper.ServicoMapper;
import br.com.mecaniQA.api.model.Servico;
import br.com.mecaniQA.api.repository.ServicoRepository;

import java.time.LocalDateTime;
import java.util.List;

public class ServicoService {

    private final ServicoRepository repository = ServicoRepository.getInstance();

    public ServicoResponseDTO cadastrar(ServicoRequestDTO dto) {
        validar(dto);
        Servico servico = ServicoMapper.toModel(null, dto, LocalDateTime.now());
        return ServicoMapper.toResponse(repository.save(servico));
    }

    public List<ServicoResponseDTO> listar() {
        return repository.findAll().stream().map(ServicoMapper::toResponse).toList();
    }

    public ServicoResponseDTO buscarPorCodigo(Long codigo) {
        Servico servico = repository.findById(codigo)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Serviço não encontrado"));
        return ServicoMapper.toResponse(servico);
    }

    public ServicoResponseDTO atualizar(Long codigo, ServicoRequestDTO dto) {
        validar(dto);
        Servico servico = repository.findById(codigo)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Serviço não encontrado"));

        servico.setNome(dto.getNome());
        servico.setTempoEstimadoMinutos(dto.getTempoEstimadoMinutos());
        servico.setCustoTabelado(dto.getCustoTabelado());
        servico.setDataAtualizacao(LocalDateTime.now());

        return ServicoMapper.toResponse(repository.update(servico));
    }

    public void excluir(Long codigo) {
        if (!repository.existsById(codigo)) {
            throw new RecursoNaoEncontradoException("Serviço não encontrado");
        }
        repository.deleteById(codigo);
    }

    private void validar(ServicoRequestDTO dto) {
        if (dto == null || dto.getNome() == null || dto.getTempoEstimadoMinutos() == null ||
                dto.getCustoTabelado() == null) {
            throw new DadosInvalidosException("Dados obrigatórios do serviço não informados");
        }
    }
}
