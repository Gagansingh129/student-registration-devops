package com.devops;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    private App app;
    private final int testPort = 8089;

    @BeforeEach
    void setUp() throws IOException {
        app = new App();
        app.start(testPort);
    }

    @AfterEach
    void tearDown() {
        if (app != null) {
            app.stop();
        }
    }

    @Test
    @DisplayName("Verify Student Entity and Validation")
    void testStudentValidation() {
        Student validStudent = new Student("Alice Johnson", "21BCS1001", "alice@test.edu", "DevOps & Cloud Computing");
        assertTrue(validStudent.isValid(), "Valid student model should pass validation");

        Student invalidStudent = new Student("", "21BCS1002", "no-email", "");
        assertFalse(invalidStudent.isValid(), "Incomplete student should fail validation");
    }

    @Test
    @DisplayName("Verify Health Check Endpoint")
    void testHealthEndpoint() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + testPort + "/health"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), "Health endpoint should return HTTP 200");
        assertTrue(response.body().contains("UP"), "Health response should contain status UP");
    }

    @Test
    @DisplayName("Verify HTML Form Serving & Required Fields")
    void testHtmlFormContent() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + testPort + "/"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), "Home endpoint should return HTTP 200");

        String html = response.body();
        assertTrue(html.contains("Student Registration Form"), "HTML must contain page title");
        assertTrue(html.contains("id=\"studentName\""), "HTML must have studentName field");
        assertTrue(html.contains("id=\"rollNumber\""), "HTML must have rollNumber field");
        assertTrue(html.contains("id=\"email\""), "HTML must have email field");
        assertTrue(html.contains("id=\"course\""), "HTML must have course field");
        assertTrue(html.contains("type=\"submit\""), "HTML must have submit button");
    }

    @Test
    @DisplayName("Verify Local index.html File Structure")
    void testIndexHtmlFileExists() {
        Path indexPath = Paths.get("index.html");
        assertTrue(Files.exists(indexPath), "index.html must exist in project root");
    }
}
