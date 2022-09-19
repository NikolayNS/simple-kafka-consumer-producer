package com.dmitrenko.simplekafkaconsumerproducer.exception;

public class ConversionValueException extends RuntimeException {

    public ConversionValueException() {
        super();
    }

    public ConversionValueException(String message) {
        super(message);
    }
}
