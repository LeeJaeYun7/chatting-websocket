package com.example.chatting_websocket.websocket.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class SessionManager {

    // 세션 ID를 key로만 저장, Set처럼 활용
    private final Map<String, String> sessionMap = new ConcurrentHashMap<>();

    public void saveSession(String memberId, String sessionId) {
        sessionMap.put(memberId, sessionId); // 중복 방지됨
    }

    public void removeSession(String memberId) {
        sessionMap.remove(memberId);
    }

    public boolean containsSession(String memberId) {
        return sessionMap.containsKey(memberId);
    }

    public String getSession(String memberId){
        return sessionMap.get(memberId);
    }
}


