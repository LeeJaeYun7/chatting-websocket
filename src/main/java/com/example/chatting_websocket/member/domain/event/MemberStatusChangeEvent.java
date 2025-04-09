package com.example.chatting_websocket.member.domain.event;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberStatusChangeEvent {

    private String memberId;
    private boolean isOnline;

    @Builder
    public MemberStatusChangeEvent(String memberId, boolean isOnline) {
        this.memberId = memberId;
        this.isOnline = isOnline;
    }

    public static MemberStatusChangeEvent of(String memberId, boolean isOnline) {
        return MemberStatusChangeEvent.builder()
                .memberId(memberId)
                .isOnline(isOnline)
                .build();
    }
}
