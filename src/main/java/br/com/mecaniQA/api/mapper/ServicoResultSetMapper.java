package br.com.mecaniQA.api.mapper;

import br.com.mecaniQA.api.model.Servico;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class ServicoResultSetMapper {

    private ServicoResultSetMapper() {
    }

    public static Servico toModel(ResultSet rs) throws SQLException {
        return new Servico(
                rs.getLong("codigo"),
                rs.getString("nome"),
                rs.getInt("tempo_estimado_minutos"),
                rs.getDouble("custo_tabelado"),
                rs.getTimestamp("data_criacao").toLocalDateTime(),
                rs.getTimestamp("data_atualizacao").toLocalDateTime()
        );
    }
}
