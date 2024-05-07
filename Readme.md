# Descrição do Projeto
#### Este projeto é uma aplicação para gerenciamento de contas a pagar, desenvolvida em Java utilizando o framework Spring Boot e o banco de dados PostgreSQL.

## Documentação
- Na pasta "docs" deste projeto, você encontrará recursos úteis para testar e explorar a aplicação:
#### Arquivo CSV
- O arquivo `file-to-upload.csv` localizado em `docs/` pode ser utilizado para testar a funcionalidade de importação de contas a pagar via arquivo CSV. Este arquivo contém dados fictícios de contas a pagar que podem ser utilizados para realizar testes na aplicação.
#### Arquivo do Insomnia
- O arquivo `insomnia.json` localizado em `docs/` contém uma coleção do Insomnia com os endpoints da API mapeados. Você pode importar este arquivo no Insomnia para facilitar o teste e a exploração das APIs da aplicação.

## Requisitos Gerais
- Utilização da linguagem de programação Java, versão 17 ou superior.
- Utilização do framework Spring Boot.
- Utilização do banco de dados PostgreSQL.
- Execução da aplicação em um container Docker.
- Orquestração da aplicação, banco de dados e outros serviços necessários utilizando Docker Compose.
- Hospedagem do código do projeto no GitHub ou GitLab.
- Utilização de mecanismo de autenticação.
- Organização do projeto com Domain Driven Design.
- Utilização do Flyway para criar a estrutura de banco de dados.
- Utilização de JPA.
- Paginação de todas as APIs de consulta.

## Requisitos Específicos
- Implementação da entidade "Conta" na aplicação.
- Implementação das seguintes APIs:
- Cadastrar conta;
- Atualizar conta;
- Alterar a situação da conta;
- Obter a lista de contas a pagar, com filtro de data de vencimento e descrição;
- Obter conta filtrando pelo id;
- Obter valor total pago por período.
- Implementação de um mecanismo para importação de contas a pagar via arquivo CSV, que será consumido através de uma API.
- Implementação de testes unitários.
