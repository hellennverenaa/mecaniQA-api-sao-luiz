package br.com.mecaniQA.api.service;

import br.com.mecaniQA.api.dto.AtualizarStatusOrdemServicoDTO;
import br.com.mecaniQA.api.dto.OrdemServicoRequestDTO;
import br.com.mecaniQA.api.dto.OrdemServicoResponseDTO;
import br.com.mecaniQA.api.exception.DadosInvalidosException;
import br.com.mecaniQA.api.exception.RecursoNaoEncontradoException;
import br.com.mecaniQA.api.mapper.OrdemServicoMapper;
import br.com.mecaniQA.api.model.OrdemServico;
import br.com.mecaniQA.api.repository.OrdemServicoRepository;

public class OrdemServicoService {

    private final OrdemServicoRepository repository = OrdemServicoRepository.getInstance();

    public OrdemServicoResponseDTO criar(OrdemServicoRequestDTO dto) {
        if (dto == null || dto.getStatus() == null) {
            throw new DadosInvalidosException("Status da ordem de serviço não informado");
        }

        OrdemServico ordem = OrdemServicoMapper.toModel(null, dto);
        return OrdemServicoMapper.toResponse(repository.save(ordem));
    }

    public OrdemServicoResponseDTO atualizarStatus(Long codigo, AtualizarStatusOrdemServicoDTO dto) {
        if (dto == null || dto.getStatus() == null) {
            throw new DadosInvalidosException("Status da ordem de serviço não informado");
        }

        OrdemServico ordem = repository.findById(codigo)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Ordem de serviço não encontrada"));
        ordem.setStatus(dto.getStatus());

        return OrdemServicoMapper.toResponse(repository.update(ordem));
    }
}
