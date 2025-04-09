package com.example.chatting_websocket.chat.oneonone.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class OneOnOneChatMessageResponse {

    private String roomId;
    private String senderId;
    private String receiverId;
    private String content;
    private LocalDateTime timestamp;

    @Builder
    public OneOnOneChatMessageResponse(String roomId, String senderId, String receiverId, String content, LocalDateTime timestamp) {
        this.roomId = roomId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.timestamp = timestamp;
    }

    public static OneOnOneChatMessageResponse of(String roomId, String senderId, String receiverId, String content, LocalDateTime timestamp) {
        return OneOnOneChatMessageResponse.builder()
                                          .roomId(roomId)
                                          .senderId(senderId)
                                          .receiverId(receiverId)
                                          .content(content)
                                          .timestamp(timestamp)
                                          .build();
    }
}
