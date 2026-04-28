# Multi-stage Dockerfile for building and running the Spring Boot backend
# Build stage: uses Maven to build the project
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /workspace

# Copy only what is needed first to leverage Docker layer caching
COPY pom.xml mvnw .
COPY .mvn .mvn
RUN mkdir -p src && echo "" > src/.placeholder

# Copy the rest of the source
COPY ./ .

# Build the project (skip tests for faster build; remove -DskipTests for CI)
RUN ./mvnw -B -DskipTests package || mvn -B -DskipTests package

# Run stage: smaller runtime image
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy jar built by Maven (artifact name may vary; using wildcard)
COPY --from=build /workspace/target/*.jar app.jar

# Use environment PORT provided by Render; default mapping handled by application.properties
ENV JAVA_OPTS=""
EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar --server.port=${PORT:-8080}"]
FROM eclipse-temurin:21-jdk
WORKDIR /app

COPY . .
RUN chmod +x mvnw && ./mvnw clean package -DskipTests

EXPOSE 10000
CMD ["sh", "-c", "java -jar target/student-system-0.0.1-SNAPSHOT.jar --server.port=${PORT}"]
