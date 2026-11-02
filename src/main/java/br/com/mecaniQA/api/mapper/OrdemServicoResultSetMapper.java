package br.com.mecaniQA.api.mapper;

import br.com.mecaniQA.api.model.OrdemServico;
import br.com.mecaniQA.api.model.StatusOrdemServico;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class OrdemServicoResultSetMapper {

    private OrdemServicoResultSetMapper() {
    }

    public static OrdemServico toModel(ResultSet rs) throws SQLException {
        return OrdemServico.builder()
                .codigo(rs.getLong("codigo"))
                .status(StatusOrdemServico.valueOf(rs.getString("status")))
                .build();
    }
}
