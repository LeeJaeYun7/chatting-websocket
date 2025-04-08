package com.example.chatting_websocket.chat.group.application;

import com.example.chatting_websocket.chat.group.application.feign.GroupChatMessageClient;
import com.example.chatting_websocket.chat.group.controller.dto.GroupChatMessageServerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupChatMessageService {

    private final GroupChatMessageClient chatMessageClient;

    public void sendGroupChatMessage(String roomId, String senderId, String content) {
        GroupChatMessageServerRequest request = GroupChatMessageServerRequest.of(roomId, senderId, content);
        chatMessageClient.sendGroupChatMessage(request);
    }
}

