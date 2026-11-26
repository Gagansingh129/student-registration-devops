# Multi-stage or direct runtime image using official Temurin Alpine
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the built jar artifact and index.html
COPY target/student-registration-app-1.0.0.jar app.jar
COPY index.html index.html

# Expose HTTP port
EXPOSE 8080

# Environment variables
ENV PORT=8080

# Run the Student Registration Application
ENTRYPOINT ["java", "-jar", "app.jar", "8080"]
