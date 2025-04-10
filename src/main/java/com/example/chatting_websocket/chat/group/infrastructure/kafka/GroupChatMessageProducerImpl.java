package com.example.chatting_websocket.chat.group.infrastructure.kafka;

import com.example.chatting_websocket.chat.group.domain.event.GroupChatMessageProducer;
import com.example.chatting_websocket.chat.group.domain.event.GroupChatMessageServerEvent;
import com.example.chatting_websocket.chat.group.infrastructure.kafka.enums.GroupChatMessageTopics;
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
public class GroupChatMessageProducerImpl implements GroupChatMessageProducer {

    private final JsonConverter jsonConverter;
    private KafkaProducer<String, String> producer;

    @Autowired
    private KafkaConfig kafkaConfig;

    @PostConstruct
    public void init() {
        this.producer = new KafkaProducer<>(kafkaConfig.kafkaProducerConfig());
    }

    public void sendGroupChatMessage(GroupChatMessageServerEvent event){
        String eventJson = jsonConverter.convertToJson(event);
        ProducerRecord<String, String> record = new ProducerRecord<>(GroupChatMessageTopics.CHAT_MESSAGE_TOPIC, eventJson);
        producer.send(record);
    }

    @PreDestroy
    public void close() {
        producer.flush();
        producer.close();
    }
}
