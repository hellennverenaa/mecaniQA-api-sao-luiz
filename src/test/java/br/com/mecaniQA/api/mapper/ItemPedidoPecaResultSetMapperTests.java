package br.com.mecaniQA.api.mapper;

import br.com.mecaniQA.api.model.CategoriaPeca;
import br.com.mecaniQA.api.model.ItemPedidoPeca;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ItemPedidoPecaResultSetMapperTests {

    @Test
    void deveConverterResultSetEmItemPedidoPeca() throws Exception {
        LocalDateTime dataCadastro = LocalDateTime.of(2026, 10, 1, 10, 0);
        LocalDateTime dataAtualizacao = LocalDateTime.of(2026, 10, 2, 11, 30);
        ResultSet resultado = mock(ResultSet.class);

        when(resultado.getLong("codigo")).thenReturn(15L);
        when(resultado.getString("nome")).thenReturn("Filtro de óleo");
        when(resultado.getString("codigo_barras")).thenReturn("7890000000150");
        when(resultado.getString("fornecedor_marca")).thenReturn("MecaniQA");
        when(resultado.getInt("quantidade_estoque")).thenReturn(20);
        when(resultado.getDouble("preco_custo")).thenReturn(18.50);
        when(resultado.getDouble("preco_venda")).thenReturn(29.90);
        when(resultado.getString("categoria")).thenReturn(CategoriaPeca.MOTOR.name());
        when(resultado.getString("tamanho")).thenReturn("P");
        when(resultado.getString("cor")).thenReturn("Preto");
        when(resultado.getTimestamp("data_cadastro")).thenReturn(Timestamp.valueOf(dataCadastro));
        when(resultado.getTimestamp("data_atualizacao")).thenReturn(Timestamp.valueOf(dataAtualizacao));
        when(resultado.getInt("quantidade")).thenReturn(3);

        ItemPedidoPeca item = ItemPedidoPecaResultSetMapper.toModel(resultado);

        assertThat(item.getQuantidade()).isEqualTo(3);
        assertThat(item.getPeca().getCodigo()).isEqualTo(15L);
        assertThat(item.getPeca().getNome()).isEqualTo("Filtro de óleo");
        assertThat(item.getPeca().getCategoria()).isEqualTo(CategoriaPeca.MOTOR);
    }
}
