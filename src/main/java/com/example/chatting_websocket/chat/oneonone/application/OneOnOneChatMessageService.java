package com.example.chatting_websocket.chat.oneonone.application;

import com.example.chatting_websocket.chat.oneonone.domain.event.OneOnOneChatMessageProducer;
import com.example.chatting_websocket.chat.oneonone.domain.event.OneOnOneChatMessageServerEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OneOnOneChatMessageService {
    private final OneOnOneChatMessageProducer oneOnOneChatMessageProducer;

    public void sendOneOnOneChatMessage(String roomId, String senderId, String content) {
        OneOnOneChatMessageServerEvent event = OneOnOneChatMessageServerEvent.of(roomId, senderId, content);
        oneOnOneChatMessageProducer.sendOneOnOneChatMessage(event);
    }
}

