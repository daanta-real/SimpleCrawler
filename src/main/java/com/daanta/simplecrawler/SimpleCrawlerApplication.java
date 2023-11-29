package com.daanta.simplecrawler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class SimpleCrawlerApplication {

    @Value("${server.port}")
    private int port;

    @Value("${server.servlet.context-path:/}")
    private String contextPath;

    public static void main(String[] args) {
        SpringApplication.run(SimpleCrawlerApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> log.debug("\n\n - SERVER STARTED -\nhttp://localhost:{}{}\n", port, contextPath);
    }

}
