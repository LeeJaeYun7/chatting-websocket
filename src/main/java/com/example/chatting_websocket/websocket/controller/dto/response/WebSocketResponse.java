package com.example.chatting_websocket.websocket.controller.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class WebSocketResponse {

    private boolean isSuccess;

    @Builder
    public WebSocketResponse(boolean isSuccess){
        this.isSuccess = isSuccess;
    }

    public static WebSocketResponse of(boolean isSuccess){
        return WebSocketResponse.builder()
                                .isSuccess(isSuccess)
                                .build();
    }
}
