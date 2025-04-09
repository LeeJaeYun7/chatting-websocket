package com.example.chatting_websocket.websocket.domain.event;

import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Builder;

@Getter
@NoArgsConstructor
public class MemberStatusEvent {

    private String memberId;
    private boolean isOnline;

    @Builder
    public MemberStatusEvent(String memberId, boolean isOnline) {
        this.memberId = memberId;
        this.isOnline = isOnline;
    }

    public static MemberStatusEvent of(String memberId, boolean isOnline) {
        return MemberStatusEvent.builder()
                .memberId(memberId)
                .isOnline(isOnline)
                .build();
    }
}
