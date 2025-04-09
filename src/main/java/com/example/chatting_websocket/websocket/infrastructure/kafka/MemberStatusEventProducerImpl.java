package com.example.chatting_websocket.websocket.infrastructure.kafka;

import com.example.chatting_websocket.config.KafkaConfig;
import com.example.chatting_websocket.shared.utils.JsonConverter;
import com.example.chatting_websocket.websocket.domain.event.MemberStatusEvent;
import com.example.chatting_websocket.websocket.domain.event.MemberStatusEventProducer;
import com.example.chatting_websocket.websocket.domain.event.enums.MemberStatusConst;
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

    public void sendMemberStatusEvent(String memberId, boolean isOnline) {
        MemberStatusEvent event = MemberStatusEvent.of(memberId, isOnline);
        String eventJson = jsonConverter.convertToJson(event);

        ProducerRecord<String, String> record = new ProducerRecord<>(MemberStatusConst.MEMBER_STATUS_TOPIC, memberId, eventJson);
        producer.send(record);
    }

    @PreDestroy
    public void close() {
        producer.flush();
        producer.close();
    }

}
