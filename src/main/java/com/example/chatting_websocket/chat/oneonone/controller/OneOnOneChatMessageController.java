package com.example.chatting_websocket.chat.oneonone.controller;

import com.example.chatting_websocket.chat.oneonone.application.OneOnOneChatMessageService;
import com.example.chatting_websocket.chat.oneonone.controller.dto.OneOnOneChatMessageRequest;
import com.example.chatting_websocket.chat.shared.service.ChatMessageValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class OneOnOneChatMessageController {
    private final OneOnOneChatMessageService oneOnOneChatMessageService;
    private final ChatMessageValidationService validationService;

    @MessageMapping("/v1/chatMessage/oneOnOne")
    @SendTo("/topic/chatMessage/oneOnOne")
    public void sendChatMessage(OneOnOneChatMessageRequest chatMessageRequest, SimpMessageHeaderAccessor headerAccessor) {
        validationService.validate(chatMessageRequest);


        String roomId = chatMessageRequest.getRoomId();
        String senderId = headerAccessor.getSessionAttributes().get("AUTHENTICATED_MEMBER_ID").toString();
        String content = chatMessageRequest.getContent();
        oneOnOneChatMessageService.sendOneOnOneChatMessage(roomId, senderId, content);
    }
}
