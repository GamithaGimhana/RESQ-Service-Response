# RESQ — Response & Resource Coordination Microservice (`resq-response-service`)

## Student & Assessment Details
- **Student Name:** Gamitha
- **Student Number:** HDSE-ITS2130-ECA
- **Slack Handle:** `@gamitha`
- **GCP Project ID:** `resq-enterprise-cloud`
- **Course:** ITS 2130 — Enterprise Cloud Architecture

---

## 1. Project Description
`resq-response-service` manages specialized emergency rescue teams, member skills, real-time GPS locations, and emergency disaster resource inventories (vehicles, life boats, medical kits, generators). It provides dynamic inventory reservation/allocation and release tracking for active disaster incidents.

---

## 2. Technology Stack & Database Architecture
- **Runtime:** Java 17 / 25
- **Framework:** Spring Boot 3.3.5, Spring Data MongoDB
- **Database (Non-Relational):** MongoDB (`resq_response` database)
- **Collections:**
  - `response_teams`: Rescue units, specialized skills, GPS coordinates, personnel records
  - `resources`: Emergency logistics inventory, total vs. available counts
  - `resource_allocations`: Dynamic incident resource locks, allocations, and release audits
- **Service Discovery:** Netflix Eureka Client
- **Process Management:** PM2 on GCP Compute Engine VM

---

## 3. API Endpoints Specification
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/v1/response/teams` | List rescue teams (filter by `type`, `status`) |
| `POST` | `/api/v1/response/teams` | Register a new specialized rescue team |
| `GET` | `/api/v1/response/teams/{id}` | Get team details by ID or code |
| `PUT` | `/api/v1/response/teams/{id}` | Update team information / skills |
| `PATCH` | `/api/v1/response/teams/{id}/status` | Update team status (`AVAILABLE`, `DEPLOYED`, `STANDBY`) |
| `GET` | `/api/v1/response/resources` | List resources (filter by `category`, `status`) |
| `POST` | `/api/v1/response/resources` | Create new emergency inventory item |
| `GET` | `/api/v1/response/resources/{id}` | Get resource details |
| `PUT` | `/api/v1/response/resources/{id}` | Update inventory quantities/details |
| `POST` | `/api/v1/response/allocations` | Allocate resource quantity to an incident |
| `POST` | `/api/v1/response/allocations/{id}/release` | Release allocated resources back to inventory |
| `GET` | `/api/v1/response/allocations/incident/{incidentId}` | List allocations for an incident |
| `GET` | `/api/v1/response/statistics` | Aggregated response team and resource KPIs |

---

## 4. Setup & Getting Started

### Local Development
```bash
# Compile and run (connecting to local MongoDB or Atlas)
mvn clean spring-boot:run

# Run unit tests
mvn clean test
```

### Production Execution (PM2)
```bash
# Package
mvn clean package -DskipTests

# Launch with PM2
pm2 start target/resq-response-service-1.0.0.jar --name "resq-response-service"

# Save PM2 state
pm2 save
pm2 startup systemd
```
