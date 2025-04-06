package com.example.chatting_websocket.chatmessage.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatMessageServerRequest {

    private String roomUuid;     // 어떤 채팅방에 보낸 건지
    private String senderUuid;
    private String receiverUuid;
    private String content;    // 메시지 내용

    @Builder
    public ChatMessageServerRequest(String roomUuid, String senderUuid, String receiverUuid, String content) {
        this.roomUuid = roomUuid;
        this.senderUuid = senderUuid;
        this.receiverUuid = receiverUuid;
        this.content = content;
    }

    public static ChatMessageServerRequest of(String roomUuid, String senderUuid, String receiverUuid, String content){
        return ChatMessageServerRequest.builder()
                                       .roomUuid(roomUuid)
                                       .senderUuid(senderUuid)
                                       .receiverUuid(receiverUuid)
                                       .content(content)
                                       .build();
    }
}
