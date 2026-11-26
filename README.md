# Student Registration Application - DevOps Workflow

A comprehensive demonstration of an end-to-end DevOps pipeline for a Student Registration web application, covering Version Control (Git & GitHub), Build Automation & Testing (Maven, JUnit 5), Continuous Integration (Jenkins & GitHub Actions), and Containerization (Docker).

---

## 🛠️ Tech Stack & DevOps Tools
- **Frontend:** HTML5, CSS3, JavaScript (Responsive Registration Form)
- **Backend:** Java 17, Embedded Light HTTP Server
- **Build & Dependency Management:** Apache Maven 3.10.0
- **Testing:** JUnit 5 (jupiter-api, jupiter-engine, surefire)
- **Version Control:** Git, GitHub (`https://github.com/Gagansingh129/student-registration-devops`)
- **CI/CD Automation:** Jenkins Declarative Pipeline & GitHub Actions
- **Containerization:** Docker (`Dockerfile`, Multi-stage Alpine/Slim image)

---

## 📋 Features & Fields
The registration form captures:
1. **Student Name** (`studentName`)
2. **Roll Number** (`rollNumber`)
3. **Email Address** (`email`)
4. **Course Selection** (`course`)
5. **Submit Action** (Client-side validation and backend processing)

---

## 🚀 Execution Instructions

### 1. Local Maven Build
```bash
mvn clean package
```

### 2. Run Tests
```bash
mvn test
```

### 3. Run Application Locally
```bash
java -jar target/student-registration-app-1.0.0.jar
```
Visit: `http://localhost:8080/`

### 4. Build & Run with Docker
```bash
docker build -t student-registration-app:latest .
docker run -d -p 8080:8080 --name student-registration-container student-registration-app:latest
```
Visit: `http://localhost:8080/`
