package com.example.chatting_websocket.chat.oneonone.domain.event;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class OneOnOneChatMessageEvent {

    private String roomId;    // 채팅방 ID
    private String senderId;    // 메시지를 보낸 사용자 ID
    private String receiverId; // 메시지를 받은 사용자 ID
    private String content;   // 메시지 내용
    private LocalDateTime timestamp; // 메시지 보낸 시각

    @Builder
    public OneOnOneChatMessageEvent(String roomId, String senderId, String receiverId, String content, LocalDateTime timestamp) {
        this.roomId = roomId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.timestamp = timestamp;
    }

    public static OneOnOneChatMessageEvent of(String roomId, String senderId, String receiverId, String content, LocalDateTime timestamp) {
        return OneOnOneChatMessageEvent.builder()
                                       .roomId(roomId)
                                       .senderId(senderId)
                                       .receiverId(receiverId)
                                       .content(content)
                                       .timestamp(timestamp)
                                       .build();
    }
}
