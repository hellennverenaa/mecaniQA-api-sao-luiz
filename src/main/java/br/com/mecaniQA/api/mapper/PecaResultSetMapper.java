package br.com.mecaniQA.api.mapper;

import br.com.mecaniQA.api.model.CategoriaPeca;
import br.com.mecaniQA.api.model.Peca;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class PecaResultSetMapper {

    private PecaResultSetMapper() {
    }

    public static Peca toModel(ResultSet rs) throws SQLException {
        return new Peca(
                rs.getLong("codigo"),
                rs.getString("nome"),
                rs.getString("codigo_barras"),
                rs.getString("fornecedor_marca"),
                rs.getInt("quantidade_estoque"),
                rs.getDouble("preco_custo"),
                rs.getDouble("preco_venda"),
                CategoriaPeca.valueOf(rs.getString("categoria")),
                rs.getString("tamanho"),
                rs.getString("cor"),
                rs.getTimestamp("data_cadastro").toLocalDateTime(),
                rs.getTimestamp("data_atualizacao").toLocalDateTime()
        );
    }
}
