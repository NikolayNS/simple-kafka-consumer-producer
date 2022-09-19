package com.dmitrenko.simplekafkaconsumerproducer.service.impl;

import com.dmitrenko.simplekafkaconsumerproducer.exception.ValidationException;
import com.dmitrenko.simplekafkaconsumerproducer.service.ValidationService;
import com.dmitrenko.simplekafkaconsumerproducer.util.ValidateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValidationServiceImpl implements ValidationService {

    private final ValidateUtil validateUtil;

    @Override
    public <D> D validate(D value) {
        log.info("Start validate process for value: {}", value);

        try {
            return validateUtil.validate(value);

        } catch (ConstraintViolationException e) {
            var fields = e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessageTemplate)
                .collect(Collectors.joining(", "));

            log.error("Failed validation, error fields: {}", fields);
            throw new ValidationException(String.format("Failed validation, error fields: [%s]", fields));
        } catch (Exception e) {
            log.error("Failed validation, error: {}", e.getMessage());
            throw new ValidationException(String.format("Failed validation, error: [%s]", e.getMessage()));
        }
    }
}
