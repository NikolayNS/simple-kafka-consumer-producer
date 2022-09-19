package com.dmitrenko.simplekafkaconsumerproducer.integration.kafka.consumer;

import com.dmitrenko.simplekafkaconsumerproducer.service.ApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimpleConsumer {

    private final ApiService apiService;

    @KafkaListener(
        topics = "#{'${spring.kafka.topics.api-example-request-v1.name}'}",
        groupId = "#{'${spring.kafka.topics.api-example-request-v1.group-id}'}")
    public void getHistory(@Header("transactionId") String transactionId, ConsumerRecord<String, String> message) {
        MDC.put("messageKey", message.key());

        log.info("Listening api-example-request-v1, transactionId: {}, message: {}", transactionId, message.value());

        apiService.someAction(transactionId, message.value());

        MDC.clear();
    }
}
