package com.devops;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Executors;

/**
 * Embedded HTTP Server serving the Student Registration Application
 */
public class App {
    public static final int DEFAULT_PORT = 8080;
    private HttpServer server;

    public static void main(String[] args) {
        int port = DEFAULT_PORT;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException ignored) {}
        }

        App app = new App();
        try {
            app.start(port);
            System.out.println("=================================================");
            System.out.println("Student Registration Server started successfully!");
            System.out.println("Access URL: http://localhost:" + port + "/");
            System.out.println("Health Check: http://localhost:" + port + "/health");
            System.out.println("=================================================");
        } catch (IOException e) {
            System.err.println("Failed to start server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void start(int port) throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new StaticFileHandler());
        server.createContext("/register", new RegistrationHandler());
        server.createContext("/health", new HealthHandler());
        server.setExecutor(Executors.newFixedThreadPool(10));
        server.start();
    }

    public void stop() {
        if (server != null) {
            server.stop(0);
        }
    }

    public int getPort() {
        return server != null ? server.getAddress().getPort() : -1;
    }

    static class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            byte[] responseBytes;
            String contentType = "text/html; charset=UTF-8";

            // Try reading index.html from working directory or resources
            Path rootFile = Paths.get("index.html");
            if (Files.exists(rootFile)) {
                responseBytes = Files.readAllBytes(rootFile);
            } else {
                InputStream is = App.class.getResourceAsStream("/index.html");
                if (is == null) {
                    is = App.class.getResourceAsStream("/web/index.html");
                }
                if (is != null) {
                    responseBytes = is.readAllBytes();
                    is.close();
                } else {
                    String fallback = "<html><body><h1>Student Registration Application</h1><p>Running on Java/Docker.</p></body></html>";
                    responseBytes = fallback.getBytes(StandardCharsets.UTF_8);
                }
            }

            exchange.getResponseHeaders().set("Content-Type", contentType);
            exchange.sendResponseHeaders(200, responseBytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(responseBytes);
            }
        }
    }

    static class RegistrationHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                InputStream is = exchange.getRequestBody();
                String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);

                String responseJson = "{\"status\":\"success\",\"message\":\"Registration received successfully!\"}";
                byte[] responseBytes = responseJson.getBytes(StandardCharsets.UTF_8);

                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, responseBytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(responseBytes);
                }
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }
    }

    static class HealthHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String resp = "{\"status\":\"UP\",\"application\":\"Student Registration System\",\"version\":\"1.0.0\"}";
            byte[] bytes = resp.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
    }
}
