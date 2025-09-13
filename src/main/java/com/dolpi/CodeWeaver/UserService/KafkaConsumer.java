package com.dolpi.CodeWeaver.UserService;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class KafkaConsumer {

    private String codes;

    private final OllamaChatModel client;

    public KafkaConsumer(OllamaChatModel client) {
        this.client = client;
    }

    @KafkaListener(topics = "code-topic", groupId = "my-group")
    public void consume(String prompt) {
        this.codes = client.stream(prompt)
                .collectList()
                .map(list -> String.join(" ", list))
                .block();

    }

    public String getCodes() {
        return codes;
    }

}