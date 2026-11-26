pipeline {
    agent any

    tools {
        maven 'Maven-3.10.0'
        jdk 'JDK-17'
    }

    stages {
        stage('Checkout Source') {
            steps {
                echo 'Checking out source code from Git repository...'
                checkout scm
            }
        }

        stage('Maven Clean & Compile') {
            steps {
                echo 'Compiling Java sources with Maven...'
                bat 'mvn clean compile'
            }
        }

        stage('Execute Unit Tests') {
            steps {
                echo 'Running JUnit 5 test suite...'
                bat 'mvn test'
            }
        }

        stage('Package Application JAR') {
            steps {
                echo 'Packaging application into executable JAR...'
                bat 'mvn package -DskipTests=true'
            }
        }

        stage('Archive Artifacts') {
            steps {
                echo 'Archiving build artifact...'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Docker Container Build') {
            steps {
                echo 'Building Docker container image...'
                bat 'docker build -t student-registration-app:latest .'
            }
        }
    }

    post {
        always {
            echo '========================================='
            echo 'Jenkins Pipeline Execution Completed.'
            echo '========================================='
        }
        success {
            echo 'SUCCESS: All pipeline stages passed successfully!'
        }
        failure {
            echo 'FAILURE: One or more pipeline stages failed.'
        }
    }
}
