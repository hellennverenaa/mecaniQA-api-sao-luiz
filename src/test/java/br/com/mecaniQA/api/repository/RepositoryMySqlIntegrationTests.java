package br.com.mecaniQA.api.repository;

import br.com.mecaniQA.api.database.ConexaoMySql;
import br.com.mecaniQA.api.model.CategoriaPeca;
import br.com.mecaniQA.api.model.OrdemServico;
import br.com.mecaniQA.api.model.Peca;
import br.com.mecaniQA.api.model.PedidoPecas;
import br.com.mecaniQA.api.model.Servico;
import br.com.mecaniQA.api.model.StatusOrdemServico;
import br.com.mecaniQA.api.model.StatusPedidoPecas;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.DockerClientFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class RepositoryMySqlIntegrationTests {

    private static final String URL_MYSQL_TESTE =
            "jdbc:tc:mysql:8.0.36:///mecaniqa?TC_DAEMON=true";

    private final PecaRepository pecaRepository = PecaRepository.getInstance();
    private final ServicoRepository servicoRepository = ServicoRepository.getInstance();
    private final OrdemServicoRepository ordemRepository = OrdemServicoRepository.getInstance();
    private final PedidoPecasRepository pedidoRepository = PedidoPecasRepository.getInstance();

    @BeforeAll
    static void configurarBanco() throws Exception {
        assumeTrue(
                DockerClientFactory.instance().isDockerAvailable(),
                "Docker não disponível; integração MySQL não executada"
        );

        System.setProperty("DB_URL", URL_MYSQL_TESTE);
        System.setProperty("DB_USER", "test");
        System.setProperty("DB_PASSWORD", "test");

        executarScriptSql(Path.of("sql", "mecaniqa.sql"));
    }

    @BeforeEach
    void limparBanco() throws Exception {
        try (Connection conexao = ConexaoMySql.abrir();
             Statement statement = conexao.createStatement()) {
            statement.executeUpdate("DELETE FROM itens_pedido_peca");
            statement.executeUpdate("DELETE FROM pedidos_pecas");
            statement.executeUpdate("DELETE FROM ordens_servico");
            statement.executeUpdate("DELETE FROM servicos");
            statement.executeUpdate("DELETE FROM pecas");
        }
    }

    @AfterAll
    static void removerConfiguracao() {
        System.clearProperty("DB_URL");
        System.clearProperty("DB_USER");
        System.clearProperty("DB_PASSWORD");
    }

    @Test
    void deveExecutarCrudCompletoComMySqlReal() {
        LocalDateTime agora = LocalDateTime.of(2026, 10, 20, 9, 0);

        Peca peca = new Peca(
                null, "Pastilha de freio", "7890000000013", "MecaniQA", 10,
                55.0, 89.9, CategoriaPeca.FREIOS, "M", "Cinza", agora, agora
        );
        pecaRepository.save(peca);
        assertThat(peca.getCodigo()).isNotNull();
        assertThat(pecaRepository.findById(peca.getCodigo())).isPresent();
        peca.setQuantidadeEstoque(25);
        pecaRepository.update(peca);
        assertThat(pecaRepository.findById(peca.getCodigo()).orElseThrow().getQuantidadeEstoque())
                .isEqualTo(25);

        Servico servico = new Servico(null, "Troca de pastilhas", 60, 150.0, agora, agora);
        servicoRepository.save(servico);
        assertThat(servicoRepository.findAll()).hasSize(1);
        servico.setCustoTabelado(175.0);
        servicoRepository.update(servico);
        assertThat(servicoRepository.findById(servico.getCodigo()).orElseThrow().getCustoTabelado())
                .isEqualTo(175.0);

        OrdemServico ordem = OrdemServico.builder()
                .status(StatusOrdemServico.ABERTO)
                .build();
        ordemRepository.save(ordem);
        ordem.setStatus(StatusOrdemServico.EM_EXECUCAO);
        ordemRepository.update(ordem);
        assertThat(ordemRepository.findById(ordem.getCodigo()).orElseThrow().getStatus())
                .isEqualTo(StatusOrdemServico.EM_EXECUCAO);

        PedidoPecas pedido = new PedidoPecas(null, StatusPedidoPecas.ORCANDO, new java.util.ArrayList<>());
        pedidoRepository.save(pedido);
        pedidoRepository.adicionarItem(pedido.getCodigo(), peca.getCodigo(), 2);
        PedidoPecas pedidoPersistido = pedidoRepository.findById(pedido.getCodigo()).orElseThrow();
        assertThat(pedidoPersistido.getItens()).hasSize(1);
        assertThat(pedidoPersistido.getItens().getFirst().getQuantidade()).isEqualTo(2);

        pedidoPersistido.setStatus(StatusPedidoPecas.PAGO_FATURADO);
        pedidoRepository.update(pedidoPersistido);
        assertThat(pedidoRepository.findById(pedido.getCodigo()).orElseThrow().getStatus())
                .isEqualTo(StatusPedidoPecas.PAGO_FATURADO);

        pedidoRepository.deleteById(pedido.getCodigo());
        ordemRepository.deleteById(ordem.getCodigo());
        servicoRepository.deleteById(servico.getCodigo());
        pecaRepository.deleteById(peca.getCodigo());

        assertThat(pedidoRepository.existsById(pedido.getCodigo())).isFalse();
        assertThat(ordemRepository.existsById(ordem.getCodigo())).isFalse();
        assertThat(servicoRepository.existsById(servico.getCodigo())).isFalse();
        assertThat(pecaRepository.existsById(peca.getCodigo())).isFalse();
    }

    private static void executarScriptSql(Path caminho) throws Exception {
        String script = Files.readString(caminho);
        try (Connection conexao = ConexaoMySql.abrir();
             Statement statement = conexao.createStatement()) {
            for (String comando : script.split(";")) {
                String sql = comando.lines()
                        .filter(linha -> !linha.stripLeading().startsWith("--"))
                        .reduce("", (acumulado, linha) -> acumulado + "\n" + linha)
                        .trim();
                if (!sql.isBlank()) {
                    statement.execute(sql);
                }
            }
        }
    }
}
