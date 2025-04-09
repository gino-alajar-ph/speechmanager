# 🗣️ Speech Manager API

A RESTful API for politicians to manage their public speeches. Built with **Spring Boot 3**, **H2**, **JPA**, **MapStruct**, **Hibernate Validator**, and **OpenAPI (Swagger UI)**.

---

## 🚀 Features

- 📝 Add, update, delete speeches
- 📚 View all speeches
- 🔍 Search by author, date range, keyword
- ✅ Validation on input (e.g. title, author required)
- 🧪 Unit-tested with JUnit, Mockito & Instancio
- 🔄 Clean DTO–Entity mapping using MapStruct
- 🧭 Interactive API docs with Swagger

---

## ⚙️ Technologies

| Tool              | Version        |
|------------------|----------------|
| Java             | 17             |
| Spring Boot      | 3.2.4          |
| H2               | In-memory DB   |
| MapStruct        | 1.5.5.Final    |
| Hibernate Validator | 8+         |
| Swagger UI       | springdoc 2.1.0 |
| JUnit 5 / Mockito | For testing    |

---

## 🛠️ Setup

### Prerequisites
- JDK 17+
- Maven 3.8+
- (Optional) Postman / curl

---

### 🔁 Clone the Repository

```bash
git clone https://github.com/gino-alajar-ph/speechmanager.git
cd speechmanager
```

---

### 🚧 Build & Run

```bash
# 1. Build the project
mvn clean install

# 2. Run the app
mvn spring-boot:run
```

---

### 🌐 API Docs (Swagger)

Once running, access:

📄 `http://localhost:8080/swagger-ui.html`  
📘 OpenAPI JSON: `http://localhost:8080/v3/api-docs`

---

## 📦 Endpoints Summary

| Method | Endpoint                 | Description              |
|--------|--------------------------|--------------------------|
| `GET`  | `/api/speeches`         | List all speeches        |
| `POST` | `/api/speeches`         | Add new speech           |
| `PUT`  | `/api/speeches/{id}`    | Update existing speech   |
| `DELETE` | `/api/speeches/{id}`  | Delete speech by ID      |
| `GET`  | `/api/speeches/search`  | Search speeches          |

---

## 📄 Sample Request: Add a Speech

```json
POST /api/speeches
Content-Type: application/json

{
  "title": "State of the Union",
  "body": "My fellow citizens...",
  "author": "President Jane Doe",
  "speechDate": "2025-04-10",
  "keywords": ["union", "future", "hope"]
}
```

---

## 🧪 Run Tests

```bash
mvn test
```

