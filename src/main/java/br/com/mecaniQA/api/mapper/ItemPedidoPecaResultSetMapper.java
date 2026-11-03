package br.com.mecaniQA.api.mapper;

import br.com.mecaniQA.api.model.ItemPedidoPeca;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class ItemPedidoPecaResultSetMapper {

    private ItemPedidoPecaResultSetMapper() {
    }

    public static ItemPedidoPeca toModel(ResultSet rs) throws SQLException {
        return new ItemPedidoPeca(
                PecaResultSetMapper.toModel(rs),
                rs.getInt("quantidade")
        );
    }
}
