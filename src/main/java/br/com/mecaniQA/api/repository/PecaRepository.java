package br.com.mecaniQA.api.repository;

import br.com.mecaniQA.api.dto.PecaRequestDTO;
import br.com.mecaniQA.api.model.Peca;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PecaRepository {

    private static PecaRepository INSTANCE;

    private final List<Peca> pecas = new ArrayList<>();
    private Long proximoCodigo = 1L;

    private PecaRepository() {
    }

    public static synchronized PecaRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PecaRepository();
        }

        return INSTANCE;
    }

    public synchronized Peca salvar(PecaRequestDTO dto) {
        LocalDateTime agora = LocalDateTime.now();
        Peca peca = new Peca(
                proximoCodigo++,
                dto.getNome(),
                dto.getCodigoBarras(),
                dto.getFornecedorMarca(),
                dto.getQuantidadeEstoque(),
                dto.getPrecoCusto(),
                dto.getPrecoVenda(),
                dto.getCategoria(),
                dto.getTamanho(),
                dto.getCor(),
                agora,
                agora
        );

        pecas.add(peca);
        return peca;
    }

    public List<Peca> listarTodos() {
        return new ArrayList<>(pecas);
    }

    public Optional<Peca> buscarPorCodigo(Long codigo) {
        return pecas.stream()
                .filter(peca -> peca.getCodigo().equals(codigo))
                .findFirst();
    }

    public synchronized Optional<Peca> atualizar(Long codigo, PecaRequestDTO dto) {
        Optional<Peca> pecaEncontrada = buscarPorCodigo(codigo);

        pecaEncontrada.ifPresent(peca -> {
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
        });

        return pecaEncontrada;
    }

    public synchronized boolean excluir(Long codigo) {
        return pecas.removeIf(peca -> peca.getCodigo().equals(codigo));
    }
}
