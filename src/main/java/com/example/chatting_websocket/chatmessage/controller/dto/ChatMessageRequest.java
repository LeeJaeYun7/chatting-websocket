package com.example.chatting_websocket.chatmessage.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatMessageRequest {

    private long roomId;     // 어떤 채팅방에 보낸 건지
    private long receiverId; // 메시지 보내는 대상
    private String content;    // 메시지 내용
    private String jwtToken;   // jwt 토큰

    @Builder
    public ChatMessageRequest(long roomId, long receiverId, String content, String jwtToken) {
        this.roomId = roomId;
        this.receiverId = receiverId;
        this.content = content;
        this.jwtToken = jwtToken;
    }

    public boolean isContentLengthValid() {
        return content != null && content.length() <= 1000;
    }
}
