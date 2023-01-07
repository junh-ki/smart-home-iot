package com.iot.diagnostic.commons.kafka.service;

import com.iot.diagnostic.commons.kafka.KafkaTopics;
import com.iot.diagnostic.commons.kafka.dto.RcTelemetryDto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Component
public class RcTelemetryProducer {

    @Bean
    public CommandLineRunner commandLineRunner(KafkaTemplate<String, RcTelemetryDto> rcTelemetryKafkaTemplate) {
        return args -> {
            for (int i = 0; i < 1000; i++) {
                rcTelemetryKafkaTemplate.send(KafkaTopics.TOPIC_RC_TELEMETRY, RcTelemetryDto.builder()
                        .steeringVoltage(2.701D)
                        .throttleVoltage(2.342D)
                        .liPoVoltageCell1(3.846D)
                        .liPoVoltageCell2(3.986D)
                        .timestamp(LocalDateTime.now())
                        .build());
                Thread.sleep(TimeUnit.MILLISECONDS.toMillis(100));
            }
        };
    }

}
