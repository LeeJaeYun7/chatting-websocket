package com.example.chatting_websocket.chat.group.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class GroupChatMessageResponse {

    private String roomId;
    private String senderId;
    private String content;
    private LocalDateTime timestamp;

    @Builder
    public GroupChatMessageResponse(String roomId, String senderId, String content, LocalDateTime timestamp) {
        this.roomId = roomId;
        this.senderId = senderId;
        this.content = content;
        this.timestamp = timestamp;
    }

    public static GroupChatMessageResponse of(String roomId, String senderId, String content, LocalDateTime timestamp) {
        return GroupChatMessageResponse.builder()
                                       .roomId(roomId)
                                       .senderId(senderId)
                                       .content(content)
                                       .timestamp(timestamp)
                                       .build();
        }
}
