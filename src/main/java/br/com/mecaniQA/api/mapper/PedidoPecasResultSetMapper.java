package br.com.mecaniQA.api.mapper;

import br.com.mecaniQA.api.model.PedidoPecas;
import br.com.mecaniQA.api.model.StatusPedidoPecas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public final class PedidoPecasResultSetMapper {

    private PedidoPecasResultSetMapper() {
    }

    public static PedidoPecas toModel(ResultSet rs) throws SQLException {
        return new PedidoPecas(
                rs.getLong("codigo"),
                StatusPedidoPecas.valueOf(rs.getString("status")),
                new ArrayList<>()
        );
    }
}
