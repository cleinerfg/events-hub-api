# Events Hub API

System events management.

## Languages

- [Portuguese-BR](README.pt.md)

## Architecture

The project is structured using **Hexagonal Architecture**, focusing on:

- **Core**: Domain Logic (Hexagon Interior)
    - *Model*: Domain Entities and Value Objects
    - *Port*: Interfaces defining how the core communicates with infra.
    - *Use Case*: Orchestrate business rules and user intentions.
- **Infra**: External World (Adapters)
    - *Web*: REST Controllers and DTOs.
    - *Persistence*: Database entities and repository.
    - *Adapter*: Implementation of the Core ports to connect the domain to the infrastructure.
    - *Config*: Use Cases Bean configuration.
- **Shared**: Common components used across multiple modules, such as global exception handling.

## Technologies Used

The project was developed using **Java 17** and **Spring Boot 4.0.3**.

- **Framework**: Spring Boot (Web, Data JPA, Security, Validation)
- **Database**: PostgreSQL
- **Migration Management**: Flyway
- **Security**: Java-JWT
- **Utilities**: Lombok
- **Containerization**: Docker

## Prerequisites

Before you begin, you will need to have installed on your machine:

- [Git](https://git-scm.com)
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Docker](https://www.docker.com/)

## Installation and Execution

### 1. Clone the repository

```bash
git clone https://github.com/cleinerfg/events-hub-api.git
cd events-hub-api
```

### 2. Start the database with Docker

```bash
docker-compose up -d
```

### 3. Configure environment variables

- Create a `.env` file in the root of the project and
  configure the access credentials for the database and application.

Example:

```bash
DB_POSTGRES_USERNAME=username
DB_POSTGRES_PASSWORD=password
DB_POSTGRES_URL=jdbc:postgresql://localhost:5432/events-hub
JWT_SECRET=jwt-secret-hash
JWT_ISSUER=events-hub
JWT_EXPIRATION_SECONDS=3600
```

- Config your IDE to use environment variables.

Config IntelliJ IDEA:

```bash
Run > Edit Configurations > Add New Configuration > Aplication
```

Fill:

- `Name`: EventsHubApp
- `Main Class`: com.eventshub.EventsHubApplication
- `Environment variables`: ../events-hub-api/.env

`Note`: In the *Environment variables*, put the full path to the *.env* file.

### 4. Run the application

Execute the App.