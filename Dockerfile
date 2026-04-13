# Stage 1: Build the Kotlin Spring Boot application
FROM eclipse-temurin:25-jdk AS builder

WORKDIR /app

# Copy gradle wrapper and build configuration
COPY gradlew* ./
COPY gradle ./gradle
COPY build.gradle.kts ./backend/
COPY settings.gradle.kts .

# Copy source code
COPY src ./backend/src

# Build the application
# Fix CRLF line endings from Windows and make gradlew executable
RUN sed -i 's/\r$//' ./gradlew && chmod +x ./gradlew && \
    ./gradlew :backend:build -x test --no-daemon

# Stage 2: Runtime image
FROM eclipse-temurin:25-jre

WORKDIR /app

# Install curl for health checks
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Copy the built JAR from the builder stage
COPY --from=builder /app/backend/build/libs/*.jar app.jar

# Expose the port (default Spring Boot port)
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
