# MINI AUTORIZADOR
Desafio desenvolvido como parte do processo seletivo para integrar o time de desenvolvimento da [VR Benefícios](https://www.vr.com.br/). Descrição do desafio e requisitos no arquivo **[DESAFIO.md](https://github.com/luisscarlos/mini-autorizador/blob/main/DESAFIO.md)**

## Build status e analises do sonar
[![build](https://github.com/luisscarlos/mini-autorizador/actions/workflows/build.yml/badge.svg)](https://github.com/luisscarlos/mini-autorizador/actions/workflows/build.yml)
[![coverage](https://sonarcloud.io/api/project_badges/measure?project=luisscarlos_mini-autorizador&metric=coverage)](https://sonarcloud.io/dashboard?id=luisscarlos_mini-autorizador)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=luisscarlos_mini-autorizador&metric=alert_status)](https://sonarcloud.io/dashboard?id=luisscarlos_mini-autorizador)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=luisscarlos_mini-autorizador&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=luisscarlos_mini-autorizador)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=luisscarlos_mini-autorizador&metric=code_smells)](https://sonarcloud.io/dashboard?id=luisscarlos_mini-autorizador)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=luisscarlos_mini-autorizador&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=luisscarlos_mini-autorizador)

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
- [Git](https://git-scm.com/)
- [Docker](https://www.docker.com/)
- [JDK 21+](https://docs.aws.amazon.com/corretto/latest/corretto-21-ug/downloads-list.html)

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
