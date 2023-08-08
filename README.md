# Spring base project

This is just some basic Spring Boot project with some DB models and controller. Used as base to test libraries and starters.

## Set up
1. **Database**: There is a `docker-compose.yml` in the root of the project with a Postgres DB service. Run it with `docker compose up` to start the DB container.
2. **Spring boot**: Just run the project by running the complied JAR or inside some IDE. There is a script in `bin/run.sh` to run the compiled jar (you need to prevously build the project with `mvn clean package`).


## Branches index
- **feature/db-audit**: Audited DB access with Spring facilities and Basic Auth with Spring Security.
- **feature/hibernate-envers**: Entity versioning with Hibernate Envers + API access to Persons entity revisions.
- **feature/validation**: Validation of entities and params with Hibernate Validator and Spring integration.
- **feature/integration-tests**: Integration tests with Spring Boot and Docker containers.
  - MvcTest: Testing controllers with Spring MVC Test.
  - JpaTest: Testing JPA repositories with Spring Data JPA Test.
  - SpringBootTest: Testing the whole application with Spring Boot Test.
- **feature/request-context**: Request context with Spring mechanisms.
  - Static ThreadLocal: Using a static ThreadLocal to store the request context.
  - Spring Bean: Using a Spring Bean to store the request context.
- **feature/micrometer**: Register and expose custom micrometer metrics.
- **feature/entity-graph**: Add entity graphs to optimize JPA queries.