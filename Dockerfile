# Stage 1: Runtime
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built jar and index.html
COPY target/student-registration-app-1.0.0.jar app.jar
COPY index.html index.html

# Expose server port
EXPOSE 8080

# Environment variables
ENV PORT=8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar", "8080"]
