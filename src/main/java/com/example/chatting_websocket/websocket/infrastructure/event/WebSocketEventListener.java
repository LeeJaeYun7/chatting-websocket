package com.example.chatting_websocket.websocket.infrastructure.event;

import com.example.chatting_websocket.member.domain.event.MemberStatusEventProducer;
import com.example.chatting_websocket.websocket.infrastructure.inmemory.SessionManager;
import com.example.chatting_websocket.websocket.infrastructure.redis.WebSocketMemberSessionDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final WebSocketMemberSessionDAO webSocketMemberSessionDAO;
    private final SessionManager sessionManager;
    private final MemberStatusEventProducer memberStatusEventProducer;

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String memberId = headerAccessor.getSessionAttributes().get("AUTHENTICATED_MEMBER_ID").toString();

        webSocketMemberSessionDAO.removeWebSocketSession(memberId);
        sessionManager.removeSession(memberId);
        memberStatusEventProducer.sendMemberStatusChangeEvent(memberId, false);
    }
}
