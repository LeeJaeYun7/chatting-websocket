package com.example.chatting_websocket.member.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;

@NoArgsConstructor
@Getter
public class MemberStatusNotificationResponse {

    private String memberId;
    private boolean isOnline;

    // 빌더 패턴을 위한 생성자
    @Builder
    public MemberStatusNotificationResponse(String memberId, boolean isOnline) {
        this.memberId = memberId;
        this.isOnline = isOnline;
    }

    // of 메소드
    public static MemberStatusNotificationResponse of(String memberId, boolean isOnline) {
        return MemberStatusNotificationResponse.builder()
                .memberId(memberId)
                .isOnline(isOnline)
                .build();
    }
}
