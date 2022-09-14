package com.demo.kafka.customSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class OrderDeserializer implements Deserializer<Order> {
    @Override
    public Order deserialize(String s, byte[] bytes) {
        Order order = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            order = objectMapper.readValue(bytes, Order.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return order;
    }
}
