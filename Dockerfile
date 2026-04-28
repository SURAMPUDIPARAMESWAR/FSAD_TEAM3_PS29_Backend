FROM eclipse-temurin:21-jdk
WORKDIR /app

COPY . .
RUN chmod +x mvnw && ./mvnw clean package -DskipTests

EXPOSE 10000
CMD ["sh", "-c", "java -jar target/student-system-0.0.1-SNAPSHOT.jar --server.port=${PORT}"]
