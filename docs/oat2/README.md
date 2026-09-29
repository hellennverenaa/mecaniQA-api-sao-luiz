# Diagramas da OAT 2

Esta pasta preserva os diagramas da OAT 1 e documenta a implementação final da OAT 2.

## Diagrama de Classes

O arquivo `diagrama-classes.puml` mostra apenas as classes necessárias para explicar a OAT 2:

- `OrdemServico`, `PedidoPecas` e `ItemPedidoPeca`;
- `StatusOrdemServico` e `StatusPedidoPecas`;
- `OrdemServico.Builder` e seu relacionamento com `OrdemServico`;
- DTOs de entrada e saída;
- `OrdemServicoMapper` e `PedidoPecasMapper`;
- a classe `Peca`, preservada da OAT 1 porque participa da associação com o pedido.

![Diagrama de Classes da OAT 2](diagrama-classes.png)

## Diagrama de Atividade

O arquivo `diagrama-atividade.puml` representa somente a visão lógica do método
`PedidoPecasController.adicionarItem`, associado à US04:

`POST /api/pedidos-pecas/{codigo}/itens`

O fluxo contém somente:

- validação do DTO;
- `400 Bad Request` para dados inválidos;
- `404 Not Found` quando pedido ou peça não são encontrados;
- conversão da resposta para DTO;
- `200 OK` no caminho de sucesso.

O diagrama omite repository, banco de dados e detalhes de infraestrutura para manter a leitura simples.

![Diagrama de Atividade da US04](diagrama-atividade.png)

## Arquivos disponíveis

- `.puml`: fonte editável em PlantUML;
- `.svg`: imagem vetorial para documentos e apresentações;
- `.png`: visualização pronta para consulta e entrega.
