package com.example.chatting_websocket.member.domain.event;

public interface MemberStatusEventProducer {

    void sendMemberStatusChangeEvent(String memberId, boolean isOnline);
}
