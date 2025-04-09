package com.example.chatting_websocket.websocket.infrastructure.enums;

public interface WebSocketInfo {
    String ONE_ON_ONE_CHAT_MESSAGE_DESTINATION = "/topic/chatMessage/oneOnOne";
    String GROUP_CHAT_MESSAGE_DESTINATION = "/topic/chatMessage/group";

    String MEMBER_STATUS_DESTINATION = "/topic/memberStatus";
}
