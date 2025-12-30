package com.techcorp.chatbot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClient;

@Configuration
@Slf4j
public class GroqConfig {
    
    @Value("${groq.api.key}")
    private String groqApiKey;
    
    @Bean
    @Primary
    public OpenAiApi groqOpenAiApi() {
        // URL base APENAS até /openai (sem /v1)
        // O OpenAiApi adiciona /v1 automaticamente
        String baseUrl = "https://api.groq.com/openai";
        
        log.info("🔧 Configurando Groq API");
        log.info("📍 Base URL configurada: {}", baseUrl);
        log.info("📍 URL final será: {}/v1/chat/completions", baseUrl);
        log.info("🔑 API Key: {}...", groqApiKey.substring(0, 15));
        
        return new OpenAiApi(baseUrl, groqApiKey);
    }
    
    @Bean
    @Primary
    public OpenAiChatModel openAiChatModel(OpenAiApi groqOpenAiApi) {
        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .withModel("llama-3.3-70b-versatile")
                .withTemperature(0.7)
                .withMaxTokens(1000)
                .build();
        
        log.info("🤖 Modelo configurado: llama-3.3-70b-versatile");
        
        return new OpenAiChatModel(groqOpenAiApi, options);
    }
}
