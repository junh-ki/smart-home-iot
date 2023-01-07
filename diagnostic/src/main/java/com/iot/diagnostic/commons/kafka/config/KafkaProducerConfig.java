package com.iot.diagnostic.commons.kafka.config;

import com.iot.diagnostic.commons.kafka.dto.RcTelemetryDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    private static final String APP_PROP_KAFKA_SERVER = "${spring.kafka.bootstrap-servers}";
    private final String kafkaBootstrapServer;

    @Autowired
    public KafkaProducerConfig(@Value(APP_PROP_KAFKA_SERVER) String bootstrapServer) {
        this.kafkaBootstrapServer = bootstrapServer;
    }

    @Bean
    public ProducerFactory<String, RcTelemetryDto> rcTelemetryProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    private Map<String, Object> producerConfig() {
        final Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.kafkaBootstrapServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }

    @Bean
    public KafkaTemplate<String, RcTelemetryDto> rcTelemetryKafkaTemplate(ProducerFactory<String, RcTelemetryDto> rcTelemetryProducerFactory) {
        return new KafkaTemplate<>(rcTelemetryProducerFactory);
    }

}
