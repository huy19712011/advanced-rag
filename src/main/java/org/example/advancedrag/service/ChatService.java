package org.example.advancedrag.service;

import lombok.RequiredArgsConstructor;
import org.example.advancedrag.dto.ChatRequest;
import org.example.advancedrag.dto.ChatResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatClient chatClient;

    public ChatResponse getResponse(ChatRequest chatRequest) {

        String aiResponse = chatClient.prompt()
                .user(chatRequest.getMessage())
                .call()
                .content();

        return new ChatResponse(aiResponse);
    }
}
