# Events Hub API

Sistema de gestão de eventos.

## Idiomas

- [Inglês](README.md)

## Arquitetura

O projeto é estruturado utilizando **Arquitetura Hexagonal**, focando em:

- **Core**: Lógica de Domínio (Interior do Hexágono)
    - *Model*: Entidades de Domínio e Objetos de Valor.
    - *Port*: Interfaces que definem como o core se comunica com a infra.
    - *Use Case*: Orquestra as regras de negócio e intenções do usuário.
- **Infra**: Mundo Externo (Adaptadores)
    - *Web*: REST Controllers e DTOs.
    - *Persistence*: Entidades de banco de dados e repositório.
    - *Adapter*: Implementação das portas do Core para conectar o domínio à infraestrutura.
    - *Config*: Configuração de Beans dos Use Cases.
- **Shared**: Componentes comuns utilizados em múltiplos módulos, como o tratamento global de exceções.

## Tecnologias Usadas

O projeto foi desenvolvido usando **Java 17** e **Spring Boot 4.0.3**.

- **Framework**: Spring Boot (Web, Data JPA, Security, Validation)
- **Banco de Dados**: PostgreSQL
- **Gestão de Migrações**: Flyway
- **Segurança**: Java-JWT
- **Utilitários**: Lombok
- **Conteinerização**: Docker

## Pré-requisitos

Antes de começar, você precisará ter instalado em sua máquina:

- [Git](https://git-scm.com)
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Docker](https://www.docker.com/)

## Instalação e Execução

### 1. Clonar o repositório

```bash
git clone https://github.com/cleinerfg/events-hub-api.git
cd events-hub-api
```

### 2. Iniciar o banco de dados com o Docker

```bash
docker-compose up -d
```

### 3. Configurar variáveis de ambiente

- Crie um arquivo `.env` na raiz do projeto e
  configure as credenciais de acesso ao banco de dados e da aplicação.

Exemplo:

```bash
DB_POSTGRES_USERNAME=username
DB_POSTGRES_PASSWORD=password
DB_POSTGRES_URL=jdbc:postgresql://localhost:5432/events-hub
```

- Configure sua IDE para usar variáveis de ambiente.

Configuração IntelliJ IDEA:

```bash
Run > Edit Configurations > Add New Configuration > Aplication
```

Preencha:

- `Name`: EventsHubApp
- `Main Class`: com.eventshub.EventsHubApplication
- `Environment variables`: ../events-hub-api/.env

`Nota`: Em *Environment variables*, coloque o caminho completo para o arquivo *.env*.

### 4. Execute a aplicação

Execute a App.