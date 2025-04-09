package com.example.chatting_websocket.chat.oneonone.domain.event;

public interface OneOnOneChatMessageProducer {
    void sendOneOnOneChatMessage(OneOnOneChatMessageServerEvent event);
}
