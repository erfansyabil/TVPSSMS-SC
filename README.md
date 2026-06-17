# TVPSSMS — TV Production Studio System Management System

A Spring Boot web application for managing a TV production studio, with role-based access for Admins, Teachers, and Students.

## Tech Stack

- **Java 21**
- **Spring Boot 3.2.5** (Web, Security, Data JPA, Thymeleaf)
- **MySQL** (database)
- **Maven** (build tool)

---

## Prerequisites

Make sure the following are installed on your machine before proceeding:

| Tool | Minimum Version | Download |
|------|----------------|---------|
| JDK | 21 | https://adoptium.net |
| Maven | 3.8+ | https://maven.apache.org/download.cgi |
| MySQL | 8.0+ | https://dev.mysql.com/downloads/mysql |

---

## Setup

### 1. Clone the repository

```bash
git clone https://github.com/nenowoDev/TVPSSMS.git
cd TVPSSMS
```

### 2. Create the MySQL database

Open your MySQL client (MySQL Workbench, DBeaver, or terminal) and run:

```sql
CREATE DATABASE tvpssms;
```

Then create a MySQL user that matches the application config, or use your existing root credentials:

```sql
CREATE USER 'john_doe'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON tvpssms.* TO 'john_doe'@'localhost';
FLUSH PRIVILEGES;
```

> **Note:** Hibernate is configured with `ddl-auto=update`, so all tables are created automatically on first run.

### 3. Configure the database connection

Open `src/main/resources/application.properties` and update the credentials if needed:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/tvpssms
spring.datasource.username=john_doe
spring.datasource.password=password
```

### 4. Seed an initial Admin user

Because all endpoints require authentication, you need at least one user in the database before logging in. After the app has run once (to auto-create the tables), insert an admin user via SQL:

```sql
USE tvpssms;

-- BCrypt hash for the password "admin123"
INSERT INTO users (username, password, enabled)
VALUES ('admin', '$2a$10$7EqJtq98hPqEX7fNZaFWoOa.smo3S7PqoKm3BhVGETRWzFkMOBPtS', 1);

INSERT INTO authorities (username, authority)
VALUES ('admin', 'ROLE_ADMIN');
```

> The hash above corresponds to the password `admin123`. To use a different password, generate a new BCrypt hash using an online tool or your own Java snippet.

---

## Running the Application

### Option A — Maven CLI

```bash
mvn spring-boot:run
```

### Option B — Build a JAR and run it

```bash
mvn clean package -DskipTests
java -jar target/SpringLabApp3-0.0.1-SNAPSHOT.jar
```

The application starts on **http://localhost:8080**.

---

## Accessing the App

Navigate to **http://localhost:8080/users/loginrole** to reach the login page.

### Role-based redirects after login

| Role | Redirected to |
|------|--------------|
| ADMIN | `/users/admin` |
| TEACHER | `/users/teacher` |
| STUDENT | `/users/student` |

New user accounts can be registered at `/register`.

---

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   ├── com/example/
│   │   │   ├── controller/   # MVC controllers (Content, Crew, Program, Report, Task, User)
│   │   │   ├── entity/       # JPA entities (User, Authority, Content, Crew, Program, Report, Task)
│   │   │   └── service/      # DAO/service layer
│   │   └── config/           # Spring Security configuration
│   └── resources/
│       ├── templates/        # Thymeleaf HTML templates
│       └── application.properties
└── test/
```

---

## Common Issues

**Port already in use**
Change the server port in `application.properties`:
```properties
server.port=8081
```

**MySQL connection refused**
Make sure your MySQL service is running:
```bash
# Windows
net start MySQL80

# macOS / Linux
sudo systemctl start mysql
```

**Tables not created / schema errors**
Ensure `spring.jpa.hibernate.ddl-auto=update` is set in `application.properties` and the `tvpssms` database exists before starting the app.
