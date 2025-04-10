package com.example.chatting_websocket.chat.group.application;

import com.example.chatting_websocket.chat.group.domain.event.GroupChatMessageServerEvent;
import com.example.chatting_websocket.chat.group.domain.event.GroupChatMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupChatMessageService {

    private final GroupChatMessageProducer groupChatMessageProducer;

    public void sendGroupChatMessage(String roomId, String senderId, String content) {
        GroupChatMessageServerEvent event = GroupChatMessageServerEvent.of(roomId, senderId, content);
        groupChatMessageProducer.sendGroupChatMessage(event);
    }
}

