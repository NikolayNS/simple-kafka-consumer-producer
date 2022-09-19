package com.dmitrenko.simplekafkaconsumerproducer.service;

public interface ValidationService {

    <D> D validate(D value);
}
