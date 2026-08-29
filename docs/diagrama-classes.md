# Diagrama de Classes UML — MecâniQA API (Equipe São Luiz)

Diagrama estrutural da camada de domínio, objetos de transferência (DTOs), repositórios baseados no padrão **Singleton manual** em memória e controladores RESTful[cite: 3, 4].

\`\`\`mermaid
classDiagram
    direction LR

    class CategoriaPeca {
        <<enumeration>>
        MOTOR
        SUSPENSAO
        FREIOS
        ELETRICA
        ACESSORIOS
    }

    class Peca {
        -Long codigo
        -String codigoBarras
        -String fornecedorMarca
        -Integer quantidadeEstoque
        -Double precoCusto
        -Double precoVenda
        -LocalDateTime dataCadastro
        -LocalDateTime dataAtualizacao
        -String tamanho
        -String cor
        -CategoriaPeca categoria
        +Peca()
        +getCodigo() Long
        +setCodigo(Long codigo) void
        +getCodigoBarras() String
        +setCodigoBarras(String codigoBarras) void
        +getFornecedorMarca() String
        +setFornecedorMarca(String fornecedorMarca) void
        +getQuantidadeEstoque() Integer
        +setQuantidadeEstoque(Integer quantidadeEstoque) void
        +getPrecoCusto() Double
        +setPrecoCusto(Double precoCusto) void
        +getPrecoVenda() Double
        +setPrecoVenda(Double precoVenda) void
        +getDataCadastro() LocalDateTime
        +setDataCadastro(LocalDateTime dataCadastro) void
        +getDataAtualizacao() LocalDateTime
        +setDataAtualizacao(LocalDateTime dataAtualizacao) void
        +getTamanho() String
        +setTamanho(String tamanho) void
        +getCor() String
        +setCor(String cor) void
        +getCategoria() CategoriaPeca
        +setCategoria(CategoriaPeca categoria) void
    }

    class PecaRequestDTO {
        -String codigoBarras
        -String fornecedorMarca
        -Integer quantidadeEstoque
        -Double precoCusto
        -Double precoVenda
        -String tamanho
        -String cor
        -CategoriaPeca categoria
        +PecaRequestDTO()
        +getCodigoBarras() String
        +setCodigoBarras(String codigoBarras) void
        +getFornecedorMarca() String
        +setFornecedorMarca(String fornecedorMarca) void
        +getQuantidadeEstoque() Integer
        +setQuantidadeEstoque(Integer quantidadeEstoque) void
        +getPrecoCusto() Double
        +setPrecoCusto(Double precoCusto) void
        +getPrecoVenda() Double
        +setPrecoVenda(Double precoVenda) void
        +getTamanho() String
        +setTamanho(String tamanho) void
        +getCor() String
        +setCor(String cor) void
        +getCategoria() CategoriaPeca
        +setCategoria(CategoriaPeca categoria) void
    }

    class PecaRepository {
        <<Singleton>>
        -PecaRepository INSTANCE$
        -List~Peca~ pecas
        -Long proximoCodigo
        -PecaRepository()
        +getInstance()$ PecaRepository
        +salvar(PecaRequestDTO dto) Peca
        +listarTodos() List~Peca~
        +buscarPorCodigo(Long codigo) Optional~Peca~
        +atualizar(Long codigo, PecaRequestDTO dto) Optional~Peca~
        +excluir(Long codigo) boolean
    }

    class PecaController {
        -PecaRepository pecaRepository
        +PecaController()
        +cadastrar(PecaRequestDTO dto) ResponseEntity~Peca~
        +listar() ResponseEntity~List~
        +buscarPorCodigo(Long codigo) ResponseEntity~Peca~
        +atualizar(Long codigo, PecaRequestDTO dto) ResponseEntity~Peca~
        +excluir(Long codigo) ResponseEntity~Void~
    }

    class Servico {
        -Long codigo
        -String nome
        -Integer tempoEstimadoMinutos
        -Double custoTabelado
        -LocalDateTime dataCriacao
        -LocalDateTime dataAtualizacao
        +Servico()
        +getCodigo() Long
        +setCodigo(Long codigo) void
        +getNome() String
        +setNome(String nome) void
        +getTempoEstimadoMinutos() Integer
        +setTempoEstimadoMinutos(Integer tempoEstimadoMinutos) void
        +getCustoTabelado() Double
        +setCustoTabelado(Double custoTabelado) void
        +getDataCriacao() LocalDateTime
        +setDataCriacao(LocalDateTime dataCriacao) void
        +getDataAtualizacao() LocalDateTime
        +setDataAtualizacao(LocalDateTime dataAtualizacao) void
    }

    class ServicoRequestDTO {
        -String nome
        -Integer tempoEstimadoMinutos
        -Double custoTabelado
        +ServicoRequestDTO()
        +getNome() String
        +setNome(String nome) void
        +getTempoEstimadoMinutos() Integer
        +setTempoEstimadoMinutos(Integer tempoEstimadoMinutos) void
        +getCustoTabelado() Double
        +setCustoTabelado(Double custoTabelado) void
    }

    class ServicoRepository {
        <<Singleton>>
        -ServicoRepository INSTANCE$
        -List~Servico~ servicos
        -Long proximoCodigo
        -ServicoRepository()
        +getInstance()$ ServicoRepository
        +salvar(ServicoRequestDTO dto) Servico
        +listarTodos() List~Servico~
        +buscarPorCodigo(Long codigo) Optional~Servico~
        +atualizar(Long codigo, ServicoRequestDTO dto) Optional~Servico~
        +excluir(Long codigo) boolean
    }

    class ServicoController {
        -ServicoRepository servicoRepository
        +ServicoController()
        +cadastrar(ServicoRequestDTO dto) ResponseEntity~Servico~
        +listar() ResponseEntity~List~
        +buscarPorCodigo(Long codigo) ResponseEntity~Servico~
        +atualizar(Long codigo, ServicoRequestDTO dto) ResponseEntity~Servico~
        +excluir(Long codigo) ResponseEntity~Void~
    }

    %% Relacionamentos Peças
    PecaController ..> PecaRepository : getInstance()
    PecaController ..> PecaRequestDTO : recebe
    PecaRepository --> Peca : armazena
    Peca --> CategoriaPeca : possui
    PecaRequestDTO --> CategoriaPeca : possui

    %% Relacionamentos Serviços
    ServicoController ..> ServicoRepository : getInstance()
    ServicoController ..> ServicoRequestDTO : recebe
    ServicoRepository --> Servico : armazena
\`\`\`