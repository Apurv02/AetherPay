<div align="center">

# ⚡ AetherPay

### Offline Payment Relay System

*Because payments shouldn't stop when the internet does.*

[![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)](https://www.java.com)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.0-brightgreen?style=for-the-badge&logo=springboot)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql)](https://www.mysql.com)
[![JWT](https://img.shields.io/badge/JWT-Auth-black?style=for-the-badge&logo=jsonwebtokens)](https://jwt.io)
[![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-85EA2D?style=for-the-badge&logo=swagger)](https://swagger.io)
[![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven)](https://maven.apache.org)
[![License](https://img.shields.io/badge/License-Educational-lightgrey?style=for-the-badge)](LICENSE)

</div>

---

## 📌 Overview

**AetherPay** is a production-inspired backend system that simulates secure financial transactions across a mesh network of devices — even without internet connectivity.

Inspired by real-world offline UPI scenarios, AetherPay relays payments hop-by-hop between devices. Once connectivity is restored, all transactions are automatically settled on the central server.

---

## 🏆 Project Highlights

| # | Feature | Description |
|---|---|---|
| 1 | 🕸️ **Offline Mesh Payment Relay** | Payments hop across devices without internet |
| 2 | 🛡️ **JWT Authentication** | Secure token-based API access |
| 3 | 🔐 **AES Encryption** | Payment data encrypted during relay |
| 4 | 🔁 **Idempotent Payment Processing** | Duplicate payments automatically rejected |
| 5 | 📜 **Transaction History** | Complete audit trail of all payments |
| 6 | 🌐 **Relay Simulation** | TTL + Retry logic for realistic simulation |
| 7 | 📖 **Swagger Documentation** | Interactive API docs out of the box |

---

## 🧠 Concepts Demonstrated

```
REST APIs             •  Layered Architecture    •  Spring Security
JWT Authentication    •  JPA / Hibernate         •  MySQL Integration
AES Encryption        •  Idempotency             •  Exception Handling
Mesh Network Simulation
```

---

## 🏗️ Architecture

```
┌─────────────────────────────────────────────┐
│                 Client Request               │
└──────────────────────┬──────────────────────┘
                       │
              ┌────────▼────────┐
              │  Controller     │  ← Receives HTTP Requests
              └────────┬────────┘
                       │
              ┌────────▼────────┐
              │    Service      │  ← Business Logic
              └────────┬────────┘
                       │
              ┌────────▼────────┐
              │   Repository    │  ← Database Operations
              └────────┬────────┘
                       │
              ┌────────▼────────┐
              │  MySQL / H2     │  ← Persistent Storage
              └─────────────────┘
```

**Mesh Relay Flow:**

```
 📱 Phone A ──relay──► 📱 Phone B ──relay──► 📱 Phone C
                                                   │
                                         Internet Available
                                                   │
                                          AetherPay Server
                                                   │
                                         Payment Settled ✅
```

---

## 🔌 API Reference

### 🔑 Authentication
| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `POST` | `/api/auth/login` | ❌ Public | Login and receive JWT token |

### 👤 Accounts
| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `POST` | `/api/accounts` | ✅ Required | Create new account |
| `GET` | `/api/accounts` | ✅ Required | Fetch all accounts |

### 💸 Payments
| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `POST` | `/api/payment/send` | ✅ Required | Send payment (idempotent) |

### 📜 Transactions
| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `GET` | `/api/transactions` | ✅ Required | Get transaction history |

### 🕸️ Mesh Relay
| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `POST` | `/api/relay/send` | ✅ Required | Relay payment via mesh |
| `GET` | `/api/relay/history` | ✅ Required | Get relay history |

---

## 🔐 Security Architecture

```
Incoming Request
      │
      ▼
JwtAuthenticationFilter
      │
      ├── Token Missing?  ──► 401 Unauthorized
      │
      ├── Token Invalid?  ──► 401 Unauthorized
      │
      └── Token Valid?    ──► Process Request ✅
```

**Two Layers of Security:**
- **JWT Authentication** — Token-based API protection
- **AES Encryption** — Payment data encrypted during relay

---

## 🔁 Idempotency Flow

```
Payment Request arrives (paymentId: "PAY-001")
              │
              ▼
   Already in processed_payments?
       │                  │
      YES                 NO
       │                  │
       ▼                  ▼
  "Duplicate         Process Payment
   Rejected!"        Save paymentId
                     Return Success ✅
```

---

## 🕸️ Mesh Relay Simulation

```
1. Payment packet created with TTL value
2. Relayed hop-by-hop between network nodes
3. Each hop decrements TTL by 1
4. TTL reaches 0 → Packet dropped
5. Internet available → Settled on AetherPay server
6. Retry logic handles failed relay attempts
```

---

## 📸 Screenshots

> **Swagger UI**
> `[ Screenshot Here ]`

> **Login API — JWT Token Generation**
> `[ Screenshot Here ]`

> **Payment API — Success Response**
> `[ Screenshot Here ]`

> **Duplicate Payment — Rejected**
> `[ Screenshot Here ]`

> **Transaction History**
> `[ Screenshot Here ]`

> **Relay Simulation**
> `[ Screenshot Here ]`

---

## 🗄️ Database Schema

```
┌─────────────────────┐     ┌──────────────────────────┐
│      accounts       │     │       transactions        │
├─────────────────────┤     ├──────────────────────────┤
│ id          (PK)    │     │ id            (PK)        │
│ name                │     │ senderId                  │
│ phoneNumber         │     │ receiverId                │
│ balance             │     │ amount                    │
└─────────────────────┘     │ paymentId                 │
                            │ status                    │
┌─────────────────────┐     │ timestamp                 │
│  processed_payments │     └──────────────────────────┘
├─────────────────────┤
│ paymentId   (PK)    │     ┌──────────────────────────┐
│ senderId            │     │      relay_history        │
│ receiverId          │     ├──────────────────────────┤
│ amount              │     │ id            (PK)        │
└─────────────────────┘     │ packetId                  │
                            │ fromNode                  │
                            │ toNode                    │
                            │ ttl                       │
                            │ timestamp                 │
                            └──────────────────────────┘
```

---

## 📁 Repository Structure

```
AetherPay/
├── src/main/java/com/offlinepayment/payment_relay/
│   ├── controller/
│   │   ├── PaymentController.java
│   │   ├── TransactionController.java
│   │   └── AuthController.java
│   ├── service/
│   │   ├── AccountService.java
│   │   ├── TransactionService.java
│   │   ├── EncryptionService.java
│   │   ├── RelayService.java
│   │   └── JwtService.java
│   ├── model/
│   │   ├── Account.java
│   │   ├── PaymentRequest.java
│   │   ├── ProcessedPayment.java
│   │   ├── Transaction.java
│   │   └── RelayPacket.java
│   ├── repository/
│   │   ├── AccountRepository.java
│   │   ├── ProcessedPaymentRepository.java
│   │   ├── TransactionRepository.java
│   │   └── RelayHistoryRepository.java
│   ├── security/
│   │   ├── JwtAuthenticationFilter.java
│   │   └── SecurityConfig.java
│   ├── exception/
│   │   └── GlobalExceptionHandler.java
│   └── PaymentRelayApplication.java
├── src/main/resources/
│   └── application.properties
├── pom.xml
└── README.md
```

---

## ⚙️ Getting Started

### Prerequisites
- Java 17+
- MySQL 8.0+
- Maven 3.x

### Run Locally

```bash
# Clone the repository
git clone https://github.com/Apurv02/AetherPay.git
cd AetherPay

# Configure MySQL credentials in application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/aetherpay
spring.datasource.username=root
spring.datasource.password=yourpassword

# Build and run
mvn spring-boot:run
```

### Access Swagger UI
```
http://localhost:8080/swagger-ui.html
```

---

## 👨‍💻 Author

**Apurv Chaturvedi**
Final Year B.Tech — AI/ML
Lakshmi Narain College of Technology and Science

[![GitHub](https://img.shields.io/badge/GitHub-Apurv02-black?style=flat-square&logo=github)](https://github.com/Apurv02)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-Connect-blue?style=flat-square&logo=linkedin)](https://www.linkedin.com/in/apurv-chaturvedi-85639a290)

---

<div align="center">

*Built with ❤️ for learning and placement preparation*

⭐ Star this repo if you found it helpful!

</div>
