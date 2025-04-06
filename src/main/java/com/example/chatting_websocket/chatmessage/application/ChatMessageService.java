package com.example.chatting_websocket.chatmessage.application;

import com.example.chatting_websocket.chatmessage.controller.dto.ChatMessageServerRequest;
import com.example.chatting_websocket.chatmessage.application.feign.ChatMessageClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageClient chatMessageClient;

    public void sendChatMessage(String roomUuid, String senderUuid, String receiverUuid, String content, String jwtToken) {
        ChatMessageServerRequest request = ChatMessageServerRequest.of(roomUuid, senderUuid, receiverUuid, content);

        String authHeader = "Bearer " + jwtToken;
        chatMessageClient.sendChatMessage(request, authHeader);
    }
}

