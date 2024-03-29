package com.iot.diagnostic.commons.kafka.service;

import com.iot.diagnostic.commons.kafka.KafkaTopics;
import com.iot.diagnostic.commons.kafka.dto.RcTelemetryDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class RcTelemetryListener {

    private static final String PARTY_POPPER = "\uD83C\uDF89";
    private final Logger logger;

    public RcTelemetryListener() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    @KafkaListener(topics = KafkaTopics.TOPIC_RC_TELEMETRY, groupId = "Akron")
    void rcTelemetryListener(RcTelemetryDto rcTelemetryDto) {
        this.logger.info("Listener received: " + rcTelemetryDto + " " + PARTY_POPPER);
    }

}
