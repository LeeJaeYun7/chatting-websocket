package com.example.chatting_websocket.chatmessage.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatMessageRequest {

    private String roomId;     // 어떤 채팅방에 보낸 건지
    private String content; // 메시지 내용
    private String roomType;    // 채팅방 타입

    @Builder
    public ChatMessageRequest(String roomId, String content, String roomType) {
        this.roomId = roomId;
        this.content = content;
        this.roomType = roomType;
    }

    public boolean isContentLengthValid() {
        return content != null && content.length() <= 1000;
    }
}
