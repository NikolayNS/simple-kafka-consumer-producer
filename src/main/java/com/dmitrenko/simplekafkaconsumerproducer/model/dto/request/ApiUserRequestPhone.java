package com.dmitrenko.simplekafkaconsumerproducer.model.dto.request;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
public class ApiUserRequestPhone {

    @NotBlank(message = "Field [number] mustn't be empty")
    private String number;

    @NotBlank(message = "Field [countryCode] mustn't be empty")
    private String countryCode;
}
