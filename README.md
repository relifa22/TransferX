## TransferX – Modern Banking App

**TransferX** is a full-stack banking application designed to simulate real-world banking operations. Built with **Java 21**, **Spring Boot**, and **React**, the project offers secure, structured, and scalable banking functionality tailored for both adults and children.

---

### Key Features
- User registration & login (adult & child)
- Automatic account + virtual card creation on registration
- Internal money transfers between accounts
- Account balance overview
- Parental child account creation with spending limits
- Transaction history with date filtering
- Role-based restrictions (e.g., child: 10€/day max)
- REST API endpoints are consistent, RESTful, and follow clear naming conventions. Swagger documentation planned for better developer experience.

---

### Tech Stack

| Technology       | Purpose                               |
|------------------|----------------------------------------|
| Java 21          | Core backend logic                     |
| Spring Boot      | REST API, validation, DI               |
| MySQL            | Database storage                       |
| Hibernate (JPA)  | ORM mapping for entities               |
| Bean Validation  | Input validation via annotations       |
| React + Vite     | Modern frontend UI                     |

---

### Getting Started

```bash
git clone https://github.com/relifa22/TransferX.git
```

- Import as a Maven project into your IDE  
- Run the app:

```bash
./mvnw spring-boot:run
```

Backend runs on: `http://localhost:8080`

---

### API Endpoints (examples)

| Endpoint                        | Description                           |
|----------------------------------|---------------------------------------|
| POST `/api/users`               | Register new adult user              |
| POST `/api/users/child`         | Register child account (via parent)  |
| GET `/api/users/parent/{id}/child` | Get children by parent ID         |
| POST `/api/accounts`            | Create new account                    |
| POST `/api/transactions`        | Make a transfer                       |
| POST `/api/cards`               | Issue a virtual card                  |
| PATCH `/api/cards/{id}/activate`| Activate card (planned)              |

---

### User Types

- Adult – full access to all features  
- Child / Teen – limited usage (e.g., spending limit 10€/day)

---

### Coming Soon

- JWT authentication
- Swagger UI
- Full React frontend integration
- Exportable transaction reports

---

### Created by: **Renata Vaičiūnaitė**