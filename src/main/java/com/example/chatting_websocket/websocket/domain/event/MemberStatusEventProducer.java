package com.example.chatting_websocket.websocket.domain.event;

public interface MemberStatusEventProducer {

    void sendMemberStatusEvent(String memberId, boolean isOnline);
}
