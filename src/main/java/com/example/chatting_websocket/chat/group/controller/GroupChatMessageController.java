package com.example.chatting_websocket.chat.group.controller;

import com.example.chatting_websocket.chat.group.application.GroupChatMessageService;
import com.example.chatting_websocket.chat.group.controller.dto.GroupChatMessageRequest;
import com.example.chatting_websocket.chat.shared.service.ChatMessageValidationService;
import com.example.chatting_websocket.shared.exceptions.CustomException;
import com.example.chatting_websocket.shared.exceptions.CustomExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GroupChatMessageController {

    private final GroupChatMessageService groupChatMessageService;
    private final ChatMessageValidationService validationService;

    @MessageMapping("/v1/chatMessage/group")
    @SendTo("/topic/chatMessage/group")
    public void sendGroupChatMessage(GroupChatMessageRequest groupChatMessageRequest, SimpMessageHeaderAccessor headerAccessor) {
        validationService.validateGroupChat(groupChatMessageRequest);

        String roomId = groupChatMessageRequest.getRoomId();
        String senderId = headerAccessor.getSessionAttributes().get("AUTHENTICATED_MEMBER_ID").toString();
        String content = groupChatMessageRequest.getContent();

        groupChatMessageService.sendGroupChatMessage(roomId, senderId, content);
    }
}
