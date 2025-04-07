package com.example.chatting_websocket.chatmessage.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatMessageRequest {

    private String roomId;     // 어떤 채팅방에 보낸 건지
    private String receiverId; // 메시지 보내는 대상
    private String content;    // 메시지 내용

    @Builder
    public ChatMessageRequest(String roomId, String receiverId, String content) {
        this.roomId = roomId;
        this.receiverId = receiverId;
        this.content = content;
    }

    public boolean isContentLengthValid() {
        return content != null && content.length() <= 1000;
    }
}
