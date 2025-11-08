# Wallet Service

Spring Boot microservice for wallet operations: **balance**, **debit**, and **credit**.

## Tech
- Spring Boot 3.3.4, Java 20
- Spring Data JPA (Hibernate 6)
- PostgreSQL
- Actuator
- Springdoc OpenAPI (Swagger UI)
- Lombok

## Configuration
Edit `src/main/resources/application.yml`:
- DB URL: `jdbc:postgresql://localhost:5432/walletmngmt`
- Schema: `walletschema`
- User/Password

## Run
```bash
mvn spring-boot:run
# or
mvn clean package && java -jar target/wallet-service-0.0.1-SNAPSHOT.jar
```

## API
- `GET /api/wallets/{walletId}/balance`
- `POST /api/wallets/{walletId}/debit`  (body: `{ "amount": 100.0, "referenceId": "ORD-1" }`)
- `POST /api/wallets/{walletId}/credit` (body: `{ "amount": 100.0, "referenceId": "REFUND-1" }`)

Swagger UI: `http://localhost:8082/swagger-ui.html`  
Health: `http://localhost:8082/actuator/health`
