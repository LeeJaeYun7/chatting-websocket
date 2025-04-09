package com.example.chatting_websocket.websocket.infrastructure.enums;

public interface WebSocketTopics {
    String ONE_ON_ONE_CHAT_MESSAGE = "/topic/chatMessage/oneOnOne";
    String GROUP_CHAT_MESSAGE = "/topic/chatMessage/group";

    String MEMBER_STATUS = "/topic/memberStatus";
}
