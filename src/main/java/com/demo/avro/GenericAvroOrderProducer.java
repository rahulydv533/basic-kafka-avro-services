package com.demo.avro;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

public class GenericAvroOrderProducer {

    public static void main(String args[]) {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("key.serializer", KafkaAvroSerializer.class.getName());
        props.setProperty("value.serializer", KafkaAvroSerializer.class.getName());
        props.setProperty("schema.registry.url", "http://localhost:8081");

        KafkaProducer<String, GenericRecord> producer = new KafkaProducer<>(props);
        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse("{\n" +
                "\"namespace\":\"com.demo.avro\",\n" +
                "\"type\":\"record\",\n" +
                "\"name\":\"GenericAvroOrder\",\n" +
                "\"fields\":[\n" +
                "{\"name\":\"customerName\",\"type\":\"string\"},\n" +
                "{\"name\":\"product\",\"type\":\"string\"},\n" +
                "{\"name\":\"quantity\",\"type\":\"int\"}\n" +
                "]\n" +
                "}");
        GenericRecord order = new GenericData.Record(schema);
        order.put("customerName","Rahul");
        order.put("product","TV");
        order.put("quantity",15);
        ProducerRecord<String, GenericRecord> record = new ProducerRecord<>("OrderAvroTopic", order.get("customerName").toString(), order);
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
