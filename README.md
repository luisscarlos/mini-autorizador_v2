<div style="text-align: center;">
  <img src="https://www.vr.com.br/lumis-theme/br/com/vr/portal/theme/vr-portal/img/svg/logo-vr.svg" alt="VR Logo">
</div>

# MINI AUTORIZADOR
Desafio desenvolvido como parte do processo seletivo para integrar o time de desenvolvimento da [VR Benefícios](https://www.vr.com.br/). Descrição do desafio e requisitos no arquivo **[DESAFIO.md](https://github.com/luisscarlos/mini-autorizador_v2/blob/main/DESAFIO.md)**

## Build status e analises do sonar
[![build](https://github.com/luisscarlos/mini-autorizador_v2/actions/workflows/build.yml/badge.svg)](https://github.com/luisscarlos/mini-autorizador_v2/actions/workflows/build.yml)
[![coverage](https://sonarcloud.io/api/project_badges/measure?project=luisscarlos_mini-autorizador_v2&metric=coverage)](https://sonarcloud.io/dashboard?id=luisscarlos_mini-autorizador_v2)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=luisscarlos_mini-autorizador_v2&metric=alert_status)](https://sonarcloud.io/dashboard?id=luisscarlos_mini-autorizador_v2)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=luisscarlos_mini-autorizador_v2&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=luisscarlos_mini-autorizador_v2)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=luisscarlos_mini-autorizador_v2&metric=code_smells)](https://sonarcloud.io/dashboard?id=luisscarlos_mini-autorizador_v2)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=luisscarlos_mini-autorizador_v2&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=luisscarlos_mini-autorizador)

# Tecnologias utilizadas

- **[Github Actions](https://github.com/features/actions)**
- **[Spring Boot](https://spring.io/projects/spring-boot)**
- **[Spring Data JPA](https://spring.io/projects/spring-data-jpa#overview)** 
- **[Spring Security](https://spring.io/projects/spring-security)**
- **[Hibernate](https://hibernate.org/orm/)**
- **[Lombok](https://projectlombok.org/)**
- **[Docker](https://www.docker.com/)**
- **[MySQL](https://www.mysql.com/)**
- **[JaCoCo](https://www.eclemma.org/jacoco/)**
- **[Sonar](https://www.sonarsource.com/)**

# Requisitos para executar o projeto
- **[Git](https://git-scm.com/)**
- **[Docker](https://www.docker.com/)**
- **[JDK 21+](https://docs.aws.amazon.com/corretto/latest/corretto-21-ug/downloads-list.html)**

# Como executar o projeto
- Clone o projeto.
```bash
  git clone https://github.com/luisscarlos/mini-autorizador_v2.git
```
- Abra um terminal na raiz do projeto e execute o comando abaixo para iniciar o banco de dados Mysql no docker.
```bash
  cd docker && docker-compose up -d
```

- Abra a IDE de sua preferência e importe o projeto clonado e aguarde o download de todas dependências do projeto

- Execute o arquivo com a classe main MiniAutorizadorApplication.java

- Acesse a interface dos recursos do backend através do swagger usando o endereço local http://localhost:8080

- Se preferir, no pacote [postman](https://github.com/luisscarlos/mini-autorizador_v2/tree/main/postman) do projeto, existe uma collection com as requisições para serem importadas no Postman.

# Endpoints
## Cartão Controller
| Método  | Path  | Descrição  | Autenticação |
| ------------ | ------------ | ------------ | ------------ |
| POST  |  /cartoes | Cria um novo cartão | Basic Auth |
| GET  |  /cartoes/{numeroCartao} | Consulta saldo do cartão | Basic Auth |

## Transação Controller
| Método  | Path  | Descrição  | Autenticação |
| ------------ | ------------ | ------------ | ------------ |
| POST  |  /transacoes | Realiza uma transação | Basic Auth |

### Itens de atualização comparados ao [mini-autorizador_v1](https://github.com/luisscarlos/mini-autorizador_v1)
- Atualização do Spring Boot 2.7.5 para 3.3.2
- Atualização do Java 11 para 21
    - Consequentemente, mudança da API javax para jakarta nas classes
- Atualização do springdoc-openapi
- Atualização do jacoco-maven-plugin da versão 0.8.6 para 0.8.12, pois essa anterior possui vulnerabilidades
- Utilização do Spring Security para autenticação das requisições
- Melhora no tratamento de erros e estrutura do projeto
- Mudança no controle de concorrência para executar uma transação no cartão

### Itens a serem implementados
- Ofuscar o número do cartão, mostrando só os 4 primeiros e 4 últimos números
- Adicionar database migration com [Flyway](https://www.red-gate.com/products/flyway/community/)
- Configurar [logback](http://logback.qos.ch/) para padronizar logs da aplicação