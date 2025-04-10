package com.example.chatting_websocket.member.infrastructure.kafka;

import com.example.chatting_websocket.chat.group.infrastructure.kafka.enums.GroupChatMessageTopics;
import com.example.chatting_websocket.config.KafkaConfig;
import com.example.chatting_websocket.member.infrastructure.kafka.enums.MemberStatusTopics;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@Component
@RequiredArgsConstructor
@Slf4j
public class MemberStatusDeadLetterQueueReSender {

    private final RetryTemplate retryTemplate;  // RetryTemplate을 주입받습니다.
    private final SimpleRetryPolicy retryPolicy;  // RetryPolicy를 주입받습니다.
    private KafkaProducer<String, String> producer;

    @Autowired
    private KafkaConfig kafkaConfig;

    @PostConstruct
    public void init() {
        this.producer = new KafkaProducer<>(kafkaConfig.kafkaProducerConfig());
        retryTemplate.setRetryPolicy(retryPolicy);
    }

    private KafkaConsumer<String, String> createConsumer() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("group.id", "member-status-dlq-consumer-group");
        properties.put("key.deserializer", StringDeserializer.class.getName());
        properties.put("value.deserializer", StringDeserializer.class.getName());
        properties.put("auto.offset.reset", "earliest");

        return new KafkaConsumer<>(properties);
    }


    @Scheduled(fixedRate = 10000)  // 10초마다 DLQ 확인
    public void resendMessagesFromDLQ() {
        KafkaConsumer<String, String> consumer = createConsumer();
        consumer.subscribe(Collections.singletonList(GroupChatMessageTopics.DLQ_TOPIC));

        List<String> dlqMessages = new ArrayList<>();

        while (true) {
            var records = consumer.poll(100);  // 1초마다 DLQ에서 메시지 가져오기

            records.forEach(record -> dlqMessages.add(record.value()));

            // 메시지가 하나라도 있으면 계속 가져옴
            if (records.isEmpty()) {
                break;  // 더 이상 메시지가 없으면 종료
            }
        }


        for (String message : dlqMessages) {
            try {
                // RetryTemplate을 사용하여 재시도 로직 적용
                retryTemplate.execute(context -> {
                    producer.send(new ProducerRecord<>(MemberStatusTopics.MEMBER_STATUS_TOPIC, message));
                    log.info("Re-sent message to original topic: {}", message);
                    return null;  // 재발송 후 null 반환
                });
            } catch (Exception e) {
                // 재시도 후에도 실패한 경우 DLQ에 재발송
                producer.send(new ProducerRecord<>(MemberStatusTopics.DLQ_TOPIC, message));
                log.error("Failed to resend message: {}", message, e);
            }
        }
    }
}
