package com.example.chatting_websocket.chat.oneonone.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OneOnOneChatMessageRequest {

    private String roomId;     // 어떤 채팅방에 보낸 건지
    private String content; // 메시지 내용

    @Builder
    public OneOnOneChatMessageRequest(String roomId, String content) {
        this.roomId = roomId;
        this.content = content;
    }

    public boolean isContentLengthValid() {
        return content != null && content.length() <= 1000;
    }
}
