package com.example.chatting_websocket.member.infrastructure.kafka;

import com.example.chatting_websocket.config.KafkaConfig;
import com.example.chatting_websocket.member.domain.event.MemberStatusChangeEvent;
import com.example.chatting_websocket.member.domain.event.MemberStatusEventProducer;
import com.example.chatting_websocket.member.infrastructure.kafka.enums.MemberStatusTopics;
import com.example.chatting_websocket.shared.utils.JsonConverter;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.TimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberStatusEventProducerImpl implements MemberStatusEventProducer {

    private final JsonConverter jsonConverter;
    private final RetryTemplate retryTemplate;  // RetryTemplate 주입
    private final SimpleRetryPolicy retryPolicy;
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

        ProducerRecord<String, String> record = new ProducerRecord<>(MemberStatusTopics.MEMBER_STATUS_TOPIC, eventJson);

        // RetryTemplate을 사용하여 재시도 로직 적용
        retryTemplate.execute(context -> {
            try {
                producer.send(record);  // 메시지 발송
            } catch (TimeoutException e) {
                // 재시도 횟수가 초과되면 메시지를 DLQ로 전송
                if (context.getRetryCount() >= retryPolicy.getMaxAttempts()) {
                    sendToDeadLetterQueue(eventJson);  // 실패한 메시지를 DLQ로 전송
                }
                throw e;  // 예외를 다시 던져서 재시도 로직이 동작하도록 함
            }
            return null;
        });
    }

    private void sendToDeadLetterQueue(String eventJson) {
        // 실패한 메시지를 데드레터 큐로 전송
        ProducerRecord<String, String> dlqRecord = new ProducerRecord<>(MemberStatusTopics.DLQ_TOPIC, eventJson);
        producer.send(dlqRecord);  // DLQ에 메시지 발송
    }

    @PreDestroy
    public void close() {
        producer.flush();
        producer.close();
    }
}
