# 🎬 Movie Ticket Booking — H2 + JPA Edition

Full-stack demo app: Spring Boot + H2 (in-memory DB) + Spring Data JPA backend,
plain HTML/CSS/JS frontend, admin panel, and 10 classic design patterns.

## How to run

### 1. Start the backend
```bash
cd backend
mvn spring-boot:run
```
Runs at **http://localhost:8080**

### 2. (Optional) Inspect the DB
Open **http://localhost:8080/h2-console**

| Field      | Value                  |
|------------|------------------------|
| JDBC URL   | `jdbc:h2:mem:moviedb`  |
| User Name  | `sa`                   |
| Password   | *(empty)*              |

Tables: `MOVIES`, `SHOWS`, `BOOKINGS`, `BOOKING_SEATS`, `BOOKING_ADDONS`, `ADMIN_USERS`.

### 3. Open the frontend
Just open these files directly in your browser:
- `frontend/index.html` — customer booking flow
- `frontend/admin.html` — admin dashboard

**Admin credentials:**
- `admin` / `admin123` → full access
- `manager` / `manager123` → read-only

## Persisting data across restarts

By default H2 runs in-memory (data resets each restart). To persist data to disk,
edit `backend/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:h2:file:./data/moviedb
```

(remove the `DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE` part)

## Design patterns included

1. **Singleton** — `BookingCounter`
2. **Factory** — `TicketFactory`
3. **Strategy** — `PaymentContext` + `RoleStrategy`
4. **Decorator** — `PopcornDecorator`, `DrinkDecorator`
5. **Observer** — `BookingSubject`
6. **Builder** — `BookingBuilder`
7. **Facade** — `AnalyticsFacade`
8. **Command** — `CancelBookingCommand`
9. **State** — `SeatState` family
10. **Visitor** — `CsvExportVisitor`

## Requirements

- Java 17+
- Maven 3.6+
