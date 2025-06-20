# TransferX – Modern Banking Backend

**TransferX** is a backend banking system simulating real-world digital banking operations. Built with Java 21, Spring Boot, and secured using Spring Security and JWT, it provides a complete backend solution for managing users, accounts, virtual cards, child profiles, and money transfers.

This project focuses on backend functionality. The frontend can be integrated separately if needed for testing or demonstration purposes.

---

## Key Features

- Adult user registration and login (JWT-secured)
- Automatic account and card creation upon registration
- Parent-managed child account creation (with automatic account + card)
- Role-based access control (CUSTOMER, ADMIN)
- Secure internal transfers between accounts
- View balance and transaction history with time-based filtering
- Set and update account limits
- Activate / deactivate / delete virtual cards
- Change address and delete clients (if no transactions exist)
- Swagger API documentation enabled
- CORS configuration for frontend integration

---

## Technology Stack

| Technology        | Purpose                                  |
|-------------------|------------------------------------------|
| Java 21           | Core backend logic                       |
| Spring Boot       | REST API framework                       |
| Spring Security   | Authentication & authorization (JWT)     |
| MySQL             | Database storage                         |
| Hibernate (JPA)   | ORM for entity management                |
| Bean Validation   | Input validation                         |
| Swagger/OpenAPI   | API documentation                        |

---

## Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/TransferX.git
   ```

2. Open it as a Maven project in your IDE.

3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

4. Backend runs at:
   ```
   http://localhost:8080
   ```

---

## API Endpoints

### Authentication
| Method | Endpoint                  | Description                       |
|--------|---------------------------|-----------------------------------|
| POST   | `/api/auth/login`         | Login and receive JWT token       |
| POST   | `/api/auth/login/full`    | Login and retrieve client + account info |

### Clients
| Method | Endpoint                              | Description                                  |
|--------|---------------------------------------|----------------------------------------------|
| POST   | `/api/clients`                        | Register new adult client                    |
| POST   | `/api/clients/child`                  | Register child client (via parent)           |
| GET    | `/api/clients`                        | Get all clients (admin only)                 |
| GET    | `/api/clients/{id}`                   | Get client by ID                             |
| GET    | `/api/clients/parent/{id}/children`   | Get all children by parent ID                |
| GET    | `/api/clients/{id}/full-info`         | Full client info (accounts, cards, etc.)     |
| GET    | `/api/clients/full-info`              | Full info for all clients (admin only)       |
| PUT    | `/api/clients/address`                | Update client address                        |
| DELETE | `/api/clients/child/{id}`             | Delete child client (if no transactions)     |
| DELETE | `/api/clients/adult/{id}`             | Delete adult client (if no transactions)     |

### Accounts
| Method | Endpoint                                   | Description                               |
|--------|--------------------------------------------|-------------------------------------------|
| GET    | `/api/accounts/default/{clientId}`         | Get default account for client            |
| POST   | `/api/accounts/client/{clientId}`          | Create new account for client             |
| GET    | `/api/accounts`                            | Get all accounts (admin only)             |
| GET    | `/api/accounts/{id}`                       | Get account by ID                         |
| GET    | `/api/accounts/by-client/{clientId}`       | Get all accounts for client               |
| POST   | `/api/accounts/deposit`                    | Deposit cash via card                     |
| GET    | `/api/accounts/limits/{accountId}`         | Get account transfer limits               |
| PUT    | `/api/accounts/{accountId}/limits`         | Update account limits                     |
| DELETE | `/api/accounts/{accountId}`                | Delete account (if no transactions)       |

### Cards
| Method | Endpoint                            | Description                       |
|--------|-------------------------------------|-----------------------------------|
| POST   | `/api/cards/account/{accountId}`    | Create card for adult             |
| GET    | `/api/cards`                        | Get all cards (admin only)        |
| GET    | `/api/cards/{id}`                   | Get card by ID                    |
| GET    | `/api/cards/by-account/{accountId}` | Get card by account ID            |
| PATCH  | `/api/cards/{cardId}/activate`      | Activate card                     |
| PATCH  | `/api/cards/{cardId}/deactivate`    | Deactivate card                   |
| DELETE | `/api/cards/{id}`                   | Delete card                       |

### Transactions
| Method | Endpoint                        | Description                                  |
|--------|---------------------------------|----------------------------------------------|
| POST   | `/api/transactions/send`        | Send money between accounts                  |
| GET    | `/api/transactions/history`     | Get transaction history by IBAN and period   |

---

## User Roles

- **Adult** – full access to features, can register children, manage accounts and cards.
- **Child** – limited access, cannot register independently, max 10€ spending/day.
- **Admin** – full access to all system data and actions.

---

## Author

**Renata Vaičiūnaitė**

---

## License

This project is licensed for educational and demonstration purposes only. All rights reserved.
