# 📚 Library Management API

API RESTful desenvolvida com **Spring Boot** para gerenciamento de livros, autores e editoras.

---

## 🧠 Funcionalidades

- 📘 Cadastro de livros
- 👤 Cadastro de autores
- 🏢 Cadastro de editoras (publishers)
- 🔗 Relacionamento entre entidades:
  - Um livro possui uma editora
  - Um livro pode ter vários autores (ManyToMany)
- 🔄 Operações CRUD completas:
  - Criar
  - Listar
  - Buscar por ID
  - Atualizar
  - Deletar

---

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas:

```
controller → service → repository → model
```

Além disso, utiliza:

- DTOs para entrada e saída de dados
- Separação de responsabilidades
- Boas práticas com Spring

---

## 📊 Modelagem do Banco

### 📘 Book

| Campo | Tipo |
|---|---|
| `id` | UUID |
| `name` | String |
| `publisher_id` | UUID (FK) |
| `authors` | ManyToMany |

### 👤 Author

| Campo | Tipo |
|---|---|
| `id` | UUID |
| `name` | String |

### 🏢 Publisher

| Campo | Tipo |
|---|---|
| `id` | UUID |
| `name` | String |

### 🔗 Tabela intermediária — `tb_book_authors`

| Campo | Tipo |
|---|---|
| `book_id` | UUID (FK) |
| `author_id` | UUID (FK) |

---

## 🔧 Como rodar o projeto

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/seu-repo.git
cd seu-repo
```

### 2. Configure o banco de dados

Crie um banco no PostgreSQL e adicione as seguintes configurações no `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/seu_banco
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 3. Execute a aplicação

**Linux/Mac:**
```bash
./mvnw spring-boot:run
```

**Windows:**
```bash
mvnw.cmd spring-boot:run
```

---

## 🌐 Endpoints

### 📘 Books

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/books` | Listar todos os livros |
| `GET` | `/books/{id}` | Buscar livro por ID |
| `POST` | `/books` | Criar novo livro |
| `PUT` | `/books/{id}` | Atualizar livro |
| `DELETE` | `/books/{id}` | Deletar livro |

### 👤 Authors

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/authors` | Listar todos os autores |
| `GET` | `/authors/{id}` | Buscar autor por ID |
| `POST` | `/authors` | Criar novo autor |
| `PUT` | `/authors/{id}` | Atualizar autor |
| `DELETE` | `/authors/{id}` | Deletar autor |

### 🏢 Publishers

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/publishers` | Listar todas as editoras |
| `GET` | `/publishers/{id}` | Buscar editora por ID |
| `POST` | `/publishers` | Criar nova editora |
| `PUT` | `/publishers/{id}` | Atualizar editora |
| `DELETE` | `/publishers/{id}` | Deletar editora |

---

## 📥 Exemplos de Requisição

### Criar um livro — `POST /books`

```json
{
  "name": "Outliers",
  "publisherId": "UUID_DO_PUBLISHER",
  "authorIds": [
    "UUID_AUTOR_1",
    "UUID_AUTOR_2"
  ]
}
```

---

## 🛠️ Tecnologias

- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven
