package com.example.chatting_websocket.chatmessage.controller;

import com.example.chatting_websocket.chatmessage.controller.dto.ChatMessageRequest;
import com.example.chatting_websocket.chatmessage.application.ChatMessageService;
import com.example.chatting_websocket.commons.exceptions.CustomException;
import com.example.chatting_websocket.commons.exceptions.CustomExceptionType;
import com.example.chatting_websocket.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;
    private final JwtProvider jwtProvider;

    @MessageMapping("/v1/chatMessage")
    @SendTo("/topic/chatMessage")
    public void sendChatMessage(ChatMessageRequest chatMessageRequest) {
        if (!chatMessageRequest.isContentLengthValid()) {
            throw new CustomException(CustomExceptionType.CHATMESSAGE_TOO_LONG);
        }
        String roomUuid = chatMessageRequest.getRoomUuid();

        String jwtToken = chatMessageRequest.getJwtToken();
        String senderUuid = jwtProvider.getMemberUuid(jwtToken);
        String receiverUuid = chatMessageRequest.getReceiverUuid();

        String content = chatMessageRequest.getContent();
        chatMessageService.sendChatMessage(roomUuid, senderUuid, receiverUuid, content, jwtToken);
    }
}
