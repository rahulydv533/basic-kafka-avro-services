package com.demo.kafka;

import com.demo.kafka.customSerializer.Order;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class OrderConsumer {
    public static void main(String args[]) {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "com.demo.kafka.customSerializer.OrderDeserializer");
        props.setProperty("group.id", "OrderGroup");
        KafkaConsumer<String, Order> kafkaConsumer = new KafkaConsumer<>(props);
        kafkaConsumer.subscribe(Collections.singletonList("OrderTopic"));
        ConsumerRecords<String, Order> orders = kafkaConsumer.poll(Duration.ofSeconds(20));
        for (ConsumerRecord<String, Order> order : orders) {
            System.out.println(order.partition());
            System.out.println(order.offset());
            Order order1 = order.value();
            System.out.println(order1.getCustomerName());
            System.out.println(order1.getProduct());
            System.out.println(order1.getQuantity());
            System.out.println(order.partition());
            System.out.println("Message Consumed");
        }
        kafkaConsumer.close();
    }
}
