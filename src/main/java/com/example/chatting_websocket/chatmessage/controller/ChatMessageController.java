package com.example.chatting_websocket.chatmessage.controller;

import com.example.chatting_websocket.chatmessage.controller.dto.ChatMessageRequest;
import com.example.chatting_websocket.chatmessage.application.ChatMessageService;
import com.example.chatting_websocket.shared.exceptions.CustomException;
import com.example.chatting_websocket.shared.exceptions.CustomExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatMessageController {
    private final ChatMessageService chatMessageService;
    @MessageMapping("/v1/chatMessage")
    @SendTo("/topic/chatMessage")
    public void sendChatMessage(ChatMessageRequest chatMessageRequest, SimpMessageHeaderAccessor headerAccessor) {
        if (!chatMessageRequest.isContentLengthValid()) {
            throw new CustomException(CustomExceptionType.CHATMESSAGE_TOO_LONG);
        }
        String roomId = chatMessageRequest.getRoomId();
        String senderId = headerAccessor.getSessionAttributes().get("AUTHENTICATED_MEMBER_ID").toString();
        String receiverId = chatMessageRequest.getReceiverId();

        String content = chatMessageRequest.getContent();
        chatMessageService.sendChatMessage(roomId, senderId, receiverId, content);
    }
}
