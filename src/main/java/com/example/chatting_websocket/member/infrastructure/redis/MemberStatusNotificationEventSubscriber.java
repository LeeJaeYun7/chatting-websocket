package com.example.chatting_websocket.member.infrastructure.redis;

import com.example.chatting_websocket.member.controller.dto.MemberStatusNotificationResponse;
import com.example.chatting_websocket.member.domain.event.MemberStatusNotificationEvent;
import com.example.chatting_websocket.member.infrastructure.redis.enums.RedisKey;
import com.example.chatting_websocket.shared.utils.JsonConverter;
import com.example.chatting_websocket.websocket.infrastructure.SessionManager;
import com.example.chatting_websocket.websocket.infrastructure.WebSocketClientMessageSender;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class MemberStatusNotificationEventSubscriber {

    private final RedissonClient redissonClient;
    private final JsonConverter jsonConverter;
    private final SessionManager sessionManager;
    private final WebSocketClientMessageSender webSocketClientMessageSender;

    @PostConstruct
    public void init() {
        try {
            startMemberStatusEventChannelListening();
        } catch (Exception e) {
            log.error("Error during Redis Pub/Sub channel initialization", e);
        }
    }

    private void startMemberStatusEventChannelListening() {
        // Redis 채널을 구독하기 위한 RTopic 객체
        RTopic topic = redissonClient.getTopic(RedisKey.MEMBER_STATUS_CHANNEL);

        // 메시지 리스너 등록
        topic.addListener(String.class, (channel, message) -> {
            MemberStatusNotificationEvent event = jsonConverter.convertFromJson(message, MemberStatusNotificationEvent.class);

            String memberId = event.getMemberId();
            List<String> friendIds = event.getFriendIds();
            boolean isOnline = event.isOnline();

            MemberStatusNotificationResponse response = MemberStatusNotificationResponse.of(memberId, isOnline);

            for(String friendId: friendIds){
                if(sessionManager.containsSession(friendId)){
                    String sessionId = sessionManager.getSession(friendId);
                    webSocketClientMessageSender.sendMemberStatusNotificationToClient(sessionId, response);
                }
            }
        });
    };
}