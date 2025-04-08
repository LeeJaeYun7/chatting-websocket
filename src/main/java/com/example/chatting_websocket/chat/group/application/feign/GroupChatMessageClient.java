package com.example.chatting_websocket.chat.group.application.feign;

import com.example.chatting_websocket.chat.group.controller.dto.GroupChatMessageServerRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name= "groupChatMessageClient", url = "${chatting.server.url}")
public interface GroupChatMessageClient {

    @PostMapping("/api/v1/chatMessage/group")
    void sendGroupChatMessage(@RequestBody GroupChatMessageServerRequest request);
}