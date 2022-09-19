package com.dmitrenko.simplekafkaconsumerproducer.integration.kafka.producer.impl;

import com.dmitrenko.simplekafkaconsumerproducer.integration.kafka.producer.KafkaProducer;
import com.dmitrenko.simplekafkaconsumerproducer.model.dto.response.ApiUserResponse;
import com.dmitrenko.simplekafkaconsumerproducer.model.dto.view.HeadersView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerImpl implements KafkaProducer<String, HeadersView, ApiUserResponse> {

    private final KafkaTemplate<String, ApiUserResponse> kafkaTemplate;

    @Value("${spring.kafka.topics.api-example-response-v1.name}") private String topic;

    @Override
    public void sendMessage(String key, ApiUserResponse value) {
        var producerRecord = new ProducerRecord<>(topic, key, value);
        this.kafkaTemplate.send(producerRecord);

        log.info("Send message to {}, message: {}", topic, value);
    }

    @Override
    public void sendMessage(String key, HeadersView headers, ApiUserResponse value) {
        var producerRecord = new ProducerRecord<>(topic, key, value);
        producerRecord.headers().add("transactionId", headers.getTransactionId().getBytes());
        this.kafkaTemplate.send(producerRecord);

        log.info("Send message to {}, transactionId: {}, message: {}", topic, headers.getTransactionId(), value);
    }
}
