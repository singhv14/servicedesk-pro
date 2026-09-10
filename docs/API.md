# ServiceDesk Pro API

## Milestone 1 — API Quality

This document records the REST API implemented and verified during Milestone 1.

## Base URL

```text
http://localhost:8080
```

---

# Ticket API

## 1. Create Ticket

Creates a new support ticket for an existing user.

### Method

```text
POST
```

### URL

```text
http://localhost:8080/api/tickets
```

### Request Body

```json
{
  "title": "Laptop issue",
  "description": "Laptop is not starting",
  "userId": 1,
  "status": "OPEN",
  "priority": "HIGH",
  "category": "HARDWARE"
}
```

### Expected Response

```text
201 Created
```

---

## 2. Get Tickets

Returns tickets with optional filtering, keyword search, pagination and sorting.

### Method

```text
GET
```

### URL

```text
http://localhost:8080/api/tickets
```

### Expected Response

```text
200 OK
```

---

# Filtering

## Filter by Status

```text
GET http://localhost:8080/api/tickets?status=OPEN
```

## Filter by Priority

```text
GET http://localhost:8080/api/tickets?priority=HIGH
```

## Filter by Category

```text
GET http://localhost:8080/api/tickets?category=HARDWARE
```

---

# Keyword Search

Searches ticket title and description.

```text
GET http://localhost:8080/api/tickets?keyword=laptop
```

The keyword search is case-insensitive.

---

# Multiple Filters

```text
GET http://localhost:8080/api/tickets?status=OPEN&priority=HIGH&category=HARDWARE&keyword=laptop
```

---

# Pagination

Pagination uses zero-based page numbering.

## Page 0

```text
GET http://localhost:8080/api/tickets?page=0&size=10
```

## Page 1

```text
GET http://localhost:8080/api/tickets?page=1&size=10
```

---

# Sorting

Sorting uses:

```text
sort=field,direction
```

## Ascending

```text
GET http://localhost:8080/api/tickets?sort=title,asc
```

## Descending

```text
GET http://localhost:8080/api/tickets?sort=title,desc
```

---

# Combined Filtering, Pagination and Sorting

```text
GET http://localhost:8080/api/tickets?status=OPEN&priority=HIGH&page=0&size=10&sort=title,asc
```

---

# Query Parameters

| Parameter | Required | Description |
|---|---|---|
| status | No | Filter by status |
| priority | No | Filter by priority |
| category | No | Filter by category |
| keyword | No | Search title and description |
| page | No | Zero-based page number |
| size | No | Number of tickets per page |
| sort | No | Sort field and direction |

---

# Validation

Required fields when creating a ticket:

- `title`
- `description`
- `userId`

---

# Error Handling

If the specified user does not exist:

```text
404 Not Found
```

Example response:

```json
{
  "status": 404,
  "message": "User not found"
}
```

---

# Development Smoke Test

The development endpoint is retained as a simple application smoke test.

```text
GET http://localhost:8080/api/hello
```

Expected response:

```text
Hello from ServiceDesk Pro
```

---

# Verification Status

Milestone 1 API functionality was verified through automated tests and manual API testing.

Verified areas:

- Ticket creation
- Ticket retrieval
- Status filtering
- Priority filtering
- Category filtering
- Keyword search
- Multiple filters
- Pagination
- Sorting
- Combined filtering, pagination and sorting
- Validation
- User-not-found error handling
- Development smoke test
