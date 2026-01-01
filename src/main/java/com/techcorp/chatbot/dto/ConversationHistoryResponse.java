package com.techcorp.chatbot.dto;

import com.techcorp.chatbot.entity.Conversation;

import java.time.LocalDateTime;
import java.util.List;

public record ConversationHistoryResponse(
    String sessionId,
    long messageCount,
    List<MessageDto> messages
) {
    public record MessageDto(
        String role,
        String content,
        LocalDateTime timestamp,
        Integer tokensUsed
    ) {
        public static MessageDto from(Conversation conversation) {
            return new MessageDto(
                conversation.getRole(),
                conversation.getContent(),
                conversation.getTimestamp(),
                conversation.getTokensUsed()
            );
        }
    }
    
    public static ConversationHistoryResponse from(String sessionId, List<Conversation> conversations) {
        return new ConversationHistoryResponse(
            sessionId,
            conversations.size(),
            conversations.stream()
                .map(MessageDto::from)
                .toList()
        );
    }
}
