FROM maven:3.9-eclipse-temurin-17 AS service1
WORKDIR /tmp
COPY ../service1/src ./service1/src
COPY ../service1/pom.xml ./service1
COPY ../pom.xml .
WORKDIR service1
RUN mvn clean package

FROM tomcat:10.1-jdk17-temurin
COPY --from=service1 /tmp/service1/target/service1.war $CATALINA_HOME/webapps
EXPOSE 8080
CMD catalina.sh run
