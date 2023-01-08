package com.iot.diagnostic.commons.kafka.controller;

import com.iot.diagnostic.commons.kafka.KafkaTopics;
import com.iot.diagnostic.commons.kafka.dto.RcTelemetryDto;
import com.iot.diagnostic.commons.kafka.dto.RcTelemetryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/telemetries")
public class RcTelemetryController {

    private final KafkaTemplate<String, RcTelemetryDto> rcTelemetryKafkaTemplate;

    @Autowired
    public RcTelemetryController(KafkaTemplate<String, RcTelemetryDto> rcTelemetryKafkaTemplate) {
        this.rcTelemetryKafkaTemplate = rcTelemetryKafkaTemplate;
    }

    @PostMapping
    public void publish(@RequestBody RcTelemetryRequest rcTelemetryRequest) {
        this.rcTelemetryKafkaTemplate.send(KafkaTopics.TOPIC_RC_TELEMETRY, RcTelemetryDto.builder()
                .throttleVoltage(rcTelemetryRequest.throttleVoltage())
                .steeringVoltage(rcTelemetryRequest.steeringVoltage())
                .liPoVoltageCell1(rcTelemetryRequest.liPoVoltageCell1())
                .liPoVoltageCell2(rcTelemetryRequest.liPoVoltageCell2())
                .timestamp(LocalDateTime.now())
                .build());
    }

}
