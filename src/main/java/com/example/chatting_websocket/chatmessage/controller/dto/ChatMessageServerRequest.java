package com.example.chatting_websocket.chatmessage.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatMessageServerRequest {

    private String roomId;     // 어떤 채팅방에 보낸 건지
    private String senderId;
    private String receiverId;
    private String content;    // 메시지 내용

    @Builder
    public ChatMessageServerRequest(String roomId, String senderId, String receiverId, String content) {
        this.roomId = roomId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
    }

    public static ChatMessageServerRequest of(String roomId, String senderId, String receiverId, String content){
        return ChatMessageServerRequest.builder()
                                       .roomId(roomId)
                                       .senderId(senderId)
                                       .receiverId(receiverId)
                                       .content(content)
                                       .build();
    }
}
