package com.example.chatting_websocket.websocket.infrastructure.redis;

import com.example.chatting_websocket.websocket.infrastructure.redis.enums.RedisKey;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebSocketMemberSessionDAO {

    private final RedissonClient redissonClient;

    public void saveWebSocketSession(String memberId, String sessionId) {
        RMapCache<String, String> sessionMap = redissonClient.getMapCache(RedisKey.WEBSOCKET_SESSION_KEY);
        sessionMap.put(memberId, sessionId);
    }

    public String removeWebSocketSession(String memberId){
        RMapCache<String, String> sessionMap = redissonClient.getMapCache(RedisKey.WEBSOCKET_SESSION_KEY);
        return sessionMap.remove(memberId);
    }
}
