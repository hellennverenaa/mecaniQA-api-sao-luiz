package br.com.mecaniQA.api.repository;

import br.com.mecaniQA.api.dto.ServicoRequestDTO;
import br.com.mecaniQA.api.model.Servico;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServicoRepository {

    private static ServicoRepository INSTANCE;

    private final List<Servico> servicos = new ArrayList<>();
    private Long proximoCodigo = 1L;

    private ServicoRepository() {
    }

    public static synchronized ServicoRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ServicoRepository();
        }

        return INSTANCE;
    }

    public synchronized Servico salvar(ServicoRequestDTO dto) {
        LocalDateTime agora = LocalDateTime.now();
        Servico servico = new Servico(
                proximoCodigo++,
                dto.getNome(),
                dto.getTempoEstimadoMinutos(),
                dto.getCustoTabelado(),
                agora,
                agora
        );

        servicos.add(servico);
        return servico;
    }

    public List<Servico> listarTodos() {
        return new ArrayList<>(servicos);
    }

    public Optional<Servico> buscarPorCodigo(Long codigo) {
        return servicos.stream()
                .filter(servico -> servico.getCodigo().equals(codigo))
                .findFirst();
    }

    public synchronized Optional<Servico> atualizar(Long codigo, ServicoRequestDTO dto) {
        Optional<Servico> servicoEncontrado = buscarPorCodigo(codigo);

        servicoEncontrado.ifPresent(servico -> {
            servico.setNome(dto.getNome());
            servico.setTempoEstimadoMinutos(dto.getTempoEstimadoMinutos());
            servico.setCustoTabelado(dto.getCustoTabelado());
            servico.setDataAtualizacao(LocalDateTime.now());
        });

        return servicoEncontrado;
    }

    public synchronized boolean excluir(Long codigo) {
        return servicos.removeIf(servico -> servico.getCodigo().equals(codigo));
    }
}
