package com.example.chatting_websocket.websocket.infrastructure;

import com.example.chatting_websocket.chat.group.controller.dto.GroupChatMessageResponse;
import com.example.chatting_websocket.chat.oneonone.controller.dto.OneOnOneChatMessageResponse;
import com.example.chatting_websocket.websocket.infrastructure.enums.WebSocketInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebSocketClientMessageSender {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendOneOnOneChatMessageToClient(String sessionId, OneOnOneChatMessageResponse response) {
        messagingTemplate.convertAndSendToUser(sessionId, WebSocketInfo.ONE_ON_ONE_CHAT_MESSAGE_DESTINATION, response);
    }

    public void sendGroupChatMessageToClient(String sessionId, GroupChatMessageResponse response) {
        messagingTemplate.convertAndSendToUser(sessionId, WebSocketInfo.GROUP_CHAT_MESSAGE_DESTINATION, response);
    }
}
