# 📝 Todo Lists API

My first Java project, built with the **Spring Boot** framework. It is a REST API for managing tasks (todo list), featuring JWT authentication and a database hosted on Supabase.

---

## 🚀 Tech Stack

- **Java 26 (26.0.1 OpenJDK)**
- **Spring Boot** — main framework
- **Spring Security** — authentication & authorization
- **JWT** — token management
- **Supabase (PostgreSQL)** — database
- **JPA / Hibernate** — ORM
- **Maven** — dependency management

---

## ⚙️ Prerequisites

- Java 26+
- Maven
- A [Supabase](https://supabase.com) account with a project created

---

## 🛠️ Installation & Setup

### 1. Clone the repository

```bash
git clone https://github.com/ton-user/todo-lists.git
cd todo-lists
```

### 2. Configure environment variables

Create a `.env` file at the root of the project and fill in the following values:

```properties
DB_DATASOURCE_URL=jdbc:postgresql://db.<your-project>.supabase.co:5432/postgres
DB_USERNAME=postgres.<username>
DB_PASSWORD=<password>
DB_URL=https://<db_id>.supabase.co
JWT_SECRET=<secret>
SUPABASE_SERVICE_KEY=<key>
SUPABASE_ANON_KEY=<key>
```

### 3. Run the project

**In development (with Maven):**
```bash
./mvnw spring-boot:run
```

**By building and running the JAR:**
```bash
./mvnw clean package
java -jar target/todo-lists-0.0.1-SNAPSHOT.jar
```

The API will be available at: `http://localhost:8080`

---

## 🧪 Running Tests

```bash
./mvnw test
```

Tests are written with **JUnit 5** and **MockMvc**, covering the repository, service and controller via `@WebMvcTest`.

---

## 🔧 Manual Testing

The API routes were manually tested using **[Postman](https://www.postman.com)**. To test the protected routes, set the `Authorization` header with the JWT token obtained after login:

```
Authorization: Bearer <your-token>
```

---

## 📖 API Documentation (Swagger)

Interactive Swagger documentation will is available [here](http://localhost:8080/swagger-ui/index.html).

---

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/example/todolists/
│   │   ├── configuration/        # Security and .env config
│   │   ├── domain/
│   │   │   └── todo/
│   │   │       ├── controller/   # REST controllers
│   │   │       ├── service/      # Business logic
│   │   │       ├── repository/   # Database access
│   │   │       └── dto/          # Data transfer objects
│   │   ├── exception/            # Handler and exception definition
│   │   └── security/             # JWT, filters, Spring Security config
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/example/todolists/
        └── todo/
│   │   │   ├── controller/       # Controller tests
│   │   │   ├── repository/       # Repository tests
│   │   │   └── service/          # Service tests
```

---

## 🔐 Authentication

The API uses **JWT (JSON Web Token)**. To access protected routes, you must first authenticate and include the token in your request headers:

```
Authorization: Bearer <your-token>
```

---

## 📌 Main Routes

| Method | Route | Description | Auth required |
|--------|-------|-------------|---------------|
| `POST` | `/auth/register` | Create an account | ❌ |
| `POST` | `/auth/login` | Log in | ❌ |
| `GET` | `/todos` | List your tasks | ✅ |
| `POST` | `/todos` | Create a task | ✅ |
| `GET` | `/todos/{id}` | Get a task by ID | ✅ |
| `PATCH` | `/todos/{id}` | Update a task | ✅ |
| `DELETE` | `/todos/{id}` | Delete a task | ✅ |

---

## 🌱 What I Learned

This project was an opportunity to explore:
- The layered architecture of a Spring Boot application (controller / service / repository)
- Setting up JWT authentication with Spring Security
- Writing controller unit tests with MockMvc
- Connecting to a remote PostgreSQL database via Supabase

---

## 📄 License

Personal project — free to use for learning purposes.