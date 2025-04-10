package com.example.chatting_websocket.websocket.application;

import com.example.chatting_websocket.chat.group.controller.dto.GroupChatMessageResponse;
import com.example.chatting_websocket.chat.oneonone.controller.dto.OneOnOneChatMessageResponse;
import com.example.chatting_websocket.member.controller.dto.MemberStatusNotificationResponse;
import com.example.chatting_websocket.websocket.infrastructure.enums.WebSocketTopics;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebSocketClientMessageSender {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendOneOnOneChatMessageToClient(String sessionId, OneOnOneChatMessageResponse response) {
        messagingTemplate.convertAndSendToUser(sessionId, WebSocketTopics.ONE_ON_ONE_CHAT_MESSAGE, response);
    }

    public void sendGroupChatMessageToClient(String sessionId, GroupChatMessageResponse response) {
        messagingTemplate.convertAndSendToUser(sessionId, WebSocketTopics.GROUP_CHAT_MESSAGE, response);
    }

    public void sendMemberStatusNotificationToClient(String sessionId, MemberStatusNotificationResponse response) {
        messagingTemplate.convertAndSendToUser(sessionId, WebSocketTopics.MEMBER_STATUS, response);
    }
}
