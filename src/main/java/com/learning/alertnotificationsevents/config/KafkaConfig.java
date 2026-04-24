package com.learning.alertnotificationsevents.config;


import org.apache.kafka.common.TopicPartition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
@EnableKafka
public class KafkaConfig {

    /*
    * DLQ configuations
    * */
    @Bean
    public DefaultErrorHandler errorHandler(KafkaTemplate<Object,Object> kafkaTemplate){


        // retry after 2 sec for 3 time
        FixedBackOff backOff = new FixedBackOff(2000L,2);

        // send the fail messages to DLQ
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate,
                (record,ex)-> new TopicPartition(record.topic() + ".DLQ", record.partition()));


        return new DefaultErrorHandler(recoverer, backOff);


    }
}
