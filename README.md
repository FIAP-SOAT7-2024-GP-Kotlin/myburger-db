## Video demostração: 
https://www.youtube.com/watch?v=vr1br-s-Adk&ab_channel=VidaExtra

# myburger-management-service
myburger-db


## Introdução
Este é um projeto gerenciado usando Gradle, que utiliza o Liquibase para gerenciar a migração de bancos de dados. O Liquibase é uma ferramenta de versionamento de banco de dados open-source.

## Requisitos
Para executar este projeto, você precisará ter o seguinte software instalado:

- Java Development Kit (JDK) 21 ou superior
- Gradle 8.10 ou superior

## Configuração do Liquibase

Para executar o Liquibase, antes é necessário configurar o .env com as informações do banco de dados. O arquivo .env deve conter as seguintes variáveis:

| Variável de Ambiente | Descrição             | Valor Padrão                                 |
|----------------------|-----------------------|----------------------------------------------|
| DATABASE_URL         | URL do banco de dados | jdbc:postgresql://localhost:5432/myburger-db |
| DATABASE_USERNAME    | Nome do usuário       | postgres                                     |
| DATABASE_PASSWORD    | Senha do usuário      | myburger-db                                  |

Essas variáveis de ambiente são utilizadas para configurar a conexão com o banco de dados. O Liquibase usa essas variáveis para se conectar ao banco de dados.

## Instruções de Execução

Para executar o Liquibase, utilize o comando abaixo:
> ./gradlew --no-daemon update

Este comando executará as migrações do banco de dados.

## Organização dos Arquivos e Diretórios

Este projeto segue a organização padrão do Gradle, com os seguintes diretórios:
- `src/main/resources`: Contém recursos como arquivos de configuração e scripts SQL para Liquibase.
- `.env`: Arquivo contendo informações sensíveis, como credenciais do banco de dados.
- `build.gradle.kts`: Arquivo principal do Gradle que define as tarefas e configurações do projeto.

Dentro do diretório `src/main/resources`, existem os seguintes arquivos relevantes para o Liquibase:
- `masters.xml`: Arquivo principal do Liquibase que define as sequências de migrações.

## Banco de Dados

Neste projeto, estamos utilizando um banco de dados PostgreSQL. Este banco de dados é famoso e amplamente utilizado. 
Robustez e confiabilidade são características importantes para bancos de dados do PostgreSQL. Ele é um banco de dados 
padrão em qualquer cloud provider que ofereça serviços de hospedagem de aplicações.

Devido ao escopo do projeto com as seguintes características: 
- Sistema com usuarios limitados devido a interação por totem e pontos na cozinha
- Buscas sem necessidade de alto desempenho (podemos usar um cache no API Gateway)


