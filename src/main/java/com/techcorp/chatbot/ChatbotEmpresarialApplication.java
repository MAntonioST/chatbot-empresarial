package com.techcorp.chatbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
    org.springframework.ai.autoconfigure.openai.OpenAiAutoConfiguration.class
})
public class ChatbotEmpresarialApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatbotEmpresarialApplication.class, args);
    }

}
