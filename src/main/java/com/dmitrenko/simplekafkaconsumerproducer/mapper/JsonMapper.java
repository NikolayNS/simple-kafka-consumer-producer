package com.dmitrenko.simplekafkaconsumerproducer.mapper;

public interface JsonMapper {

    <D> D toObject(String value, Class<D> clazz);

    <D> String toJson(D object);
}
