package br.com.mecaniQA.api.repository;

import br.com.mecaniQA.api.database.ConexaoMySql;
import br.com.mecaniQA.api.mapper.PecaResultSetMapper;
import br.com.mecaniQA.api.mapper.PedidoPecasResultSetMapper;
import br.com.mecaniQA.api.model.ItemPedidoPeca;
import br.com.mecaniQA.api.model.PedidoPecas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PedidoPecasRepository implements Repository<PedidoPecas, Long> {

    private static PedidoPecasRepository INSTANCE;

    private PedidoPecasRepository() {
    }

    public static synchronized PedidoPecasRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PedidoPecasRepository();
        }
        return INSTANCE;
    }

    @Override
    public synchronized PedidoPecas save(PedidoPecas pedido) {
        String sql = "INSERT INTO pedidos_pecas (status) VALUES (?)";

        try (Connection conexao = ConexaoMySql.abrir();
             PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, pedido.getStatus().name());
            statement.executeUpdate();
            try (ResultSet chaves = statement.getGeneratedKeys()) {
                if (chaves.next()) {
                    pedido.setCodigo(chaves.getLong(1));
                }
            }
            return pedido;
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao cadastrar pedido no MySQL", e);
        }
    }

    @Override
    public Optional<PedidoPecas> findById(Long codigo) {
        String sql = "SELECT * FROM pedidos_pecas WHERE codigo = ?";
        PedidoPecas pedido;

        try (Connection conexao = ConexaoMySql.abrir();
             PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setLong(1, codigo);
            try (ResultSet resultado = statement.executeQuery()) {
                if (!resultado.next()) {
                    return Optional.empty();
                }
                pedido = PedidoPecasResultSetMapper.toModel(resultado);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao buscar pedido no MySQL", e);
        }

        carregarItens(pedido);
        return Optional.of(pedido);
    }

    @Override
    public List<PedidoPecas> findAll() {
        String sql = "SELECT * FROM pedidos_pecas ORDER BY codigo";
        List<PedidoPecas> pedidos = new ArrayList<>();

        try (Connection conexao = ConexaoMySql.abrir();
             PreparedStatement statement = conexao.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {
            while (resultado.next()) {
                pedidos.add(PedidoPecasResultSetMapper.toModel(resultado));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao listar pedidos no MySQL", e);
        }

        for (PedidoPecas pedido : pedidos) {
            carregarItens(pedido);
        }
        return pedidos;
    }

    @Override
    public synchronized PedidoPecas update(PedidoPecas pedido) {
        String sql = "UPDATE pedidos_pecas SET status = ? WHERE codigo = ?";

        try (Connection conexao = ConexaoMySql.abrir();
             PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setString(1, pedido.getStatus().name());
            statement.setLong(2, pedido.getCodigo());
            statement.executeUpdate();
            return pedido;
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao atualizar pedido no MySQL", e);
        }
    }

    @Override
    public synchronized void deleteById(Long codigo) {
        String excluirItens = "DELETE FROM itens_pedido_peca WHERE pedido_codigo = ?";
        String excluirPedido = "DELETE FROM pedidos_pecas WHERE codigo = ?";

        try (Connection conexao = ConexaoMySql.abrir()) {
            conexao.setAutoCommit(false);
            try (PreparedStatement itens = conexao.prepareStatement(excluirItens);
                 PreparedStatement pedido = conexao.prepareStatement(excluirPedido)) {
                itens.setLong(1, codigo);
                itens.executeUpdate();
                pedido.setLong(1, codigo);
                pedido.executeUpdate();
                conexao.commit();
            } catch (SQLException e) {
                conexao.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao excluir pedido no MySQL", e);
        }
    }

    @Override
    public boolean existsById(Long codigo) {
        String sql = "SELECT 1 FROM pedidos_pecas WHERE codigo = ?";

        try (Connection conexao = ConexaoMySql.abrir();
             PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setLong(1, codigo);
            try (ResultSet resultado = statement.executeQuery()) {
                return resultado.next();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao verificar pedido no MySQL", e);
        }
    }

    private void carregarItens(PedidoPecas pedido) {
        String sql = "SELECT p.*, i.quantidade FROM itens_pedido_peca i " +
                "JOIN pecas p ON p.codigo = i.peca_codigo WHERE i.pedido_codigo = ? " +
                "ORDER BY i.peca_codigo";

        try (Connection conexao = ConexaoMySql.abrir();
             PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setLong(1, pedido.getCodigo());
            try (ResultSet resultado = statement.executeQuery()) {
                List<ItemPedidoPeca> itens = new ArrayList<>();
                while (resultado.next()) {
                    itens.add(new ItemPedidoPeca(
                            PecaResultSetMapper.toModel(resultado),
                            resultado.getInt("quantidade")
                    ));
                }
                pedido.setItens(itens);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao carregar peças do pedido no MySQL", e);
        }
    }

    public synchronized void adicionarItem(Long codigoPedido, Long codigoPeca, Integer quantidade) {
        String sql = "INSERT INTO itens_pedido_peca (pedido_codigo, peca_codigo, quantidade) " +
                "VALUES (?, ?, ?)";

        try (Connection conexao = ConexaoMySql.abrir();
             PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setLong(1, codigoPedido);
            statement.setLong(2, codigoPeca);
            statement.setInt(3, quantidade);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao adicionar peça ao pedido no MySQL", e);
        }
    }
}
