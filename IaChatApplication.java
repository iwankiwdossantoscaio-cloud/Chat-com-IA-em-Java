package com.iachat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IaChatApplication {
    public static void main(String[] args) {
        SpringApplication.run(IaChatApplication.class, args);
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║   IA Chat rodando em:                ║");
        System.out.println("║   http://localhost:8080              ║");
        System.out.println("╚══════════════════════════════════════╝\n");
    }
}
