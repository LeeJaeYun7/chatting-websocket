package com.example.chatting_websocket.chat.group.infrastructure.redis;

import com.example.chatting_websocket.chat.group.controller.dto.GroupChatMessageResponse;
import com.example.chatting_websocket.chat.group.domain.event.GroupChatMessageEvent;
import com.example.chatting_websocket.chat.group.domain.event.GroupChatMessageSubscriber;
import com.example.chatting_websocket.chat.group.infrastructure.redis.enums.RedisKey;
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
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class GroupChatMessageSubscriberImpl implements GroupChatMessageSubscriber {

    private final RedissonClient redissonClient;
    private final JsonConverter jsonConverter;
    private final SessionManager sessionManager;
    private final WebSocketClientMessageSender webSocketClientMessageSender;

    @PostConstruct
    public void init() {
        try {
            startGroupChatMessageEventChannelListening();
        } catch (Exception e) {
            log.error("Error during Redis Pub/Sub channel initialization", e);
        }
    }

    private void startGroupChatMessageEventChannelListening() {
        // Redis 채널을 구독하기 위한 RTopic 객체
        RTopic topic = redissonClient.getTopic(RedisKey.GROUP_CHAT_CHANNEL);

        // 메시지 리스너 등록
        topic.addListener(String.class, (channel, message) -> {
            GroupChatMessageEvent event = jsonConverter.convertFromJson(message, GroupChatMessageEvent.class);

            String roomId = event.getRoomId();
            String senderId = event.getSenderId();
            List<String> participantIds = event.getParticipantIds();
            String content = event.getContent();
            LocalDateTime timestamp = event.getTimestamp();

            GroupChatMessageResponse response = GroupChatMessageResponse.of(roomId, senderId, content, timestamp);

            for(String participantId: participantIds){
                if(sessionManager.containsSession(participantId)){
                    String sessionId = sessionManager.getSession(participantId);
                    webSocketClientMessageSender.sendGroupChatMessageToClient(sessionId, response);
                }
            }
        });
    };
}