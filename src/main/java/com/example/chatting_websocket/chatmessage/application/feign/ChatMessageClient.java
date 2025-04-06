package com.example.chatting_websocket.chatmessage.application.feign;

import com.example.chatting_websocket.chatmessage.controller.dto.ChatMessageServerRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name= "chatMessageClient", url = "${chatting.server.url}")
public interface ChatMessageClient {
    @PostMapping("/api/v1/chatMessage")
    void sendChatMessage(@RequestBody ChatMessageServerRequest request, @RequestHeader("Authorization") String authorizationHeader);
}