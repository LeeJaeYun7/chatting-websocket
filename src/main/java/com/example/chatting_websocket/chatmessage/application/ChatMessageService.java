package com.example.chatting_websocket.chatmessage.application;

import com.example.chatting_websocket.chatmessage.controller.dto.ChatMessageServerRequest;
import com.example.chatting_websocket.chatmessage.application.feign.ChatMessageClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageClient chatMessageClient;

    public void sendChatMessage(String roomId, String senderId, String content, String roomType) {
        ChatMessageServerRequest request = ChatMessageServerRequest.of(roomId, senderId, content);

        if(roomType.equals("oneOnOne")){
            chatMessageClient.sendOneOnOneChatMessage(request);
        }

        chatMessageClient.sendGroupChatMessage(request);
    }
}

