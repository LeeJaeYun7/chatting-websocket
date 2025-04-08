package com.example.chatting_websocket.chat.oneonone.application.feign;

import com.example.chatting_websocket.chat.oneonone.controller.dto.OneOnOneChatMessageServerRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name= "oneOnOneChatMessageClient", url = "${chatting.server.url}")
public interface OneOnOneChatMessageClient {
    @PostMapping("/api/v1/chatMessage/oneOnOne")
    void sendOneOnOneChatMessage(@RequestBody OneOnOneChatMessageServerRequest request);
}