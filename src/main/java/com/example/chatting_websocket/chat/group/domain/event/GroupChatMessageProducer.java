package com.example.chatting_websocket.chat.group.domain.event;

public interface GroupChatMessageProducer {
    void sendGroupChatMessage(GroupChatMessageServerEvent event);
}
