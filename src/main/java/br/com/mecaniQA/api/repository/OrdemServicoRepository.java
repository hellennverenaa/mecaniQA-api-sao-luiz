package br.com.mecaniQA.api.repository;

import br.com.mecaniQA.api.dto.OrdemServicoRequestDTO;
import br.com.mecaniQA.api.mapper.OrdemServicoMapper;
import br.com.mecaniQA.api.model.OrdemServico;
import br.com.mecaniQA.api.model.StatusOrdemServico;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdemServicoRepository {

    private static OrdemServicoRepository INSTANCE;

    private final List<OrdemServico> ordens = new ArrayList<>();
    private Long proximoCodigo = 1L;

    private OrdemServicoRepository() {
    }

    public static synchronized OrdemServicoRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OrdemServicoRepository();
        }

        return INSTANCE;
    }

    public synchronized OrdemServico salvar(OrdemServicoRequestDTO dto) {
        OrdemServico ordemServico = OrdemServicoMapper.toModel(proximoCodigo++, dto);
        ordens.add(ordemServico);
        return ordemServico;
    }

    public Optional<OrdemServico> buscarPorCodigo(Long codigo) {
        return ordens.stream()
                .filter(ordem -> ordem.getCodigo().equals(codigo))
                .findFirst();
    }

    public synchronized Optional<OrdemServico> atualizarStatus(Long codigo,
                                                                StatusOrdemServico status) {
        Optional<OrdemServico> ordemEncontrada = buscarPorCodigo(codigo);
        ordemEncontrada.ifPresent(ordem -> ordem.setStatus(status));
        return ordemEncontrada;
    }
}
