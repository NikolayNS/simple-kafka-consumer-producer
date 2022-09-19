package com.dmitrenko.simplekafkaconsumerproducer.model.dto.view;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class HeadersView {
    private String transactionId;
}
