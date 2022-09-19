package com.dmitrenko.simplekafkaconsumerproducer.service.impl;

import com.dmitrenko.simplekafkaconsumerproducer.integration.kafka.producer.KafkaProducer;
import com.dmitrenko.simplekafkaconsumerproducer.mapper.JsonMapper;
import com.dmitrenko.simplekafkaconsumerproducer.mapper.UserResponseMapper;
import com.dmitrenko.simplekafkaconsumerproducer.model.dto.request.ApiUserRequest;
import com.dmitrenko.simplekafkaconsumerproducer.model.dto.response.ApiUserResponse;
import com.dmitrenko.simplekafkaconsumerproducer.model.dto.view.HeadersView;
import com.dmitrenko.simplekafkaconsumerproducer.service.ApiService;
import com.dmitrenko.simplekafkaconsumerproducer.service.ValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {

    private final ValidationService validationService;

    private final KafkaProducer<String, HeadersView, ApiUserResponse> kafkaProducer;

    private final JsonMapper jsonMapper;
    private final UserResponseMapper userResponseMapper;

    @Override
    public void someAction(String transactionId, String message) {
        log.info("Some action for transactionId: {}, message: {}", transactionId, message);

        var request = jsonMapper.toObject(message, ApiUserRequest.class);
        request = validationService.validate(request);

        log.info("Some action right here");

        kafkaProducer
            .sendMessage(
                UUID.randomUUID().toString(),
                new HeadersView().setTransactionId(transactionId),
                userResponseMapper.from(request));
    }
}
