package com.dmitrenko.simplekafkaconsumerproducer.mapper.impl;

import com.dmitrenko.simplekafkaconsumerproducer.mapper.UserResponseMapper;
import com.dmitrenko.simplekafkaconsumerproducer.model.dto.request.ApiUserRequest;
import com.dmitrenko.simplekafkaconsumerproducer.model.dto.response.ApiUserResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserResponseMapperImpl implements UserResponseMapper {

    @Override
    public ApiUserResponse from(ApiUserRequest source) {
        return new ApiUserResponse()
            .setUserId(source.getUserId())
            .setTaskId(UUID.randomUUID())
            .setDescription("Administrative processing");
    }
}
