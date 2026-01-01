package com.techcorp.chatbot.controller;

import com.techcorp.chatbot.dto.ChatRequest;
import com.techcorp.chatbot.dto.ChatResponse;
import com.techcorp.chatbot.dto.ConversationHistoryResponse;
import com.techcorp.chatbot.entity.Conversation;
import com.techcorp.chatbot.service.ChatbotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
@Slf4j
public class ChatController {
    
    private final ChatbotService chatbotService;
    
    /**
     * Health check
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Chatbot está funcionando! 🤖");
    }
    
    /**
     * Endpoint principal de chat
     */
    @PostMapping
    public ResponseEntity<ChatResponse> chat(@Valid @RequestBody ChatRequest request) {
        log.info("Recebida mensagem: {} (sessionId: {})", 
            request.message(), request.sessionId());
        
        ChatResponse response = chatbotService.chat(request);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Buscar histórico de uma sessão
     */
    @GetMapping("/sessions/{sessionId}/history")
    public ResponseEntity<ConversationHistoryResponse> getHistory(
            @PathVariable String sessionId) {
        
        log.info("Buscando histórico da sessão: {}", sessionId);
        
        List<Conversation> history = chatbotService.getHistory(sessionId);
        ConversationHistoryResponse response = ConversationHistoryResponse.from(sessionId, history);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Deletar histórico de uma sessão
     */
    @DeleteMapping("/sessions/{sessionId}/history")
    public ResponseEntity<Map<String, String>> clearHistory(
            @PathVariable String sessionId) {
        
        log.info("Deletando histórico da sessão: {}", sessionId);
        
        chatbotService.clearHistory(sessionId);
        
        return ResponseEntity.ok(Map.of(
            "message", "Histórico deletado com sucesso",
            "sessionId", sessionId
        ));
    }
    
    /**
     * Contar mensagens de uma sessão
     */
    @GetMapping("/sessions/{sessionId}/count")
    public ResponseEntity<Map<String, Object>> countMessages(
            @PathVariable String sessionId) {
        
        long count = chatbotService.countMessages(sessionId);
        
        return ResponseEntity.ok(Map.of(
            "sessionId", sessionId,
            "messageCount", count
        ));
    }
}
