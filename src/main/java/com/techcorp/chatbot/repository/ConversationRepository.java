package com.techcorp.chatbot.repository;

import com.techcorp.chatbot.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    
    /**
     * Busca histórico de conversas por sessionId, ordenado por timestamp
     */
    List<Conversation> findBySessionIdOrderByTimestampAsc(String sessionId);
    
    /**
     * Busca últimas N mensagens de uma sessão
     */
    @Query(value = "SELECT * FROM conversations WHERE session_id = ?1 " +
           "ORDER BY timestamp DESC LIMIT ?2", nativeQuery = true)
    List<Conversation> findLastNMessagesBySessionId(String sessionId, int limit);
    
    /**
     * Deleta todas as conversas de uma sessão
     */
    void deleteBySessionId(String sessionId);
    
    /**
     * Conta mensagens de uma sessão
     */
    long countBySessionId(String sessionId);
}
