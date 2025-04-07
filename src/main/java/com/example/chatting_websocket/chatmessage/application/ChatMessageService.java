package com.example.chatting_websocket.chatmessage.application;

import com.example.chatting_websocket.chatmessage.controller.dto.ChatMessageServerRequest;
import com.example.chatting_websocket.chatmessage.application.feign.ChatMessageClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageClient chatMessageClient;

    public void sendChatMessage(long roomId, long senderId, long receiverId, String content) {
        ChatMessageServerRequest request = ChatMessageServerRequest.of(roomId, senderId, receiverId, content);
        chatMessageClient.sendChatMessage(request);
    }
}

