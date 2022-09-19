package com.dmitrenko.simplekafkaconsumerproducer.model.dto.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class ApiUserResponse {
    private UUID userId;
    private UUID taskId;
    private String description;
}
