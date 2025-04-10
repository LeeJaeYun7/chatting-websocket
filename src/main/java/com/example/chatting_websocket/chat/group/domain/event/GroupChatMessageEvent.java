package com.example.chatting_websocket.chat.group.domain.event;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class GroupChatMessageEvent {

    private String roomId;    // 채팅방 ID
    private String senderId;    // 메시지를 보낸 사용자 ID
    private List<String> participantIds; // 채팅방 참여자 ID
    private String content;   // 메시지 내용
    private LocalDateTime timestamp; // 메시지 보낸 시각

    @Builder
    public GroupChatMessageEvent(String roomId, String senderId, List<String> participantIds, String content, LocalDateTime timestamp) {
        this.roomId = roomId;
        this.senderId = senderId;
        this.participantIds = participantIds;
        this.content = content;
        this.timestamp = timestamp;
    }

    public static GroupChatMessageEvent of(String roomId, String senderId, List<String> participantIds, String content, LocalDateTime timestamp) {
        return GroupChatMessageEvent.builder()
                                    .roomId(roomId)
                                    .senderId(senderId)
                                    .participantIds(participantIds)
                                    .content(content)
                                    .timestamp(timestamp)
                                    .build();
    }
}
