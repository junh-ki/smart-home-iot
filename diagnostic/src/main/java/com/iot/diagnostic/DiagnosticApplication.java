package com.iot.diagnostic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class DiagnosticApplication {

    @Bean
    public WebClient.Builder getWebClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.findAndRegisterModules();
    }

    @Bean
    public Logger getLogger() {
        return LoggerFactory.getLogger(getClass());
    }

    public static void main(String[] args) {
        SpringApplication.run(DiagnosticApplication.class, args);
    }

}
