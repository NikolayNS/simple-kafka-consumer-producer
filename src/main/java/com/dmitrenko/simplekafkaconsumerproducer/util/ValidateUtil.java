package com.dmitrenko.simplekafkaconsumerproducer.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Slf4j
@Service
@Validated
public class ValidateUtil {

    public <D> D validate(@Valid D request) {
        log.info("Successful validation for request: {}", request.getClass());

        return request;
    }
}
