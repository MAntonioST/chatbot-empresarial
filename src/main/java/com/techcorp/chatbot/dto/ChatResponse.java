package com.techcorp.chatbot.dto;

import java.time.LocalDateTime;

public record ChatResponse(
    String response,
    String sessionId,
    LocalDateTime timestamp,
    Integer tokensUsed
) {
    public static ChatResponse of(String response, String sessionId, Integer tokens) {
        return new ChatResponse(
            response,
            sessionId,
            LocalDateTime.now(),
            tokens
        );
    }
}
