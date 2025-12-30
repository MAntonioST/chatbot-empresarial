package com.techcorp.chatbot.service;

import com.techcorp.chatbot.dto.ChatRequest;
import com.techcorp.chatbot.dto.ChatResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatbotService {
    
    private final ChatClient.Builder chatClientBuilder;
    
    private static final String SYSTEM_PROMPT = """
        Você é um assistente virtual da TechCorp, uma empresa de tecnologia.
        
        Suas responsabilidades:
        - Responder dúvidas sobre tecnologia e desenvolvimento
        - Ser profissional, claro e objetivo
        - Usar exemplos práticos quando apropriado
        - Sempre responder em português brasileiro
        
        Regras:
        1. Seja conciso (máximo 3 parágrafos)
        2. Se não souber, admita e sugira alternativas
        3. Evite jargões desnecessários
        """;
    
    public ChatResponse chat(ChatRequest request) {
        log.debug("Processando mensagem: {}", request.message());
        
        try {
            String sessionId = request.sessionId() != null 
                ? request.sessionId() 
                : UUID.randomUUID().toString();
            
            ChatClient chatClient = chatClientBuilder.build();
            
            // Usar nome completo da classe para evitar conflito
            org.springframework.ai.chat.model.ChatResponse aiResponse = chatClient.prompt()
                    .system(SYSTEM_PROMPT)
                    .user(request.message())
                    .call()
                    .chatResponse();
            
            String responseText = aiResponse.getResult().getOutput().getContent();
            Integer tokensUsed = Math.toIntExact(aiResponse.getMetadata().getUsage().getTotalTokens());
            
            log.info("Resposta gerada. Tokens usados: {}", tokensUsed);
            
            return ChatResponse.of(responseText, sessionId, tokensUsed);
            
        } catch (Exception e) {
            log.error("Erro ao processar chat", e);
            throw new RuntimeException("Erro ao processar mensagem: " + e.getMessage());
        }
    }
}
