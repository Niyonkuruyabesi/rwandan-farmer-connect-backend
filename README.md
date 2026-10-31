# Rwandan Farmer Connect: Backend

**Student:** Niyonkuru Nyirimpeta Yabesi
**ID:** 27715
**Project:** Rwandan Farmer Connect
**Course:** Web Technology and Internet

## About the project 

Rwandan Farmer Connect is a backend API that links smallholder farmers with the agronomist who advise them. A farmer requests a farm visit, handled by an agronomist posted to one sector and trained in several crops. The visit moves from `REQUESTED` to `ACCEPTED`, and ends `VISITED` or `DECLINED`. After a visit is `VISITED`, the agronomist records the recommendations given to the farmer.

## Technologies

- Java and Spring Boot (Spring Web, Spring Data JPA)
- PostgreSQL
- Maven
- Postman(API testing)

## Entities

| Entity | Table | Role |
|---|---|---|
|Farmer| `farmer` | The farmer the work is done for (business key: `farmerCode`) |
|Agronomist | `agronomist` | Advises farmers; has one sector and many crops |
|Crop | `crop` | A crop an agronomist can be trained in |
|Sector | `sector` | Where an aggronomistis assigned |
|FarmVisit | `farm_visit` | The dated transaction with a `VisitStatus` |
|Recommendation | `recommendation` | Written only after a farm visit is `VISITED` |

## How to run the API

### 1. Requirements

- JDK (the version set in `pom.xml`)
- Maven 
- PostgreSQL
- Postman

### 2. Get the code

```bash
git clone https://github.com/Niyonkuruyabesi/rwandan-farmer-connect-backend.git
cd rwandan-farmer-connnect-backend
```

### 3. Create the database

```sql
CREATE DATABASE farmerconnect_db;
```

### 4. Configure the connection

Open 'src/main/resources/application.properties`and set your own PostgreSQL port, username and password:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5433/farmerconnect_db
spring.datasource.username=postgres
spring.datasource.password=YOUR_PASSWORD
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

The tables are created automatically on the first start (`ddl-auto=update`).

### 5. Start the application

```bash
mvn spring-boot:run
```
The API runs at `http://localhost:8080`.

### 6. Test with Postman

Import `rwandan-farmer-connect-midterm.postman_collection.json`(in the root of this repository). It has one request per requirement, named by its ID(A2, B1, C4, D2, ...).

Enter the seed data(crops, sectors, agronomists, farmers, farm visits) through the `save` endpoints before testing the queries.

## Endpoints

| ID | Method | endpoint |
|---|---|---|
| A2 | POST / GET / PUT / DELETE | `/api/{path}/save`, `/all`, `/{id}`, `/update/{id}`, `/delete/{id}` where `{path}` is `crops`, `sectors`, `agronomists`, `farmers` or `farm-visits` |
| B1 | GET | `/api/farmers/by-last-name?lastName=niyonzima` |
| B2 | GET | `/api/farm-visits/by-status?status=REQUESTED` |
| B3 | GET | `/api/farm-visits/between?start=2026-10-01&end=2026-10-31` |
| C1 | GET | `/api/agronomists/by-crop?name=maize` |
| C2 | GET | `/api/sectors/free` |
| C3 | GET | `/api/crops/unused` |
| C4 | GET | `/farmers/of-agronomist/{agronomistId}` |
| D1 | POST | `/api/farm-visits/save` (safe saving rules) |
| D2 | PATCH | `/api/farm-visits/{id}/status?status=ACCEPTED` |
| D3 | PUT | `/api/agronomists/{agronomistId}/crops/{cropId}` |
| D4 | PATCH | `/api/agronomists/{agronomistId}/sector?sectorNumber=4` |
| E1 | POST | `/api/recommendations/save?farmVisitId=...` |
| E2 | GET | `/api/recommendations/farmer/{farmerCode}` |
| E3 | DELETE | `/api/recommendations/delete/{id}` |
| F1 | GET | `/api/farm-visits/stats/by-status ` |
| F2 | PATCH | `/api/farm-visits/cancel-day?agronomistId=...&date=2026-10-20` |
| Bonus | GET | `/api/dashboard/summary` |
| Bonus | GET | `/api/farm-visits/page?page=0&size=5&sort=visitDate,desc` |

## Project structure

```
src/main/java/rw/auca/rwandan_farmer_connect/
|- domain/       entities and the VisitStatus enum
|- repository/   Spring Data JPA repositories (derived queries and JPQL)
|- service/      business rules, return a list, an Optional or a String message
|- controller/   REST endpoints, map the message to an HTTP status
```


