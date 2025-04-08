package com.example.chatting_websocket.chat.group.domain.event;

public interface GroupChatMessageConsumer {

    public void receiveGroupChatMessage(String message);
}
