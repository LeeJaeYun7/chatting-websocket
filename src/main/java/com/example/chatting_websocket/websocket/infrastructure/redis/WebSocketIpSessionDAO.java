package com.example.chatting_websocket.websocket.infrastructure.redis;

import com.example.chatting_websocket.websocket.infrastructure.redis.enums.RedisKey;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
@RequiredArgsConstructor
public class WebSocketIpSessionDAO {

    private final RedissonClient redissonClient;

    public void saveIpSession(String sessionId) throws UnknownHostException {
        String ipAddress = getIpAddress();
        RList<String> sessionList = redissonClient.getList(RedisKey.WEBSOCKET_IP_SESSION_KEY + ipAddress);
        sessionList.add(sessionId);
    }

    public String getIpAddress() throws UnknownHostException{
        return InetAddress.getLocalHost().getHostAddress();
    }
}
