FROM maven:3.9.1 AS builder

WORKDIR /app
COPY api/pom.xml .
RUN mvn dependency:go-offline

COPY api/src ./src
RUN mvn package -DskipTests

FROM openjdk:20-slim
WORKDIR /app
COPY --from=builder /app/target/api-0.0.1-SNAPSHOT.jar /app

CMD ["java", "-jar", "api-0.0.1-SNAPSHOT.jar"]
