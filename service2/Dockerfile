FROM maven:3.9-eclipse-temurin-17 AS service2
WORKDIR /tmp
COPY ../service2/src ./service2/src
COPY ../service2/pom.xml ./service2
COPY ../pom.xml .
WORKDIR service2
RUN mvn clean package

FROM tomcat:10.1-jdk17-temurin
COPY --from=service2 /tmp/service2/target/service2.war $CATALINA_HOME/webapps
EXPOSE 8081
CMD catalina.sh run
