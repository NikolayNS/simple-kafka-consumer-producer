package com.dmitrenko.simplekafkaconsumerproducer.integration.kafka.producer;

public interface KafkaProducer<K, H, V> {

    void sendMessage(K key, V value);

    void sendMessage(K key, H headers, V value);
}
