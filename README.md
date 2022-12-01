Desafio desenvolvido para integrar o time de desenvolvimento da [VR Benefícios](https://www.vr.com.br/) através da [Elumini](https://www.elumini.com.br/pb/). Descrição do desafio e requisitos no arquivo **[DESAFIO.md](https://github.com/luisscarlos/mini-autorizador/blob/main/DESAFIO.md)**

# Tecnologias utilizadas

- **[Spring Boot](https://spring.io/projects/spring-boot)**
- **[Spring Data JPA](https://spring.io/projects/spring-data-jpa#overview)** 
- **[Hibernate](https://hibernate.org/orm/)**
- **[Lombok](https://projectlombok.org/)**
- **[Docker](https://www.docker.com/)**
- **[MySQL](https://www.mysql.com/)**


# Requisitos para executar o projeto
- [Git](https://git-scm.com/)
- [Docker](https://www.docker.com/)
- [JDK 11+](https://www.oracle.com/br/java/technologies/javase/jdk11-archive-downloads.html)

# Como executar o projeto
- Clone o projeto.
```bash
  git clone https://github.com/luisscarlos/mini-autorizador.git
```
- Abra um terminal na raiz do projeto e execute o comando abaixo para iniciar o banco de dados Mysql no docker.
```bash
  cd docker && docker-compose up -d
```

- Abra a IDE de sua preferência e importe o projeto clonado e aguarde o download de todas dependências do projeto

- Execute o arquivo com a classe main MiniAutorizadorApplication.java

- Acesse a interface dos recursos do backend através do swagger usando o endereço local http://localhost:8080


# Rotas
## Cartão Controller
| Método  | Path  | Descrição  |
| ------------ | ------------ | ------------ |
| POST  |  /cartoes | Cria um novo cartão |
| GET  |  /cartoes/{numeroCartao} | Consulta saldo do cartão |

## Transação Controller
| Método  | Path  | Descrição  |
| ------------ | ------------ | ------------ |
| POST  |  /transacoes | Realiza uma transação |