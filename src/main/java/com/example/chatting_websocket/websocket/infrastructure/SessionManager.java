package com.example.chatting_websocket.websocket.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class SessionManager {

    // 세션 ID를 key로만 저장, Set처럼 활용
    private final Map<String, Boolean> sessionMap = new ConcurrentHashMap<>();

    public void saveSession(String sessionId) {
        sessionMap.put(sessionId, true); // 중복 방지됨
    }

    public void removeSession(String sessionId) {
        sessionMap.remove(sessionId);
    }

    public boolean containsSession(String sessionId) {
        return sessionMap.containsKey(sessionId);
    }
}


