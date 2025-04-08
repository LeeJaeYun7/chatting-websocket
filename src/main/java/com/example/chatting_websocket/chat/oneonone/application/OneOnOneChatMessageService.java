package com.example.chatting_websocket.chat.oneonone.application;

import com.example.chatting_websocket.chat.oneonone.application.feign.OneOnOneChatMessageClient;
import com.example.chatting_websocket.chat.oneonone.controller.dto.OneOnOneChatMessageServerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OneOnOneChatMessageService {

    private final OneOnOneChatMessageClient oneOnOneChatMessageClient;

    public void sendOneOnOneChatMessage(String roomId, String senderId, String content) {
        OneOnOneChatMessageServerRequest request = OneOnOneChatMessageServerRequest.of(roomId, senderId, content);
        oneOnOneChatMessageClient.sendOneOnOneChatMessage(request);
    }
}

