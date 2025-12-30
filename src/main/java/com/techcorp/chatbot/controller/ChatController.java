package com.techcorp.chatbot.controller;

import com.techcorp.chatbot.dto.ChatRequest;
import com.techcorp.chatbot.dto.ChatResponse;
import com.techcorp.chatbot.service.ChatbotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ChatController {
    
    private final ChatbotService chatbotService;
    
    @PostMapping
    public ResponseEntity<ChatResponse> chat(@Valid @RequestBody ChatRequest request) {
        log.info("Recebida mensagem: {}", request.message());
        ChatResponse response = chatbotService.chat(request);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Chatbot está funcionando! 🤖");
    }
}
