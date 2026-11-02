package br.com.mecaniQA.api.repository;

import br.com.mecaniQA.api.database.ConexaoMySql;
import br.com.mecaniQA.api.dto.OrdemServicoRequestDTO;
import br.com.mecaniQA.api.mapper.OrdemServicoMapper;
import br.com.mecaniQA.api.mapper.OrdemServicoResultSetMapper;
import br.com.mecaniQA.api.model.OrdemServico;
import br.com.mecaniQA.api.model.StatusOrdemServico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdemServicoRepository implements Repository<OrdemServico, Long> {

    private static OrdemServicoRepository INSTANCE;

    private OrdemServicoRepository() {
    }

    public static synchronized OrdemServicoRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OrdemServicoRepository();
        }
        return INSTANCE;
    }

    @Override
    public synchronized OrdemServico save(OrdemServico ordem) {
        String sql = "INSERT INTO ordens_servico (status) VALUES (?)";

        try (Connection conexao = ConexaoMySql.abrir();
             PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, ordem.getStatus().name());
            statement.executeUpdate();
            try (ResultSet chaves = statement.getGeneratedKeys()) {
                if (chaves.next()) {
                    ordem.setCodigo(chaves.getLong(1));
                }
            }
            return ordem;
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao cadastrar ordem de serviço no MySQL", e);
        }
    }

    @Override
    public Optional<OrdemServico> findById(Long codigo) {
        String sql = "SELECT * FROM ordens_servico WHERE codigo = ?";

        try (Connection conexao = ConexaoMySql.abrir();
             PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setLong(1, codigo);
            try (ResultSet resultado = statement.executeQuery()) {
                if (resultado.next()) {
                    return Optional.of(OrdemServicoResultSetMapper.toModel(resultado));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao buscar ordem de serviço no MySQL", e);
        }
    }

    @Override
    public List<OrdemServico> findAll() {
        String sql = "SELECT * FROM ordens_servico ORDER BY codigo";
        List<OrdemServico> ordens = new ArrayList<>();

        try (Connection conexao = ConexaoMySql.abrir();
             PreparedStatement statement = conexao.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {
            while (resultado.next()) {
                ordens.add(OrdemServicoResultSetMapper.toModel(resultado));
            }
            return ordens;
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao listar ordens de serviço no MySQL", e);
        }
    }

    @Override
    public synchronized OrdemServico update(OrdemServico ordem) {
        String sql = "UPDATE ordens_servico SET status = ? WHERE codigo = ?";

        try (Connection conexao = ConexaoMySql.abrir();
             PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setString(1, ordem.getStatus().name());
            statement.setLong(2, ordem.getCodigo());
            statement.executeUpdate();
            return ordem;
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao atualizar ordem de serviço no MySQL", e);
        }
    }

    @Override
    public synchronized void deleteById(Long codigo) {
        String sql = "DELETE FROM ordens_servico WHERE codigo = ?";

        try (Connection conexao = ConexaoMySql.abrir();
             PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setLong(1, codigo);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao excluir ordem de serviço no MySQL", e);
        }
    }

    @Override
    public boolean existsById(Long codigo) {
        String sql = "SELECT 1 FROM ordens_servico WHERE codigo = ?";

        try (Connection conexao = ConexaoMySql.abrir();
             PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setLong(1, codigo);
            try (ResultSet resultado = statement.executeQuery()) {
                return resultado.next();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao verificar ordem de serviço no MySQL", e);
        }
    }

    // Mantém os métodos utilizados na OAT 2 até a criação da camada Service.
    public OrdemServico salvar(OrdemServicoRequestDTO dto) {
        return save(OrdemServicoMapper.toModel(null, dto));
    }

    public Optional<OrdemServico> buscarPorCodigo(Long codigo) {
        return findById(codigo);
    }

    public synchronized Optional<OrdemServico> atualizarStatus(Long codigo,
                                                                StatusOrdemServico status) {
        Optional<OrdemServico> encontrada = findById(codigo);
        encontrada.ifPresent(ordem -> {
            ordem.setStatus(status);
            update(ordem);
        });
        return encontrada;
    }
}
