package com.demo.avro;

import com.demo.kafka.customSerializer.Order;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class AvroOrderConsumer {
    public static void main(String args[]) {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("key.deserializer", KafkaAvroDeserializer.class.getName());
        props.setProperty("value.deserializer", KafkaAvroDeserializer.class.getName());
        props.setProperty("group.id", "OrderGroup");
        props.setProperty("schema.registry.url", "http://localhost:8081");
        props.setProperty("specific.avro.reader", "true");
        KafkaConsumer<String, AvroOrder> kafkaConsumer = new KafkaConsumer<>(props);
        kafkaConsumer.subscribe(Collections.singletonList("OrderAvroTopic"));
        ConsumerRecords<String, AvroOrder> orders = kafkaConsumer.poll(Duration.ofSeconds(20));
        for (ConsumerRecord<String, AvroOrder> order : orders) {
            System.out.println(order.partition());
            System.out.println(order.offset());
            AvroOrder order1 = order.value();
            System.out.println(order1.getCustomerName());
            System.out.println(order1.getProduct());
            System.out.println(order1.getQuantity());
            System.out.println("Message Consumed");
        }
        kafkaConsumer.close();
    }
}
