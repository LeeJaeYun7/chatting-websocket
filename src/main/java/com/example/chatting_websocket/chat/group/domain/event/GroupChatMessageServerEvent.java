package com.example.chatting_websocket.chat.group.domain.event;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GroupChatMessageServerEvent {

    private String roomId;     // 어떤 채팅방에 보낸 건지
    private String senderId;
    private String content;    // 메시지 내용

    @Builder
    public GroupChatMessageServerEvent(String roomId, String senderId, String content) {
        this.roomId = roomId;
        this.senderId = senderId;
        this.content = content;
    }

    public static GroupChatMessageServerEvent of(String roomId, String senderId, String content){
        return GroupChatMessageServerEvent.builder()
                                          .roomId(roomId)
                                          .senderId(senderId)
                                          .content(content)
                                          .build();
    }
}
