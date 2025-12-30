package com.techcorp.chatbot.dto;


import jakarta.validation.constraints.NotBlank;

public record ChatRequest(
        @NotBlank(message = "Mensagem não pode estar vazia")
        String message,

        String sessionId
) {
    public ChatRequest {
        if (message != null) {
            message = message.trim();
        }
    }
}

