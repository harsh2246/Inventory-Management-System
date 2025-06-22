 E-Commerce Inventory Management System

A robust and scalable Inventory Management System built with Java sprinboot redis. 
This system allows for efficient supply management, quantity reservation, cancellation, and real-time availability checks with caching optimization.

---

## 📌 Project Objective

To develop a production-ready, optimized inventory service as a part of a larger e-commerce system, utilizing:
- Relational Database (MySQL) for persistence
- Redis for high-performance caching
- Spring Boot for RESTful API development

---

## ✅ Features

- Create item supply with initial quantity.
- Reserve item quantity based on customer demand.
- Cancel item reservations.
- Check current item availability.
- Redis caching for item availability.
- Concurrency-safe operations.

---

## 🛠️ Tech Stack

| Layer          | Technology                          |
|----------------|--------------------------------------|
| Backend        | Java, Spring Boot, Spring MVC        |
| Database       | MySQL / PostgreSQL                   |
| Caching        | Redis (via Redisson)                 |
| ORM            | Spring Data JPA                      |
| Build Tool     | Maven                                |
| Testing        | JUnit, MockMvc                       |
| API Testing    | Postman                              |
| Version Control| Git + GitHub                         |

---

## 🧱 API Endpoints

| Method | Endpoint                        | Description                     |
|--------|----------------------------------|---------------------------------|
| POST   | `/create`                        | Create a new item supply        |
| POST   | `/reserve/{itemId}/{quantity}`   | Reserve item quantity           |
| POST   | `/cancel/{itemId}/{quantity}`    | Cancel reserved quantity        |
| GET    | `/available/{itemId}`            | Get item availability (cached)  |

---

## 🧪 Testing

- Unit tests written using **JUnit 5**
- Controller tests with **MockMvc**
- Run with:
  ```bash
  mvn test

## also Used Swagger to show docuementation of API
