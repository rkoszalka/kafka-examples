package org.koszalka.kafka.examples;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemoWithKeys {

    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger(ProducerDemoWithKeys.class);
        // create producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG  , "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // create producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        for (int i = 0; i<10; i++ ) {
            // create producer record
            ProducerRecord<String, String> record = new ProducerRecord<String, String>("first_topic", "hello world " + Integer.toString(i));

            // send dat
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e != null) {
                        logger.info("Metadata: {}", recordMetadata.toString());
                    } else {
                        logger.error("Error: {}", e);
                    }
                }
            });
        }

        producer.flush();
        producer.close();
    }

}
