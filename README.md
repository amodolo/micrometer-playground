# micrometer-playground
Playground project to dirt the hands with micrometer tracing and observations

## Start infrastructure components
```bash
docker-compose up -d
```

## Start infrastructure components and services
```bash
docker-compose --profile full up -d
```

## Stop dependencies and services
```bash
docker-compose --profile full down
```

## Useful URLs
- Service: http://localhost:8080/service1/api/resource1 (CRUD service)
- Kafka boostrap server: `localhost.29092`
- Postgres connection url: `jdbc:postgresql://localhost:5432/postgres` (postgres/password)
