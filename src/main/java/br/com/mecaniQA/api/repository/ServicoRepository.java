package br.com.mecaniQA.api.repository;

import br.com.mecaniQA.api.database.ConexaoMySql;
import br.com.mecaniQA.api.dto.ServicoRequestDTO;
import br.com.mecaniQA.api.mapper.ServicoMapper;
import br.com.mecaniQA.api.mapper.ServicoResultSetMapper;
import br.com.mecaniQA.api.model.Servico;

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

public class ServicoRepository implements Repository<Servico, Long> {

    private static ServicoRepository INSTANCE;

    private ServicoRepository() {
    }

    public static synchronized ServicoRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ServicoRepository();
        }
        return INSTANCE;
    }

    @Override
    public synchronized Servico save(Servico servico) {
        String sql = "INSERT INTO servicos (nome, tempo_estimado_minutos, custo_tabelado, " +
                "data_criacao, data_atualizacao) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoMySql.abrir();
             PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preencherDados(statement, servico);
            statement.executeUpdate();
            try (ResultSet chaves = statement.getGeneratedKeys()) {
                if (chaves.next()) {
                    servico.setCodigo(chaves.getLong(1));
                }
            }
            return servico;
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao cadastrar serviço no MySQL", e);
        }
    }

    @Override
    public Optional<Servico> findById(Long codigo) {
        String sql = "SELECT * FROM servicos WHERE codigo = ?";

        try (Connection conexao = ConexaoMySql.abrir();
             PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setLong(1, codigo);
            try (ResultSet resultado = statement.executeQuery()) {
                if (resultado.next()) {
                    return Optional.of(ServicoResultSetMapper.toModel(resultado));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao buscar serviço no MySQL", e);
        }
    }

    @Override
    public List<Servico> findAll() {
        String sql = "SELECT * FROM servicos ORDER BY codigo";
        List<Servico> servicos = new ArrayList<>();

        try (Connection conexao = ConexaoMySql.abrir();
             PreparedStatement statement = conexao.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {
            while (resultado.next()) {
                servicos.add(ServicoResultSetMapper.toModel(resultado));
            }
            return servicos;
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao listar serviços no MySQL", e);
        }
    }

    @Override
    public synchronized Servico update(Servico servico) {
        String sql = "UPDATE servicos SET nome = ?, tempo_estimado_minutos = ?, " +
                "custo_tabelado = ?, data_criacao = ?, data_atualizacao = ? WHERE codigo = ?";

        try (Connection conexao = ConexaoMySql.abrir();
             PreparedStatement statement = conexao.prepareStatement(sql)) {
            preencherDados(statement, servico);
            statement.setLong(6, servico.getCodigo());
            statement.executeUpdate();
            return servico;
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao atualizar serviço no MySQL", e);
        }
    }

    @Override
    public synchronized void deleteById(Long codigo) {
        String sql = "DELETE FROM servicos WHERE codigo = ?";

        try (Connection conexao = ConexaoMySql.abrir();
             PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setLong(1, codigo);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao excluir serviço no MySQL", e);
        }
    }

    @Override
    public boolean existsById(Long codigo) {
        String sql = "SELECT 1 FROM servicos WHERE codigo = ?";

        try (Connection conexao = ConexaoMySql.abrir();
             PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setLong(1, codigo);
            try (ResultSet resultado = statement.executeQuery()) {
                return resultado.next();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao verificar serviço no MySQL", e);
        }
    }

    // Mantém os métodos usados pelos controllers até a adaptação da camada Service.
    public Servico salvar(ServicoRequestDTO dto) {
        return save(ServicoMapper.toModel(null, dto, LocalDateTime.now()));
    }

    public List<Servico> listarTodos() {
        return findAll();
    }

    public Optional<Servico> buscarPorCodigo(Long codigo) {
        return findById(codigo);
    }

    public synchronized Optional<Servico> atualizar(Long codigo, ServicoRequestDTO dto) {
        Optional<Servico> encontrado = findById(codigo);
        encontrado.ifPresent(servico -> {
            servico.setNome(dto.getNome());
            servico.setTempoEstimadoMinutos(dto.getTempoEstimadoMinutos());
            servico.setCustoTabelado(dto.getCustoTabelado());
            servico.setDataAtualizacao(LocalDateTime.now());
            update(servico);
        });
        return encontrado;
    }

    public synchronized boolean excluir(Long codigo) {
        if (!existsById(codigo)) {
            return false;
        }
        deleteById(codigo);
        return true;
    }

    private void preencherDados(PreparedStatement statement, Servico servico) throws SQLException {
        statement.setString(1, servico.getNome());
        statement.setInt(2, servico.getTempoEstimadoMinutos());
        statement.setDouble(3, servico.getCustoTabelado());
        statement.setTimestamp(4, Timestamp.valueOf(servico.getDataCriacao()));
        statement.setTimestamp(5, Timestamp.valueOf(servico.getDataAtualizacao()));
    }
}
