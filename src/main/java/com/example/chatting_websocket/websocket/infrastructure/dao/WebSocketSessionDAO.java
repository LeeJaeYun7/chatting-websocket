package com.example.chatting_websocket.websocket.infrastructure.dao;

import com.example.chatting_websocket.websocket.infrastructure.dao.enums.RedisKey;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebSocketSessionDAO {

    private final RedissonClient redissonClient;

    public void saveWebSocketSession(String memberId, String sessionId) {
        RMapCache<String, String> sessionMap = redissonClient.getMapCache(RedisKey.WEBSOCKET_SESSION_KEY);
        sessionMap.put(memberId, sessionId);
    }

    public String getWebSocketSession(String memberId){
        RMapCache<String, String> sessionMap = redissonClient.getMapCache(RedisKey.WEBSOCKET_SESSION_KEY);
        return sessionMap.get(memberId);
    }
}
