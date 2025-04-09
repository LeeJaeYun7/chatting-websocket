package com.example.chatting_websocket.chat.group.infrastructure.kafka;

import com.example.chatting_websocket.chat.group.controller.dto.GroupChatMessageResponse;
import com.example.chatting_websocket.chat.group.domain.event.GroupChatMessageConsumer;
import com.example.chatting_websocket.chat.group.domain.event.GroupChatMessageEvent;
import com.example.chatting_websocket.chat.group.domain.event.enums.GroupChatMessageConst;
import com.example.chatting_websocket.shared.utils.JsonConverter;
import com.example.chatting_websocket.websocket.infrastructure.SessionManager;
import com.example.chatting_websocket.websocket.infrastructure.WebSocketClientMessageSender;
import com.example.chatting_websocket.websocket.infrastructure.redis.WebSocketMemberSessionDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GroupChatMessageConsumerImpl implements GroupChatMessageConsumer {

    private final JsonConverter jsonConverter;
    private final WebSocketMemberSessionDAO webSocketMemberSessionDAO;
    private final WebSocketClientMessageSender webSocketClientMessageSender;
    private final SessionManager sessionManager;

    @KafkaListener(
            topics = GroupChatMessageConst.CHAT_MESSAGE_TOPIC,
            groupId = "websocket-consumer-group"
    )
    public void receiveGroupChatMessage(String message) {
        GroupChatMessageEvent event = jsonConverter.convertFromJson(message, GroupChatMessageEvent.class);
        GroupChatMessageResponse response = GroupChatMessageResponse.of(event.getRoomId(), event.getSenderId(), event.getContent(), event.getTimestamp());

        List<Long> participantIds = event.getParticipantIds()
                                         .stream()
                                         .map(Long::parseLong)  // String을 Long으로 변환
                                         .toList();

        for(long participantId: participantIds){
            String sessionId = webSocketMemberSessionDAO.getWebSocketSession(String.valueOf(participantId));
            if(sessionManager.containsSession(sessionId)){
                webSocketClientMessageSender.sendGroupChatMessageToClient(sessionId, response);
            }
        }

    }
}
