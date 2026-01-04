package com.techcorp.chatbot.config;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class GroqConfig {

    @Value("${groq.api.key}")
    private String groqApiKey;

    @Bean
    public OpenAiApi groqOpenAiApi() {
        log.info("🔧 Configurando Groq API");
        String baseUrl = "https://api.groq.com/openai";
        log.info("📍 Base URL: {}", baseUrl);
        log.info("🔑 API Key: {}...", groqApiKey.substring(0, Math.min(15, groqApiKey.length())));
        
        return new OpenAiApi(baseUrl, groqApiKey);
    }

    @Bean
    public OpenAiChatModel openAiChatModel(OpenAiApi groqOpenAiApi) {
        OpenAiChatOptions options = OpenAiChatOptions.builder()
            .withModel("llama-3.3-70b-versatile")
            .withTemperature(0.7)
            .withMaxTokens(1000)
            .build();
        
        log.info("🤖 Modelo: llama-3.3-70b-versatile");
        
        return new OpenAiChatModel(groqOpenAiApi, options);
    }
}
