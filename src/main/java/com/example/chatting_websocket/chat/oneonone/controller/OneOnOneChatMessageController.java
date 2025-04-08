package com.example.chatting_websocket.chat.oneonone.controller;

import com.example.chatting_websocket.chat.oneonone.application.OneOnOneChatMessageService;
import com.example.chatting_websocket.chat.oneonone.controller.dto.OneOnOneChatMessageRequest;
import com.example.chatting_websocket.shared.exceptions.CustomException;
import com.example.chatting_websocket.shared.exceptions.CustomExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OneOnOneChatMessageController {
    private final OneOnOneChatMessageService oneOnOneChatMessageService;

    @MessageMapping("/v1/chatMessage/oneOnOne")
    @SendTo("/topic/chatMessage/oneOnOne")
    public void sendChatMessage(OneOnOneChatMessageRequest chatMessageRequest, SimpMessageHeaderAccessor headerAccessor) {
        if (!chatMessageRequest.isContentLengthValid()) {
            throw new CustomException(CustomExceptionType.CHATMESSAGE_TOO_LONG);
        }
        System.out.println("sendChatMessage 실행!");

        String roomId = chatMessageRequest.getRoomId();
        String senderId = headerAccessor.getSessionAttributes().get("AUTHENTICATED_MEMBER_ID").toString();
        String content = chatMessageRequest.getContent();
        oneOnOneChatMessageService.sendOneOnOneChatMessage(roomId, senderId, content);
    }
}
