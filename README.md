# Software Design - Assignment 3 ![Deadline 05.05.2022 17:00](https://img.shields.io/badge/deadline-05.05.2022%2017%3A00-blue.svg "Deadline: 05.05.2022 17:00")
This is the third and last assignment of the software design laboratory.

## Resources
The feature descriptions may be found in [this presentation](https://docs.google.com/document/d/1tVYAyPtw45p4jIwGaeIKsNPpqTvS5VukwBIKkj6pv8A/edit?usp=sharing), whilst the theoretical background may be found in [this presentation](https://docs.google.com/presentation/d/1c7GF7YhRlcTGC6juUaPZcXtdvoRNjR4a9E5nBhWFVX8/edit?usp=sharing).

## Design Constraints
 * Client - Server architecture,
 * Exposing a REST API from the server,
 * And consuming it on the client,
 * Using the Observer design pattern and the Command one,
 * Protecting the API using HTTP Basic Authentication.

## Recommended Technologies
 * Everything from Assignment 1,
 * Everything from Assignment 2,
 * Postman.

## Getting started
 * You will work on both the assignment 1 and assignment 2 code bases,
 * Backend: use [@RestController](https://spring.io/guides/gs/rest-service-cors/#_create_a_resource_controller)s,
 * Backend: [enable CORS for all paths on the server](https://spring.io/guides/gs/rest-service-cors/#_global_cors_configuration),
 * Frontend: use the [fetch API](https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch) to communicate with the server.

## Grading
Minimum requirements for a passing grade:
 * REST API on the server,
 * Reading data from the server on the client,
 * Use HTTP Basic for securing the server,
 * Feature 1,
 * Observer design pattern,
 * Command design pattern (either on client or on server side).

Additional requirements:

| Requirement                        | Grade |
|------------------------------------|-------|
| Feature 2                          |   6   |
| Feature 3                          |   7   |
| Postman Tests                      |   8   |
| WebSockets*                        |   10  |

**Use WebSockets to immediately show updates on the UI (new questions / answers added, answers edited / removed, scores updated, etc).*

Bonus requirements:

| Requirement                                                | Points |
|------------------------------------------------------------|--------|
| Prepare and present API interface specifications - [Swagger](https://www.baeldung.com/spring-rest-openapi-documentation) | 1.0    |
| Support undo - redo functionality                          | 1.0    |
| End to end tests using Cypress                             | 1.5    |
| Secure the application using OAuth 2.0*                    | 2.0    |

**Enable a local authorization and resource server on the backend and use the password grant from the frontend to obtain an OAuth token.*
