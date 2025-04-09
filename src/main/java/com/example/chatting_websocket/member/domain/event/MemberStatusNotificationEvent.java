package com.example.chatting_websocket.member.domain.event;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MemberStatusNotificationEvent {

    private String memberId;
    private List<String> friendIds;
    private boolean isOnline;

    @Builder
    public MemberStatusNotificationEvent(String memberId, List<String> friendIds, boolean isOnline) {
        this.memberId = memberId;
        this.friendIds = friendIds;
        this.isOnline = isOnline;
    }

    // of 메소드
    public static MemberStatusNotificationEvent of(String memberId, List<String> friendIds, boolean isOnline) {
        return MemberStatusNotificationEvent.builder()
                .memberId(memberId)
                .friendIds(friendIds)
                .isOnline(isOnline)
                .build();
    }
}
