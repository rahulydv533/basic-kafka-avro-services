package com.demo.avro;

import com.demo.kafka.customSerializer.Order;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

public class AvroOrderProducer {

    public static void main(String args[]) {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("key.serializer", KafkaAvroSerializer.class.getName());
        props.setProperty("value.serializer", KafkaAvroSerializer.class.getName());
        props.setProperty("schema.registry.url", "http://localhost:8081");

        KafkaProducer<String, AvroOrder> producer = new KafkaProducer<>(props);
        AvroOrder order = new AvroOrder("Rahul", "AC", 1);
        ProducerRecord<String, AvroOrder> record = new ProducerRecord<>("OrderAvroTopic", order.getCustomerName().toString(), order);
        //TODO SYNC call
        try{
            RecordMetadata recordMetadata = producer.send(record).get(); //Synchronous Call
            System.out.println(recordMetadata.partition());
            System.out.println(recordMetadata.offset());
            System.out.println("Message Published");
        } catch(Exception ex ){
            ex.printStackTrace();
            System.out.println("failed");
        } finally {
            producer.close();
        }

        //TODO ASYNC Call
       /* try {
            producer.send(record, new OrderCallback()).get(); //Synchronous Call
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("failed");
        } finally {
            producer.close();
        }*/

    }

}
