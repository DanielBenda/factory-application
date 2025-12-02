# Factory — Digital Manufacturing Simulation Platform

Factory is a demonstration and educational application simulating the **digitalization of a small manufacturing company**.  
The project aims to showcase modern approaches to enterprise application design:

- domain-driven design (DDD),
- separated modules for persistence, domain logic, GraphQL API, and application runtime,
- modern security using Spring Security,
- both REST and GraphQL interfaces,
- and a modern Java 21 + Spring Boot 3.5.x stack.

The application serves as a foundation for building manufacturing workflows, managing production orders, planning, material management, and other digitalization processes.

---

## 🚀 Features (Current State)

- **GraphQL API** for domain operations (production orders, operations, BOMs, etc.)
- **REST API** for system communication
- **Modular architecture** (multi-module Maven, DDD)
- **Basic authentication** via HTTP Basic
- **Security profiles** (ADMIN + planned USER)
- **Updated and secured dependencies** (Netty, Nimbus JWT, Logback, Tomcat, Spring Core)

---

## 🧱 Project Architecture

The project is split into four Maven modules:

factory <br>
├── persistence – JPA / persistence layer <br>
├── domain – pure domain logic (DDD) <br>
├── graphql – GraphQL resolvers + schema definitions <br>
└── application – bootable Spring Boot application (REST, Security)


Each module is clearly separated with well-defined responsibilities and boundaries.

---

## 🛠 Technologies Used

### ▶ Backend & Frameworks
- **Java 21**
- **Spring Boot 3.5.5**
- **Spring Web** (REST API)
- **Spring Security**
- **Spring GraphQL**
- **Spring Context / Core**

### ▶ Build & Modularization
- **Maven (multi-module)**
- **dependencyManagement** (central BOM)
- **Spring Boot Maven Plugin**

### ▶ Logging
- Logback (updated to 1.5.20 due to CVE fixes)

### ▶ Networking Libraries
- Netty 4.1.125.Final (CVE fixes)
- Apache Tomcat Embed 10.1.45 (CVE fixes)

### ▶ Cryptography & Tokens
- Nimbus JOSE + JWT 9.37.4 (CVE fix)

### ▶ Utility Libraries
- Apache Commons Lang3 3.18.0

---

## 📦 How to Build the Project

Requirements:

- Java 21+
- Maven 3.9+

### Build the entire project:

``` bash
mvn clean install

Build & run the application:

From the application/ module, run:

mvn spring-boot:run