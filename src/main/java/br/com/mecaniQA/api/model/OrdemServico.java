package br.com.mecaniQA.api.model;

public class OrdemServico {

    private Long codigo;
    private StatusOrdemServico status;

    private OrdemServico(Builder builder) {
        this.codigo = builder.codigo;
        this.status = builder.status;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public StatusOrdemServico getStatus() {
        return status;
    }

    public void setStatus(StatusOrdemServico status) {
        this.status = status;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Long codigo;
        private StatusOrdemServico status;

        public Builder codigo(Long codigo) {
            this.codigo = codigo;
            return this;
        }

        public Builder status(StatusOrdemServico status) {
            this.status = status;
            return this;
        }

        public OrdemServico build() {
            return new OrdemServico(this);
        }
    }
}