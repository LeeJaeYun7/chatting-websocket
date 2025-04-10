package com.example.chatting_websocket.chat.oneonone.infrastructure.redis;

import com.example.chatting_websocket.chat.oneonone.controller.dto.OneOnOneChatMessageResponse;
import com.example.chatting_websocket.chat.oneonone.domain.event.OneOnOneChatMessageEvent;
import com.example.chatting_websocket.chat.oneonone.domain.event.OneOnOneChatMessageSubscriber;
import com.example.chatting_websocket.chat.oneonone.infrastructure.redis.enums.RedisKey;
import com.example.chatting_websocket.shared.utils.JsonConverter;
import com.example.chatting_websocket.websocket.infrastructure.inmemory.SessionManager;
import com.example.chatting_websocket.websocket.application.WebSocketClientMessageSender;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class OneOnOneChatMessageSubscriberImpl implements OneOnOneChatMessageSubscriber {

    private final RedissonClient redissonClient;
    private final JsonConverter jsonConverter;
    private final SessionManager sessionManager;
    private final WebSocketClientMessageSender webSocketClientMessageSender;

    @PostConstruct
    public void init() {
        try {
            startOneOnOneChatMessageEventChannelListening();
        } catch (Exception e) {
            log.error("Error during Redis Pub/Sub channel initialization", e);
        }
    }

    private void startOneOnOneChatMessageEventChannelListening() {
        // Redis 채널을 구독하기 위한 RTopic 객체
        RTopic topic = redissonClient.getTopic(RedisKey.ONEONONE_CHAT_CHANNEL);

        // 메시지 리스너 등록
        topic.addListener(String.class, (channel, message) -> {
            OneOnOneChatMessageEvent event = jsonConverter.convertFromJson(message, OneOnOneChatMessageEvent.class);

            String roomId = event.getRoomId();
            String senderId = event.getSenderId();
            String receiverId = event.getReceiverId();
            String content = event.getContent();
            LocalDateTime timestamp = event.getTimestamp();

            OneOnOneChatMessageResponse response = OneOnOneChatMessageResponse.of(roomId, senderId, receiverId, content, timestamp);

            if(sessionManager.containsSession(receiverId)){
                String sessionId = sessionManager.getSession(receiverId);
                webSocketClientMessageSender.sendOneOnOneChatMessageToClient(sessionId, response);
            }
        });
    };
}