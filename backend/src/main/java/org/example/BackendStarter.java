package org.example;

import org.springframework.boot.SpringApplication;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class BackendStarter {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ApplicationBackendConfig.class);
        app.run(args);
    }
}