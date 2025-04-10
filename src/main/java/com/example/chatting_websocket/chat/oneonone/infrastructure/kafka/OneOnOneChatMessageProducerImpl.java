package com.example.chatting_websocket.chat.oneonone.infrastructure.kafka;


import com.example.chatting_websocket.chat.oneonone.domain.event.OneOnOneChatMessageProducer;
import com.example.chatting_websocket.chat.oneonone.domain.event.OneOnOneChatMessageServerEvent;
import com.example.chatting_websocket.chat.oneonone.infrastructure.kafka.enums.OneOnOneChatMessageTopics;
import com.example.chatting_websocket.config.KafkaConfig;
import com.example.chatting_websocket.shared.utils.JsonConverter;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OneOnOneChatMessageProducerImpl implements OneOnOneChatMessageProducer {

    private final JsonConverter jsonConverter;
    private KafkaProducer<String, String> producer;

    @Autowired
    private KafkaConfig kafkaConfig;

    @PostConstruct
    public void init() {
        this.producer = new KafkaProducer<>(kafkaConfig.kafkaProducerConfig());
    }

    public void sendOneOnOneChatMessage(OneOnOneChatMessageServerEvent event){
        String eventJson = jsonConverter.convertToJson(event);
        ProducerRecord<String, String> record = new ProducerRecord<>(OneOnOneChatMessageTopics.CHAT_MESSAGE_TOPIC, eventJson);
        producer.send(record);
    }

    @PreDestroy
    public void close() {
        producer.flush();
        producer.close();
    }
}