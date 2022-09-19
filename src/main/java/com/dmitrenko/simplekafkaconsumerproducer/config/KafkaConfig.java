package com.dmitrenko.simplekafkaconsumerproducer.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.properties.bootstrap-servers}") private String bootstrapServers;
    @Value("${spring.kafka.retry.interval}") private Long interval;
    @Value("${spring.kafka.retry.max-attempts}") private Long maxAttempts;
    @Value("${kafka.ssl.enabled}") private boolean sslEnabled;
    @Value("${kafka.ssl.protocol}") private String protocol;
    @Value("${kafka.ssl.key-store-location}") private String keyStoreLocation;
    @Value("${kafka.ssl.key-store-password}") private String keyStorePassword;
    @Value("${kafka.ssl.key-password}") private String keyPassword;
    @Value("${kafka.ssl.trust-store-location}") private String trustStoreLocation;
    @Value("${kafka.ssl.trust-store-password}") private String trustStorePassword;

    @Bean
    public <M> KafkaTemplate<String, M> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public <M> ProducerFactory<String, M> producerFactory() {
        var props = new HashMap<String, Object>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        addSsl(props);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    @Primary
    public <M> ConcurrentKafkaListenerContainerFactory<String, M> kafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, M>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(1);
        factory.setErrorHandler(new SeekToCurrentErrorHandler(new FixedBackOff(interval, maxAttempts)));
        return factory;
    }

    @Bean
    public <M> ConsumerFactory<String, M> consumerFactory() {
        var props = new HashMap<String, Object>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        addSsl(props);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    private void addSsl(Map<String, Object> props) {
        if (sslEnabled) {
            props.put("security.protocol", protocol);
            props.put("ssl.keystore.location", keyStoreLocation);
            props.put("ssl.keystore.password", keyStorePassword);
            props.put("ssl.key.password", keyPassword);
            props.put("ssl.truststore.location", trustStoreLocation);
            props.put("ssl.truststore.password", trustStorePassword);
        }
    }
}
