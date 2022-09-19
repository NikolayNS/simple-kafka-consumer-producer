package com.dmitrenko.simplekafkaconsumerproducer.mapper.impl;

import com.dmitrenko.simplekafkaconsumerproducer.exception.ConversionValueException;
import com.dmitrenko.simplekafkaconsumerproducer.mapper.JsonMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JsonMapperImpl implements JsonMapper {

    private final ObjectMapper objectMapper;

    @Override
    public <D> D toObject(String value, Class<D> clazz) {
        try {
            return objectMapper.readValue(value, clazz);
        } catch (Exception e) {
            log.error("Unsuccessfully conversion of value to {}, error: {}", clazz, e.getMessage());
            throw new ConversionValueException(String.format("Unsuccessfully conversion of value to [%s], error: %s", clazz, e.getMessage()));
        }
    }

    @Override
    public <D> String toJson(D object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            log.error("Unsuccessfully conversion of {} to Json, error: {}", object.getClass(), e.getMessage());
            throw new ConversionValueException(String.format("Unsuccessfully conversion of [%s] to Json, error: %s", object.getClass(), e.getMessage()));
        }
    }
}
