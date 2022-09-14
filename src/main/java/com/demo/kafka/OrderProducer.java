package com.demo.kafka;

import com.demo.kafka.OrderCallback;
import com.demo.kafka.customSerializer.Order;
import com.demo.kafka.customSerializer.VIPPartitioner;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

public class OrderProducer {

    public static void main(String args[]) {
        Properties props = new Properties();
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "com.demo.kafka.customSerializer.OrderSerializer");
        props.setProperty(ProducerConfig.ACKS_CONFIG,"all");
        props.setProperty(ProducerConfig.BUFFER_MEMORY_CONFIG,"34343434");
        props.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG,"gzip");
        props.setProperty(ProducerConfig.RETRIES_CONFIG,"2");
        props.setProperty(ProducerConfig.RETRY_BACKOFF_MS_CONFIG,"400");
        props.setProperty(ProducerConfig.BATCH_SIZE_CONFIG,"1024567289");
        props.setProperty(ProducerConfig.LINGER_MS_CONFIG,"500");
        props.setProperty(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG,"200");
        props.setProperty("partitioner.class", VIPPartitioner.class.getName());

        KafkaProducer<String, Order> producer = new KafkaProducer<>(props);
        Order order = new Order("Rahul", "AC", 1);
        ProducerRecord<String, Order> record = new ProducerRecord<>("OrderTopic", order.getCustomerName(), order);
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
