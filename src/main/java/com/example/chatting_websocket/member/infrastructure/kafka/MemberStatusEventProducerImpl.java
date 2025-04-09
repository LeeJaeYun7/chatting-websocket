package com.example.chatting_websocket.member.infrastructure.kafka;

import com.example.chatting_websocket.config.kafka.KafkaConfig;
import com.example.chatting_websocket.member.domain.event.MemberStatusChangeEvent;
import com.example.chatting_websocket.member.domain.event.MemberStatusEventProducer;
import com.example.chatting_websocket.member.infrastructure.kafka.enums.MemberStatusEventTopics;
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
public class MemberStatusEventProducerImpl implements MemberStatusEventProducer {

    private final JsonConverter jsonConverter;
    private KafkaProducer<String, String> producer;

    @Autowired
    private KafkaConfig kafkaConfig;

    @PostConstruct
    public void init() {
        this.producer = new KafkaProducer<>(kafkaConfig.kafkaProducerConfig());
    }

    public void sendMemberStatusChangeEvent(String memberId, boolean isOnline) {
        MemberStatusChangeEvent event = MemberStatusChangeEvent.of(memberId, isOnline);
        String eventJson = jsonConverter.convertToJson(event);

        ProducerRecord<String, String> record = new ProducerRecord<>(MemberStatusEventTopics.MEMBER_STATUS_TOPIC, memberId, eventJson);
        producer.send(record);
    }

    @PreDestroy
    public void close() {
        producer.flush();
        producer.close();
    }

}
