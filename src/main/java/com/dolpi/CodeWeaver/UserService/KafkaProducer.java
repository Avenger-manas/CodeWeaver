package com.dolpi.CodeWeaver.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducer {

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    private static final String Topic="code-topic";

    public void producer(String promt){
        kafkaTemplate.send(Topic,promt);
    }


}
