package com.example.chatting_websocket.chat.shared.validator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.example.chatting_websocket.chat.oneonone.controller.dto.OneOnOneChatMessageRequest;

@Component
public class OneOnOneChatMessageValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return OneOnOneChatMessageRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        OneOnOneChatMessageRequest request = (OneOnOneChatMessageRequest) target;

        // 메시지가 비어있거나 1000자 이상일 경우
        if (request.getContent() == null || request.getContent().trim().isEmpty()) {
            errors.rejectValue("content", "empty", "메시지는 비어 있을 수 없습니다.");
        }

        if (request.getContent() != null && request.getContent().length() > 1000) {
            errors.rejectValue("content", "tooLong", "메시지는 1000자를 넘을 수 없습니다.");
        }

        // URL 포함 여부 확인
        if (request.getContent() != null && request.getContent().matches(".*https?://.*")) {
            errors.rejectValue("content", "invalid", "메시지에 URL을 포함할 수 없습니다.");
        }

        // HTML 태그 포함 여부 확인
        if (request.getContent() != null && request.getContent().matches(".*<.*>.*")) {
            errors.rejectValue("content", "invalid", "메시지에 HTML 태그를 포함할 수 없습니다.");
        }

        if (request.getContent() != null && !request.getContent().matches("^[\\p{L}\\p{N}\\p{Zs}\\p{P}~!]+$")) {
            errors.rejectValue("content", "invalid", "메시지는 텍스트만 포함할 수 있습니다.");
        }
    }
}
