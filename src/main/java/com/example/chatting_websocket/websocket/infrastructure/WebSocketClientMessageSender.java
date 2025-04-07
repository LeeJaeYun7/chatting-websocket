package com.example.chatting_websocket.websocket.infrastructure;

import com.example.chatting_websocket.chatmessage.controller.dto.ChatMessageResponse;
import com.example.chatting_websocket.websocket.infrastructure.enums.WebSocketInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebSocketClientMessageSender {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendChatMessageToClient(String sessionId, ChatMessageResponse response) {
        messagingTemplate.convertAndSendToUser(sessionId, WebSocketInfo.CHAT_MESSAGE_DESTINATION, response);
    }
}
