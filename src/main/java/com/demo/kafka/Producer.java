package com.demo.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;


import java.util.Properties;

public class Producer {
    public static void main(String args[]) {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.setProperty("value.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");

        KafkaProducer<String, Integer> producer = new KafkaProducer<>(props);
        ProducerRecord<String, Integer> record = new ProducerRecord<>("OrderTopic", "First-Topic", 10);
        //TODO SYNC call
        /*try{
            RecordMetadata recordMetadata = producer.send(record).get(); //Synchronous Call
            System.out.println(recordMetadata.partition());
            System.out.println(recordMetadata.offset());
            System.out.println("Message Published");
        } catch(Exception ex ){
            ex.printStackTrace();
            System.out.println("failed");
        } finally {
            producer.close();
        }*/

        //TODO ASYNC Call
        try {
            producer.send(record, new OrderCallback()).get(); //Synchronous Call
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("failed");
        } finally {
            producer.close();
        }


    }
}
