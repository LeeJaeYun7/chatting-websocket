package com.example.chatting_websocket.chatmessage.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatMessageRequest {

    private String roomUuid;     // 어떤 채팅방에 보낸 건지
    private String receiverUuid; // 메시지 보내는 대상
    private String content;    // 메시지 내용
    private String jwtToken;   // jwt 토큰

    @Builder
    public ChatMessageRequest(String roomUuid, String receiverUuid, String content, String jwtToken) {
        this.roomUuid = roomUuid;
        this.receiverUuid = receiverUuid;
        this.content = content;
        this.jwtToken = jwtToken;
    }

    public boolean isContentLengthValid() {
        return content != null && content.length() <= 1000;
    }
}
