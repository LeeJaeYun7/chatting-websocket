package com.example.chatting_websocket.chat.oneonone.infrastructure.kafka;

import com.example.chatting_websocket.chat.oneonone.controller.dto.OneOnOneChatMessageResponse;
import com.example.chatting_websocket.chat.oneonone.domain.event.OneOnOneChatMessageConsumer;
import com.example.chatting_websocket.chat.oneonone.domain.event.OneOnOneChatMessageEvent;
import com.example.chatting_websocket.chat.oneonone.domain.event.enums.OneOnOneChatMessageConst;
import com.example.chatting_websocket.shared.utils.JsonConverter;
import com.example.chatting_websocket.websocket.infrastructure.SessionManager;
import com.example.chatting_websocket.websocket.infrastructure.WebSocketClientMessageSender;
import com.example.chatting_websocket.websocket.infrastructure.dao.WebSocketMemberSessionDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OneOnOneChatMessageConsumerImpl implements OneOnOneChatMessageConsumer {

    private final JsonConverter jsonConverter;
    private final WebSocketMemberSessionDAO webSocketMemberSessionDAO;
    private final WebSocketClientMessageSender webSocketClientMessageSender;

    @KafkaListener(
            topics = OneOnOneChatMessageConst.CHAT_MESSAGE_TOPIC,
            groupId = "websocket-consumer-group",
            topicPartitions = @TopicPartition(
                    topic = OneOnOneChatMessageConst.CHAT_MESSAGE_TOPIC,
                    partitions = "0"
            )
    )
    public void receiveOneOnOneChatMessage(String message) {
        OneOnOneChatMessageEvent event = jsonConverter.convertFromJson(message, OneOnOneChatMessageEvent.class);

        OneOnOneChatMessageResponse response = OneOnOneChatMessageResponse.of(event.getRoomId(), event.getSenderId(), event.getReceiverId(), event.getContent(), event.getTimestamp());

        String memberId = event.getReceiverId();
        String sessionId = webSocketMemberSessionDAO.getWebSocketSession(memberId);
        webSocketClientMessageSender.sendOneOnOneChatMessageToClient(sessionId, response);
    }
}
