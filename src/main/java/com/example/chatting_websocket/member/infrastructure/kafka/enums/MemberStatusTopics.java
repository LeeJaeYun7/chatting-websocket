package com.example.chatting_websocket.member.infrastructure.kafka.enums;

public interface MemberStatusTopics {
    public static final String MEMBER_STATUS_TOPIC = "member-status-topic";
    public static final String DLQ_TOPIC = "member-status-dlq-topic";
}
