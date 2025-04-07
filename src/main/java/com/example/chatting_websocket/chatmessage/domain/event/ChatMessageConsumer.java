package com.example.chatting_websocket.chatmessage.domain.event;

public interface ChatMessageConsumer {


    public void receiveChatMessageEvent(String message);
}
