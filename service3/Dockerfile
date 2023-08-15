FROM maven:3.9-eclipse-temurin-17 AS service3
WORKDIR /tmp
COPY ../service3/src ./service3/src
COPY ../service3/pom.xml ./service3
COPY ../pom.xml .
WORKDIR ./service3
RUN mvn clean package

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=service3 /tmp/service3/target/service3-jar-with-dependencies.jar .
CMD java -jar service3-jar-with-dependencies.jar
