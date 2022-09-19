package com.dmitrenko.simplekafkaconsumerproducer.mapper;

import com.dmitrenko.simplekafkaconsumerproducer.model.dto.request.ApiUserRequest;
import com.dmitrenko.simplekafkaconsumerproducer.model.dto.response.ApiUserResponse;

public interface UserResponseMapper {

    ApiUserResponse from(ApiUserRequest source);
}
