# ServiceDesk Pro Postman Testing

## Milestone 1 — API Quality

This document records the Postman API testing performed during Milestone 1.

The Postman requests are documented here rather than maintaining a separate exported Postman collection file.

## Base URL

```text
http://localhost:8080
```

---

# 1. Create Ticket

### Method

```text
POST
```

### URL

```text
http://localhost:8080/api/tickets
```

### Body

Select:

```text
Body → raw → JSON
```

Use:

```json
{
  "title": "Monitor not working",
  "description": "The monitor has no display",
  "userId": 1,
  "status": "OPEN",
  "priority": "HIGH",
  "category": "HARDWARE"
}
```

### Expected

```text
201 Created
```

---

# 2. Get All Tickets

```text
GET http://localhost:8080/api/tickets
```

Expected:

```text
200 OK
```

---

# 3. Filter by Status

```text
GET http://localhost:8080/api/tickets?status=OPEN
```

Expected:

```text
200 OK
```

---

# 4. Filter by Priority

```text
GET http://localhost:8080/api/tickets?priority=HIGH
```

Expected:

```text
200 OK
```

---

# 5. Filter by Category

```text
GET http://localhost:8080/api/tickets?category=HARDWARE
```

Expected:

```text
200 OK
```

---

# 6. Keyword Search

```text
GET http://localhost:8080/api/tickets?keyword=monitor
```

Expected:

```text
200 OK
```

Verify that the returned tickets contain `monitor` in the title or description.

---

# 7. Multiple Filters

```text
GET http://localhost:8080/api/tickets?status=OPEN&priority=HIGH&category=HARDWARE&keyword=monitor
```

Expected:

```text
200 OK
```

---

# 8. Pagination

```text
GET http://localhost:8080/api/tickets?page=0&size=2
```

Expected:

```text
200 OK
```

Verify pagination information such as:

- `content`
- `totalElements`
- `totalPages`
- `size`
- `number`

---

# 9. Sorting Ascending

```text
GET http://localhost:8080/api/tickets?sort=title,asc
```

Expected:

```text
200 OK
```

Verify that tickets are sorted by title ascending.

---

# 10. Sorting Descending

```text
GET http://localhost:8080/api/tickets?sort=title,desc
```

Expected:

```text
200 OK
```

Verify that tickets are sorted by title descending.

---

# 11. Combined Pagination + Sorting + Filtering

```text
GET http://localhost:8080/api/tickets?status=OPEN&priority=HIGH&page=0&size=2&sort=title,asc
```

Expected:

```text
200 OK
```

---

# 12. Development Smoke Test

```text
GET http://localhost:8080/api/hello
```

Expected:

```text
Hello from ServiceDesk Pro
```

---

# 13. User Not Found Error

Use a user ID that does not exist.

### Method

```text
POST
```

### URL

```text
http://localhost:8080/api/tickets
```

### Body

```json
{
  "title": "Test invalid user",
  "description": "Testing user not found handling",
  "userId": 999999,
  "status": "OPEN",
  "priority": "HIGH",
  "category": "HARDWARE"
}
```

Expected:

```text
404 Not Found
```

Expected response:

```json
{
  "status": 404,
  "message": "User not found"
}
```

---

# Postman Verification Checklist

- [ ] Create Ticket
- [ ] Get All Tickets
- [ ] Filter by Status
- [ ] Filter by Priority
- [ ] Filter by Category
- [ ] Keyword Search
- [ ] Multiple Filters
- [ ] Pagination
- [ ] Sorting Ascending
- [ ] Sorting Descending
- [ ] Combined Filtering + Pagination + Sorting
- [ ] `/api/hello`
- [ ] User Not Found error

---

# Milestone 1 Result

Postman testing is part of the Milestone 1 API Quality verification.

After all requests have been tested successfully, record the final result here:

```text
Status: PASS
```

```text
Automated Tests: PASS
Postman Testing: PASS
API Documentation: Complete
Milestone: 1 — API Quality
```
