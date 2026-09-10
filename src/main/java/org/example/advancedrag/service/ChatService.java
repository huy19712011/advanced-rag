package org.example.advancedrag.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.advancedrag.dto.ChatRequest;
import org.example.advancedrag.dto.ChatResponse;
import org.example.advancedrag.dto.RetrievalRequest;
import org.example.advancedrag.model.RetrievalResult;
import org.example.advancedrag.retrieval.HybridSearchService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final ChatClient chatClient;
    private final HybridSearchService hybridSearchService;
    private final PromptOrchestrationService promptOrchestrationService;

    public ChatResponse getResponse(ChatRequest chatRequest) {

        RetrievalRequest retrievalRequest = new RetrievalRequest();
        retrievalRequest.setQuery(chatRequest.getMessage());
        List<RetrievalResult> results = hybridSearchService.search(retrievalRequest);

        String prompt = promptOrchestrationService.buildPrompt(chatRequest.getMessage(), results);
        log.info("PROMPT: \n{}", prompt);

        String aiResponse = chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        List<String> sources = results.stream()
                .map(result -> (String) result.getMetadata().get("source"))
                .distinct()
                .toList();

        return new ChatResponse(aiResponse, sources);
    }
}
