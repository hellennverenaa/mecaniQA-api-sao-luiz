package br.com.mecaniQA.api.service;

import br.com.mecaniQA.api.dto.PecaRequestDTO;
import br.com.mecaniQA.api.dto.PecaResponseDTO;
import br.com.mecaniQA.api.exception.DadosInvalidosException;
import br.com.mecaniQA.api.exception.RecursoNaoEncontradoException;
import br.com.mecaniQA.api.mapper.PecaMapper;
import br.com.mecaniQA.api.model.Peca;
import br.com.mecaniQA.api.repository.PecaRepository;

import java.time.LocalDateTime;
import java.util.List;

public class PecaService {

    private final PecaRepository repository = PecaRepository.getInstance();

    public PecaResponseDTO cadastrar(PecaRequestDTO dto) {
        validar(dto);
        Peca peca = PecaMapper.toModel(null, dto, LocalDateTime.now());
        return PecaMapper.toResponse(repository.save(peca));
    }

    public List<PecaResponseDTO> listar() {
        return repository.findAll().stream().map(PecaMapper::toResponse).toList();
    }

    public PecaResponseDTO buscarPorCodigo(Long codigo) {
        Peca peca = repository.findById(codigo)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Peça não encontrada"));
        return PecaMapper.toResponse(peca);
    }

    public PecaResponseDTO atualizar(Long codigo, PecaRequestDTO dto) {
        validar(dto);
        Peca peca = repository.findById(codigo)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Peça não encontrada"));

        peca.setNome(dto.getNome());
        peca.setCodigoBarras(dto.getCodigoBarras());
        peca.setFornecedorMarca(dto.getFornecedorMarca());
        peca.setQuantidadeEstoque(dto.getQuantidadeEstoque());
        peca.setPrecoCusto(dto.getPrecoCusto());
        peca.setPrecoVenda(dto.getPrecoVenda());
        peca.setCategoria(dto.getCategoria());
        peca.setTamanho(dto.getTamanho());
        peca.setCor(dto.getCor());
        peca.setDataAtualizacao(LocalDateTime.now());

        return PecaMapper.toResponse(repository.update(peca));
    }

    public void excluir(Long codigo) {
        if (!repository.existsById(codigo)) {
            throw new RecursoNaoEncontradoException("Peça não encontrada");
        }
        repository.deleteById(codigo);
    }

    private void validar(PecaRequestDTO dto) {
        if (dto == null || dto.getCodigoBarras() == null || dto.getFornecedorMarca() == null ||
                dto.getQuantidadeEstoque() == null || dto.getPrecoCusto() == null ||
                dto.getPrecoVenda() == null || dto.getCategoria() == null) {
            throw new DadosInvalidosException("Dados obrigatórios da peça não informados");
        }
    }
}
