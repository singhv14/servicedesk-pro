# ServiceDesk Pro

Production-style **IT Support & Ticket Management Platform** built as a practical Java/Spring learning project.

The goal is not to make a simple CRUD demo. The project will be developed like a small software-company backend: work is broken into tickets, each ticket has acceptance criteria, prerequisite learning, testing, Git branches, pull requests, documentation, and a clear definition of done.

---

## 1. Project Objective

Build a realistic service-desk platform where employees can raise support tickets and support engineers can manage, assign, update, resolve, and report on those tickets.

The backend is the primary learning project. A React frontend will be added after the backend is stable. AI capabilities are intentionally planned as a later extension rather than being forced into the core application.

### Core workflow

```text
Employee
   |
   v
Create Ticket
   |
   v
Ticket Validation
   |
   v
Assignment
   |
   v
Engineer Updates / Comments
   |
   v
Resolution
   |
   v
Closure + Audit History
```

---

## 2. Product Features

### Ticket Management
- Create, view, update and close tickets
- Ticket title, description, category, priority and status
- Search and filtering
- Sorting and pagination
- Assignment to support engineers
- Ticket comments and history

### Users and Roles
- Employee
- Support Engineer
- Manager
- Admin

### Security
- Registration and password hashing
- Authentication with Spring Security
- JWT-based access tokens
- Refresh-token flow
- Role-based authorization
- Optional OAuth2 integration if it adds real value

### Operations and Engineering
- Transaction-safe multi-step workflows
- Caching for frequently-read reference data
- AOP for cross-cutting concerns such as timing/auditing
- Spring Boot Actuator for health and metrics
- Environment-specific profiles
- Spring Batch escalation/reporting job
- Automated tests
- Docker / Docker Compose
- AWS deployment
- CloudWatch logging/monitoring

### Frontend
React will consume the existing REST APIs for:
- Login/register
- Ticket list
- Ticket creation
- Ticket details
- Search/filter/sort/pagination
- Role-aware dashboards

### Future AI Extensions
- AI-suggested ticket category and priority
- Ticket summarization
- Support-reply assistant
- Knowledge-base search / RAG-style assistance

AI output will remain advisory. Server-side validation and authorization remain the source of truth.

---

## 3. Technology Stack

### Backend
- Java 21
- Spring Boot 4.1.1 baseline
- Spring MVC
- Spring REST
- Spring Data JPA
- Hibernate
- Spring Validation
- Spring Security
- JWT
- OAuth2 (optional / decision-based)
- Spring Actuator
- Spring AOP
- Spring Cache
- Spring Transactions
- Spring Batch

### Database
- PostgreSQL

### API Testing / Quality
- Postman
- JUnit
- Mockito
- Spring Boot Test / MockMvc

### DevOps / Deployment
- Git
- GitHub
- GitHub Pull Requests
- Docker
- Docker Compose
- AWS
- CloudWatch

### Frontend
- React
- REST API integration

---

## 4. Architecture Direction

The application starts with a clean layered backend and evolves only when the feature needs it.

```text
Client / Postman / React
          |
          v
   REST Controllers
          |
          v
      Services
          |
          v
    Repositories
          |
          v
 PostgreSQL / Hibernate
```

Cross-cutting concerns will be added around the core flow:

```text
Security → Authentication / Authorization
AOP      → Logging / Timing / Auditing
Cache    → Frequently-read data
TX       → Atomic business workflows
Actuator → Health / Metrics
Batch    → Scheduled escalation / reporting
```

Final production-style architecture and system-design decisions will be documented in `docs/architecture/` as the project evolves.

---

## 5. Domain Model Direction

The first version will start small and grow naturally.

```text
User
 |
 +---- raises ----> Ticket
                       |
                       +---- Category
                       +---- Priority
                       +---- Status
                       +---- Assigned Engineer
                       +---- Comments
                       +---- Audit History
```

Likely entities as the project matures:

- User
- Ticket
- Category
- TicketComment
- TicketAssignment
- AuditLog
- RefreshToken
- KnowledgeArticle (future)

The exact model is allowed to change as requirements and design decisions become clearer.

---

## 6. Development Method — Company-Like Ticket Workflow

Every meaningful change is treated as a development ticket.

For each ticket:

1. Read the objective and acceptance criteria.
2. Identify what must be learned first.
3. Learn the prerequisite concepts.
4. Implement the ticket.
5. Test with Postman and/or automated tests.
6. Review `git status` and `git diff`.
7. Commit with the ticket ID.
8. Push a feature branch.
9. Open a GitHub Pull Request.
10. Review and merge to `main`.
11. Pull the updated `main` branch.
12. Update the tracker and documentation.

### Git flow

```text
git checkout main
git pull origin main

git checkout -b feature/DEV-XXX-short-name

# learn + implement + test

git status
git diff

git add .
git commit -m "DEV-XXX: meaningful message"
git push -u origin feature/DEV-XXX-short-name

# Open Pull Request on GitHub
# Review -> Merge -> main

git checkout main
git pull origin main
```

### Branch rule

**Never develop directly on `main`.**

Initially the project uses:

```text
main
  |
  +-- feature/DEV-XXX-...
```

A `develop` / staging branch can be introduced later when deployment environments make it useful.

---

## 7. Definition of Done

A ticket is **Done** only when:

- Acceptance criteria are met.
- Code runs locally.
- Relevant Postman/API tests pass.
- Automated tests are added where appropriate.
- No accidental files/secrets are committed.
- Feature branch is pushed.
- Pull Request has been reviewed.
- PR is merged into `main`.
- Daily tracker entry is updated.
- README/docs are updated when the change affects project behavior or setup.

---

## 8. Learning Rule

Technologies that are currently unfamiliar will not be implemented by blind copying.

When a ticket introduces a new concept:

```text
Ticket
  |
  v
What do I need to learn?
  |
  v
Concept study
  |
  v
Small explanation/example
  |
  v
Project implementation
  |
  v
Testing + review
```

This is especially important for:

- Authentication
- Authorization
- Spring Security
- JWT
- OAuth2
- Transactions
- Caching
- AOP
- Spring Batch
- Docker
- AWS

---

## 9. Milestones

### Milestone 1 — Core Backend (Target: Sep 10 morning)

- Spring Boot initialization
- Spring MVC / REST
- PostgreSQL
- JPA / Hibernate
- Ticket/User model
- CRUD APIs
- Validation
- Global exception handling
- Pagination
- Sorting
- Search/filter
- Postman collection
- Basic documentation

### Milestone 2 — Security (Target: Sep 17)

- Authentication fundamentals
- Spring Security
- Registration
- Password hashing
- Login
- JWT access token
- JWT request filter
- Refresh token flow
- Roles and authorization

### Milestone 3 — Production-Style Backend (Target: Sep 24)

- Transactions
- Caching
- AOP
- Actuator
- Profiles
- Spring Batch
- Reporting/custom queries

### Milestone 4 — Deployment & Operations (Target: Sep 30)

- Unit/integration tests
- Docker
- Docker Compose
- AWS deployment
- CloudWatch
- Secret/config hardening
- Backend documentation

### Milestone 5 — React Frontend (October)

- React initialization
- Authentication UI
- Ticket screens
- Search/filter/sort/pagination UI
- Role-aware dashboard
- Frontend deployment

### Milestone 6 — AI Extensions (After core application)

- AI learning/integration
- Ticket classification
- Ticket summaries
- Reply assistant
- Knowledge-base search

### Milestone 7 — Engineering Maturity

- System design documentation
- Database indexing/performance review
- Reliability/idempotency review
- CI with GitHub Actions
- Final QA
- Resume/demo preparation

---

## 10. Project Ticket Backlog

The complete ticket plan is maintained in the separate **ServiceDesk Pro — Project Tracker.xlsx** file.

Ticket ranges:

```text
DEV-001 to DEV-013  → Foundation, REST, JPA, validation, queries
DEV-014 to DEV-022  → Authentication, Security, JWT, authorization
DEV-023 to DEV-030  → Transactions, caching, AOP, Actuator, profiles, Batch, reporting
DEV-031 to DEV-039  → Testing, Docker, AWS, CloudWatch, documentation
DEV-040 to DEV-045  → React frontend
DEV-046 to DEV-050  → AI features
DEV-051 to DEV-059  → System design, performance, reliability, OAuth2 decision, CI/CD, QA, portfolio
```

The tracker is the source of truth for ticket status, planned hours, target dates, branch names and daily work entries.

---

## 11. Documentation Structure

As the repository grows, use:

```text
servicedesk-pro/
|
+-- src/
+-- docs/
|   +-- architecture/
|   +-- api/
|   +-- decisions/
|   +-- troubleshooting/
|
+-- README.md
+-- pom.xml
+-- .gitignore
```

### Documentation rules

Update documentation during development, not only at the end.

Record:
- What was learned
- Why a design was chosen
- Important API behavior
- Troubleshooting / production-like issues
- Setup/configuration changes
- Architecture decisions

---

## 12. Secrets and Configuration

Never commit:

- Database passwords
- JWT signing secrets
- OAuth client secrets
- API keys
- AWS credentials

Use environment variables and Spring profiles.

Local development should use safe local configuration. Production configuration must be supplied through the deployment environment.

---

## 13. Current Project State

As of project initialization:

- GitHub repository: `servicedesk-pro`
- Repository visibility: Public
- PostgreSQL selected
- Java 21 selected
- Maven selected
- Jar packaging selected
- YAML configuration selected
- Initial dependencies selected:
  - Spring Web
  - Spring Boot DevTools
  - Spring Data JPA
  - PostgreSQL Driver
  - Validation
  - Lombok
- ChatGPT Project: `ServiceDesk Pro`
- Project-only memory: enabled
- Separate tracker: maintained outside the codebase until the project documentation is committed

### Immediate next step

Complete **DEV-001** locally, then complete **DEV-002 GitHub workflow** and verify the application starts successfully.

The next technical work begins with REST/MVC and then moves into PostgreSQL + JPA/Hibernate.

---

## 14. Portfolio Goal

The final project should be something that can be discussed honestly in a Java/Spring Boot interview:

> Designed and developed a production-style IT service desk backend using Spring Boot, Spring MVC/REST, Spring Data JPA, Hibernate and PostgreSQL; implemented validation, exception handling, pagination/search, authentication and JWT-based authorization, transactions, caching, AOP, batch processing, testing, Docker and AWS deployment, followed by a React frontend and optional AI-assisted support features.

The resume statement should be updated at the end to reflect only the technologies and features actually completed and understood.

---

## 15. Working Principle

**Build it. Break it. Debug it. Test it. Document it. Push it. Review it. Learn from it.**

The objective is not just to finish ServiceDesk Pro. The objective is to finish it with a GitHub history and technical understanding that demonstrate consistent software-engineering practice.
