package com.dmitrenko.simplekafkaconsumerproducer.model.dto.request;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class ApiUserRequest {

    @NotNull(message = "Field [userId] mustn't be null")
    private UUID userId;

    @NotBlank(message = "Field [firstName] mustn't be empty")
    private String firstName;

    @NotBlank(message = "Field [lastName] mustn't be empty")
    private String lastName;

    @NotBlank(message = "Field [email] mustn't be empty")
    private String email;

    @NotNull(message = "Field [phone] mustn't be null")
    private @Valid ApiUserRequestPhone phone;
}
