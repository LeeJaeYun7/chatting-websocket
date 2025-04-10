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
import org.apache.kafka.common.errors.TimeoutException;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GroupChatMessageProducerImpl implements GroupChatMessageProducer {

    private final JsonConverter jsonConverter;
    private final RetryTemplate retryTemplate;  // RetryTemplate을 주입받습니다.
    private final SimpleRetryPolicy retryPolicy;  // RetryPolicy를 주입받습니다.
    private KafkaProducer<String, String> producer;

    @Autowired
    private KafkaConfig kafkaConfig;

    @PostConstruct
    public void init() {
        this.producer = new KafkaProducer<>(kafkaConfig.kafkaProducerConfig());
    }

    @Override
    public void sendGroupChatMessage(GroupChatMessageServerEvent event) {
        String eventJson = jsonConverter.convertToJson(event);
        ProducerRecord<String, String> record = new ProducerRecord<>(GroupChatMessageTopics.CHAT_MESSAGE_TOPIC, eventJson);

        // RetryTemplate을 사용하여 최대 재시도 횟수만큼 재시도
        retryTemplate.execute(context -> {
            try {
                producer.send(record);  // 메시지 발송
            } catch (TimeoutException e) {
                // 모든 재시도 시도 후에도 실패하면 DLQ로 메시지를 전송
                if (context.getRetryCount() >= retryPolicy.getMaxAttempts()) {
                    sendToDeadLetterQueue(eventJson);  // 실패한 메시지를 DLQ로 전송
                }
                throw e;  // 예외를 다시 던져서 재시도 로직이 동작하도록 함
            }
            return null;  // 성공적으로 메시지가 발송되면 null 반환
        });
    }

    private void sendToDeadLetterQueue(String eventJson) {
        // 실패한 메시지를 데드레터 큐로 전송
        ProducerRecord<String, String> dlqRecord = new ProducerRecord<>(GroupChatMessageTopics.DLQ_TOPIC, eventJson);
        producer.send(dlqRecord);  // DLQ에 메시지 발송
    }

    @PreDestroy
    public void close() {
        producer.flush();
        producer.close();
    }
}
