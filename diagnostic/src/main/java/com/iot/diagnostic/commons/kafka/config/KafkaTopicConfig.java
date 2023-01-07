package com.iot.diagnostic.commons.kafka.config;

import com.iot.diagnostic.commons.kafka.KafkaTopics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic rcTelemetryTopic() {
        return TopicBuilder.name(KafkaTopics.TOPIC_RC_TELEMETRY)
                .build();
    }

}
