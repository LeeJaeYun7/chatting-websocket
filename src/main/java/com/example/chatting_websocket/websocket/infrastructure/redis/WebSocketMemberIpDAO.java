package com.example.chatting_websocket.websocket.infrastructure.redis;

import com.example.chatting_websocket.websocket.infrastructure.redis.enums.RedisKey;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
@RequiredArgsConstructor
public class WebSocketMemberIpDAO {

    private final RedissonClient redissonClient;

    public void saveMemberIp(long memberId) throws UnknownHostException {
        String ipAddress = getIpAddress();
        RMapCache<String, String> ipMap = redissonClient.getMapCache(RedisKey.WEBSOCKET_IP_KEY);
        ipMap.put(String.valueOf(memberId), ipAddress);
    }

    public String getIpAddress() throws UnknownHostException{
        return InetAddress.getLocalHost().getHostAddress();
    }
}
