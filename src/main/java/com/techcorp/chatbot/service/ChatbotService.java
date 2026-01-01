package com.techcorp.chatbot.service;

import com.techcorp.chatbot.dto.ChatRequest;
import com.techcorp.chatbot.dto.ChatResponse;
import com.techcorp.chatbot.entity.Conversation;
import com.techcorp.chatbot.repository.ConversationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatbotService {
    
    private final ChatClient.Builder chatClientBuilder;
    private final ConversationRepository conversationRepository;
    
    private static final String SYSTEM_PROMPT = """
        Você é um assistente virtual da TechCorp, uma empresa de tecnologia.
        
        Suas responsabilidades:
        - Responder dúvidas sobre tecnologia e desenvolvimento
        - Ser profissional, claro e objetivo
        - Usar exemplos práticos quando apropriado
        - Sempre responder em português brasileiro
        - Manter contexto da conversa anterior
        
        Regras:
        1. Seja conciso (máximo 3 parágrafos)
        2. Se não souber, admita e sugira alternativas
        3. Evite jargões desnecessários
        4. Use o histórico da conversa para dar respostas contextualizadas
        """;
    
    private static final int MAX_HISTORY_MESSAGES = 10;
    
    @Transactional
    public ChatResponse chat(ChatRequest request) {
        log.info("🔵 INICIANDO processamento da mensagem");
        log.info("📝 Mensagem: {}", request.message());
        
        try {
            String sessionId = request.sessionId() != null 
                ? request.sessionId() 
                : UUID.randomUUID().toString();
            
            log.info("📋 SessionId: {}", sessionId);
            
            // Buscar histórico
            log.info("🔍 Buscando histórico no banco...");
            List<Conversation> history = conversationRepository
                .findLastNMessagesBySessionId(sessionId, MAX_HISTORY_MESSAGES);
            
            log.info("📚 Histórico encontrado: {} mensagens", history.size());
            
            // Construir mensagens com contexto
            List<Message> messages = new ArrayList<>();
            messages.add(new SystemMessage(SYSTEM_PROMPT));
            
            // Adicionar histórico (inverter ordem)
            for (int i = history.size() - 1; i >= 0; i--) {
                Conversation conv = history.get(i);
                if ("user".equals(conv.getRole())) {
                    messages.add(new UserMessage(conv.getContent()));
                } else {
                    messages.add(new AssistantMessage(conv.getContent()));
                }
            }
            
            // Nova mensagem
            messages.add(new UserMessage(request.message()));
            
            log.info("💬 Total de mensagens no contexto: {}", messages.size());
            
            // Chamar IA
            log.info("🤖 Chamando Groq API...");
            Prompt prompt = new Prompt(messages);
            ChatClient chatClient = chatClientBuilder.build();
            
            org.springframework.ai.chat.model.ChatResponse aiResponse = chatClient
                .prompt(prompt)
                .call()
                .chatResponse();
            
            String responseText = aiResponse.getResult().getOutput().getContent();
            Integer tokensUsed = Math.toIntExact(aiResponse.getMetadata().getUsage().getTotalTokens());
            
            log.info("✅ Resposta gerada. Tokens: {}", tokensUsed);
            
            // SALVAR NO BANCO
            log.info("💾 SALVANDO mensagem do usuário no banco...");
            saveConversation(sessionId, "user", request.message(), null);
            log.info("✅ Mensagem do usuário SALVA!");
            
            log.info("💾 SALVANDO resposta do assistente no banco...");
            saveConversation(sessionId, "assistant", responseText, tokensUsed);
            log.info("✅ Resposta do assistente SALVA!");
            
            // Verificar se foi salvo
            long totalMessages = conversationRepository.countBySessionId(sessionId);
            log.info("📊 Total de mensagens na sessão {}: {}", sessionId, totalMessages);
            
            return ChatResponse.of(responseText, sessionId, tokensUsed);
            
        } catch (Exception e) {
            log.error("❌ ERRO ao processar chat", e);
            throw new RuntimeException("Erro ao processar mensagem: " + e.getMessage());
        }
    }
    
    private void saveConversation(String sessionId, String role, String content, Integer tokensUsed) {
        try {
            log.info("  → Criando objeto Conversation...");
            Conversation conversation = Conversation.builder()
                .sessionId(sessionId)
                .role(role)
                .content(content)
                .tokensUsed(tokensUsed)
                .build();
            
            log.info("  → Salvando no repositório...");
            Conversation saved = conversationRepository.save(conversation);
            
            log.info("  → ✅ Salvo com ID: {} | Role: {} | Preview: {}", 
                saved.getId(), 
                role, 
                content.substring(0, Math.min(50, content.length())));
            
        } catch (Exception e) {
            log.error("  → ❌ ERRO ao salvar conversa: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    public List<Conversation> getHistory(String sessionId) {
        log.info("📖 Buscando histórico completo da sessão: {}", sessionId);
        List<Conversation> history = conversationRepository.findBySessionIdOrderByTimestampAsc(sessionId);
        log.info("📖 Encontradas {} mensagens", history.size());
        return history;
    }
    
    @Transactional
    public void clearHistory(String sessionId) {
        log.info("🗑️  Deletando histórico da sessão: {}", sessionId);
        conversationRepository.deleteBySessionId(sessionId);
        log.info("✅ Histórico deletado!");
    }
    
    public long countMessages(String sessionId) {
        long count = conversationRepository.countBySessionId(sessionId);
        log.info("🔢 Sessão {} tem {} mensagens", sessionId, count);
        return count;
    }
}
