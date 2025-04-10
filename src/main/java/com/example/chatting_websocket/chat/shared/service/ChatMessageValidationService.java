package com.example.chatting_websocket.chat.shared.service;

import com.example.chatting_websocket.chat.group.controller.dto.GroupChatMessageRequest;
import com.example.chatting_websocket.chat.oneonone.controller.dto.OneOnOneChatMessageRequest;
import com.example.chatting_websocket.chat.shared.validator.GroupChatMessageValidator;
import com.example.chatting_websocket.chat.shared.validator.OneOnOneChatMessageValidator;
import com.example.chatting_websocket.shared.exceptions.CustomException;
import com.example.chatting_websocket.shared.exceptions.CustomExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.stereotype.Service;

@Service
@Component
@RequiredArgsConstructor
public class ChatMessageValidationService {

    private final OneOnOneChatMessageValidator oneOnOneValidator;
    private final GroupChatMessageValidator groupValidator;

    public void validateOneOnOneChat(OneOnOneChatMessageRequest chatMessageRequest) {
        Errors errors = new BeanPropertyBindingResult(chatMessageRequest, "chatMessageRequest");
        oneOnOneValidator.validate(chatMessageRequest, errors);

        if (errors.hasErrors()) {
            String errorCode = errors.getFieldError("content").getCode(); // 예: "empty", "tooLong", "invalid"
            switch (errorCode) {
                case "empty":
                    throw new CustomException(CustomExceptionType.CHATMESSAGE_EMPTY);
                case "tooLong":
                    throw new CustomException(CustomExceptionType.CHATMESSAGE_TOO_LONG);
                case "invalid":
                    throw new CustomException(CustomExceptionType.CHATMESSAGE_INVALID);
            }
        }
    }

    public void validateGroupChat(GroupChatMessageRequest chatMessageRequest) {
        Errors errors = new BeanPropertyBindingResult(chatMessageRequest, "chatMessageRequest");
        groupValidator.validate(chatMessageRequest, errors);

        if (errors.hasErrors()) {
            String errorCode = errors.getFieldError("content").getCode(); // 예: "empty", "tooLong", "invalid"
            switch (errorCode) {
                case "empty":
                    throw new CustomException(CustomExceptionType.CHATMESSAGE_EMPTY);
                case "tooLong":
                    throw new CustomException(CustomExceptionType.CHATMESSAGE_TOO_LONG);
                case "invalid":
                    throw new CustomException(CustomExceptionType.CHATMESSAGE_INVALID);
            }
        }
    }
}
