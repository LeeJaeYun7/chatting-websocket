package com.example.chatting_websocket.shared.exceptions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getCustomExceptionType().getHttpStatus(),
                ex.getCustomExceptionType().getMessage()
        );

        return ResponseEntity.status(ex.getCustomExceptionType().getHttpStatus()).body(errorResponse);
    }
}
