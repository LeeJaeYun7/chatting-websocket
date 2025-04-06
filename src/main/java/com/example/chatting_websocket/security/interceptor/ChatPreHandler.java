package com.example.chatting_websocket.security.interceptor;

import com.example.chatting_websocket.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatPreHandler implements ChannelInterceptor {

    private final JwtProvider jwtProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        // ! 연결 요청시 JWT 검증
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String token = accessor.getFirstNativeHeader("Authorization");
            log.info("token = {}", token);
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                try {
                    jwtProvider.validateToken(token);
                } catch (Exception e) {
                    log.error("토큰 유효하지 않음 or 실패", e);
                    return null;
                }
            } else {
                log.error("유효한 토큰을 찾지 못함");
                return null;
            }
        }
        return message;
    }
}