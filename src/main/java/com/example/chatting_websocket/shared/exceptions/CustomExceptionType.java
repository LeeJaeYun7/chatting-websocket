package com.example.chatting_websocket.shared.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CustomExceptionType {
    CHATMESSAGE_TOO_LONG(HttpStatus.BAD_REQUEST, "메시지 길이는 1000자 이내여야 합니다.");

    private final HttpStatus httpStatus;
    private final String message;

    CustomExceptionType(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

}
