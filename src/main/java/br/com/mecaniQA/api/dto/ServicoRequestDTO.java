package br.com.mecaniQA.api.dto;

public class ServicoRequestDTO {

    private String nome;
    private Integer tempoEstimadoMinutos;
    private Double custoTabelado;

    public ServicoRequestDTO() {
    }

    public ServicoRequestDTO(String nome, Integer tempoEstimadoMinutos, Double custoTabelado) {
        this.nome = nome;
        this.tempoEstimadoMinutos = tempoEstimadoMinutos;
        this.custoTabelado = custoTabelado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getTempoEstimadoMinutos() {
        return tempoEstimadoMinutos;
    }

    public void setTempoEstimadoMinutos(Integer tempoEstimadoMinutos) {
        this.tempoEstimadoMinutos = tempoEstimadoMinutos;
    }

    public Double getCustoTabelado() {
        return custoTabelado;
    }

    public void setCustoTabelado(Double custoTabelado) {
        this.custoTabelado = custoTabelado;
    }
}
