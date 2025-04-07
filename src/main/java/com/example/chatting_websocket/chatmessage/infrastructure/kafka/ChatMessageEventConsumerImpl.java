package com.example.chatting_websocket.chatmessage.infrastructure.kafka;

import com.example.chatting_websocket.chatmessage.controller.dto.ChatMessageResponse;
import com.example.chatting_websocket.chatmessage.domain.event.ChatMessageConsumer;
import com.example.chatting_websocket.chatmessage.domain.event.ChatMessageEvent;
import com.example.chatting_websocket.chatmessage.domain.event.enums.ChatMessageConst;
import com.example.chatting_websocket.shared.utils.JsonConverter;
import com.example.chatting_websocket.websocket.infrastructure.WebSocketClientMessageSender;
import com.example.chatting_websocket.websocket.infrastructure.dao.WebSocketSessionDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatMessageEventConsumerImpl implements ChatMessageConsumer {

    private final JsonConverter jsonConverter;
    private final WebSocketSessionDAO webSocketSessionDAO;
    private final WebSocketClientMessageSender webSocketClientMessageSender;

    @KafkaListener(
            topics = ChatMessageConst.CHAT_MESSAGE_TOPIC,
            groupId = "websocket-consumer-group",
            topicPartitions = @TopicPartition(
                    topic = ChatMessageConst.CHAT_MESSAGE_TOPIC,
                    partitions = "0"
            )
    )
    public void receiveChatMessageEvent(String message) {
        ChatMessageEvent event = jsonConverter.convertFromJson(message, ChatMessageEvent.class);

        ChatMessageResponse response = ChatMessageResponse.of(event.getRoomId(), event.getSenderId(), event.getReceiverId(), event.getContent(), event.getTimestamp());

        String memberId = event.getReceiverId();
        String sessionId = webSocketSessionDAO.getWebSocketSession(memberId);
        webSocketClientMessageSender.sendChatMessageToClient(sessionId, response);
    }
}
