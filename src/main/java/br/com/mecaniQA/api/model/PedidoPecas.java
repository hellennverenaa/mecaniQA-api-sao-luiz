package br.com.mecaniQA.api.model;

import java.util.ArrayList;
import java.util.List;

public class PedidoPecas {

    private Long codigo;
    private StatusPedidoPecas status;
    private List<ItemPedidoPeca> itens = new ArrayList<>();

    public PedidoPecas() {
    }

    public PedidoPecas(Long codigo, StatusPedidoPecas status, List<ItemPedidoPeca> itens) {
        this.codigo = codigo;
        this.status = status;
        this.itens = itens;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public StatusPedidoPecas getStatus() {
        return status;
    }

    public void setStatus(StatusPedidoPecas status) {
        this.status = status;
    }

    public List<ItemPedidoPeca> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoPeca> itens) {
        this.itens = itens;
    }
}