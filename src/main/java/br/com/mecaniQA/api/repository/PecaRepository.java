package br.com.mecaniQA.api.repository;

import br.com.mecaniQA.api.database.ConexaoMySql;
import br.com.mecaniQA.api.dto.PecaRequestDTO;
import br.com.mecaniQA.api.mapper.PecaMapper;
import br.com.mecaniQA.api.mapper.PecaResultSetMapper;
import br.com.mecaniQA.api.model.Peca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PecaRepository implements Repository<Peca, Long> {

    private static PecaRepository INSTANCE;

    private PecaRepository() {
    }

    public static synchronized PecaRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PecaRepository();
        }
        return INSTANCE;
    }

    @Override
    public synchronized Peca save(Peca peca) {
        String sql = "INSERT INTO pecas (nome, codigo_barras, fornecedor_marca, " +
                "quantidade_estoque, preco_custo, preco_venda, categoria, tamanho, cor, " +
                "data_cadastro, data_atualizacao) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoMySql.abrir();
             PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preencherDados(statement, peca);
            statement.executeUpdate();

            try (ResultSet chaves = statement.getGeneratedKeys()) {
                if (chaves.next()) {
                    peca.setCodigo(chaves.getLong(1));
                }
            }
            return peca;
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao cadastrar peça no MySQL", e);
        }
    }

    @Override
    public Optional<Peca> findById(Long codigo) {
        String sql = "SELECT * FROM pecas WHERE codigo = ?";

        try (Connection conexao = ConexaoMySql.abrir();
             PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setLong(1, codigo);
            try (ResultSet resultado = statement.executeQuery()) {
                if (resultado.next()) {
                    return Optional.of(PecaResultSetMapper.toModel(resultado));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao buscar peça no MySQL", e);
        }
    }

    @Override
    public List<Peca> findAll() {
        String sql = "SELECT * FROM pecas ORDER BY codigo";
        List<Peca> pecas = new ArrayList<>();

        try (Connection conexao = ConexaoMySql.abrir();
             PreparedStatement statement = conexao.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {
            while (resultado.next()) {
                pecas.add(PecaResultSetMapper.toModel(resultado));
            }
            return pecas;
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao listar peças no MySQL", e);
        }
    }

    @Override
    public synchronized Peca update(Peca peca) {
        String sql = "UPDATE pecas SET nome = ?, codigo_barras = ?, fornecedor_marca = ?, " +
                "quantidade_estoque = ?, preco_custo = ?, preco_venda = ?, categoria = ?, " +
                "tamanho = ?, cor = ?, data_cadastro = ?, data_atualizacao = ? WHERE codigo = ?";

        try (Connection conexao = ConexaoMySql.abrir();
             PreparedStatement statement = conexao.prepareStatement(sql)) {
            preencherDados(statement, peca);
            statement.setLong(12, peca.getCodigo());
            statement.executeUpdate();
            return peca;
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao atualizar peça no MySQL", e);
        }
    }

    @Override
    public synchronized void deleteById(Long codigo) {
        String sql = "DELETE FROM pecas WHERE codigo = ?";

        try (Connection conexao = ConexaoMySql.abrir();
             PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setLong(1, codigo);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao excluir peça no MySQL", e);
        }
    }

    @Override
    public boolean existsById(Long codigo) {
        String sql = "SELECT 1 FROM pecas WHERE codigo = ?";

        try (Connection conexao = ConexaoMySql.abrir();
             PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setLong(1, codigo);
            try (ResultSet resultado = statement.executeQuery()) {
                return resultado.next();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao verificar peça no MySQL", e);
        }
    }

    // Mantém os métodos usados pelos controllers até a adaptação da camada Service.
    public Peca salvar(PecaRequestDTO dto) {
        return save(PecaMapper.toModel(null, dto, LocalDateTime.now()));
    }

    public List<Peca> listarTodos() {
        return findAll();
    }

    public Optional<Peca> buscarPorCodigo(Long codigo) {
        return findById(codigo);
    }

    public synchronized Optional<Peca> atualizar(Long codigo, PecaRequestDTO dto) {
        Optional<Peca> encontrada = findById(codigo);
        encontrada.ifPresent(peca -> {
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
            update(peca);
        });
        return encontrada;
    }

    public synchronized boolean excluir(Long codigo) {
        if (!existsById(codigo)) {
            return false;
        }
        deleteById(codigo);
        return true;
    }

    private void preencherDados(PreparedStatement statement, Peca peca) throws SQLException {
        statement.setString(1, peca.getNome());
        statement.setString(2, peca.getCodigoBarras());
        statement.setString(3, peca.getFornecedorMarca());
        statement.setInt(4, peca.getQuantidadeEstoque());
        statement.setDouble(5, peca.getPrecoCusto());
        statement.setDouble(6, peca.getPrecoVenda());
        statement.setString(7, peca.getCategoria().name());
        statement.setString(8, peca.getTamanho());
        statement.setString(9, peca.getCor());
        statement.setTimestamp(10, Timestamp.valueOf(peca.getDataCadastro()));
        statement.setTimestamp(11, Timestamp.valueOf(peca.getDataAtualizacao()));
    }
}
