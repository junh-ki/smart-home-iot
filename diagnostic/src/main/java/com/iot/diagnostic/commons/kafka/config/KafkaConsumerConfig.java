package com.iot.diagnostic.commons.kafka.config;

import com.iot.diagnostic.commons.kafka.dto.RcTelemetryDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    private static final String APP_PROP_KAFKA_SERVER = "${spring.kafka.bootstrap-servers}";
    private final String kafkaBootstrapServer;

    @Autowired
    public KafkaConsumerConfig(@Value(APP_PROP_KAFKA_SERVER) String bootstrapServer) {
        this.kafkaBootstrapServer = bootstrapServer;
    }

    @Bean
    public ConsumerFactory<String, RcTelemetryDto> rcTelemetryConsumerFactory() {
        final JsonDeserializer<RcTelemetryDto> rcTelemetryJsonDeserializer = new JsonDeserializer<>(RcTelemetryDto.class);
        rcTelemetryJsonDeserializer.addTrustedPackages(getClass().getPackage().getName());
        final Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.kafkaBootstrapServer);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, rcTelemetryJsonDeserializer);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), rcTelemetryJsonDeserializer);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, RcTelemetryDto>> kafkaListenerContainerFactory() {
        final ConcurrentKafkaListenerContainerFactory<String, RcTelemetryDto> concurrentKafkaListenerContainerFactory
                = new ConcurrentKafkaListenerContainerFactory<>();
        concurrentKafkaListenerContainerFactory.setConsumerFactory(rcTelemetryConsumerFactory());
        return concurrentKafkaListenerContainerFactory;
    }

}
