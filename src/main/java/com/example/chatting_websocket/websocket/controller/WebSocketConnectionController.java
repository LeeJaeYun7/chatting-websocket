package com.example.chatting_websocket.websocket.controller;

import com.example.chatting_websocket.websocket.controller.dto.response.WebSocketResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Slf4j
public class WebSocketConnectionController {
    @MessageMapping("/v1/websocket/connect")
    @SendTo("/topic/websocket/connect")
    public WebSocketResponse connect(SimpMessageHeaderAccessor headerAccessor) {

        Principal principal = headerAccessor.getUser();
        String sessionId = principal.getName();

        return WebSocketResponse.of(true);
    }
}
