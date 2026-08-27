# MecaniQA — Web API Fundacional (OAT 1)

> **Equipe:** São Luiz  
> **Disciplina:** Desenvolvimento Web Orientado a Objetos (UniFTC)  
> **Professor / Avaliador:** Prof. Lucas Almeida Silva (`lasilva`)  
> **Prazo do Entregável Técnico:** 29/08/2026  

---

## Sobre o Projeto

A **MecâniQA API** é uma solução de backend construída com **Java e Spring Boot** para o gerenciamento de catálogos de autopeças e tabelas de serviços automotivos. 

O projeto segue integralmente as restrições arquiteturais da **OAT 1**, priorizando o domínio prático da Programação Orientada a Objetos em memória sem o uso de bancos de dados relacionais ou injeção automática de dependências.

---

## Restrições Arquiteturais e Decisões de Engenharia

* **Persistência 100% em Memória:** Não utiliza bancos de dados físicos, instâncias embarcadas (H2/SQLite) ou interfaces Spring Data JPA.
* **Isolamento de Injeção de Dependência:** Proibido o uso de `@Autowired`, `@Component` ou `@Repository` gerenciados pelo framework Spring nos repositórios.
* **Padrão de Projeto Singleton Manual:** Classes de repositório (`PecaRepository` e `ServicoRepository`) implementadas com construtor `private`, atributo `private static NomeRepository INSTANCE` e método de acesso global `public static synchronized NomeRepository getInstance()`.
* **Controle de Concorrência (Thread-Safety):** Métodos de escrita, geração sequencial de identificadores e mutação de listas internas utilizam o modificador `synchronized`.
* **Semântica REST com `ResponseEntity`:** Todos os métodos dos controladores manipulam explicitamente os códigos de status HTTP (`201 Created`, `200 OK`, `204 No Content` e `404 Not Found`).
* **Isolamento de Entrada com DTOs:** Uso de `PecaRequestDTO` e `ServicoRequestDTO` para receber dados externos sem expor atributos de controle gerados pela aplicação (`codigo`, `dataCadastro`, `dataCriacao` e `dataAtualizacao`).

---

## Diagrama de Classes UML

O diagrama abaixo apresenta o modelo estrutural de classes da API MecâniQA, contendo modificadores de visibilidade, tipagem explícita e mapeamento do padrão Singleton:

![Diagrama de Classes UML](docs/diagrama-classes.png)

Arquivos de especificação:
* PlantUML: [`docs/diagrama-classes.puml`](docs/diagrama-classes.puml)
* Mermaid.js: [`docs/diagrama-classes.md`](docs/diagrama-classes.md)

---

## Mapeamento de Endpoints (User Stories US01 a US08)

### Catálogo de Peças (`/api/pecas`)

| Método | Endpoint | Status HTTP | Descrição / User Story |
| :--- | :--- | :---: | :--- |
| `POST` | `/api/pecas` | `201 Created` | **US01:** Cadastrar nova peça com categoria vinculada |
| `GET` | `/api/pecas` | `200 OK` | **US02:** Listar todas as peças cadastradas |
| `GET` | `/api/pecas/{codigo}` | `200 OK` / `404 Not Found` | **US02:** Buscar peça pelo código identificador |
| `PUT` | `/api/pecas/{codigo}` | `200 OK` / `404 Not Found` | **US03:** Atualizar dados, estoques e preços de uma peça |
| `DELETE` | `/api/pecas/{codigo}` | `204 No Content` / `404 Not Found` | **US04:** Excluir registro de peça do catálogo |

### Tabela de Serviços (`/api/servicos`)

| Método | Endpoint | Status HTTP | Descrição / User Story |
| :--- | :--- | :---: | :--- |
| `POST` | `/api/servicos` | `201 Created` | **US05:** Cadastrar novo serviço automotivo |
| `GET` | `/api/servicos` | `200 OK` | **US06:** Listar todos os serviços disponíveis |
| `GET` | `/api/servicos/{codigo}` | `200 OK` / `404 Not Found` | **US06:** Buscar serviço por código identificador |
| `PUT` | `/api/servicos/{codigo}` | `200 OK` / `404 Not Found` | **US07:** Atualizar tempo estimado e custo tabelado |
| `DELETE` | `/api/servicos/{codigo}` | `204 No Content` / `404 Not Found` | **US08:** Excluir serviço do portfólio |

---

## Execução do Projeto

### Pré-requisitos
* Java JDK 17 ou superior instalado.
* Gradle Wrapper (incluso no projeto).

### Comandos de Inicialização

1. Clonar o repositório oficial:
   ```bash
   git clone [https://github.com/hellennverenaa/mecaniQA-api-sao-luiz.git](https://github.com/hellennverenaa/mecaniQA-api-sao-luiz.git)
   cd mecaniQA-api-sao-luiz
