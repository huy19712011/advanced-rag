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

        long start = System.currentTimeMillis();

        RetrievalRequest retrievalRequest = new RetrievalRequest();
        retrievalRequest.setQuery(chatRequest.getMessage());

        long retrievalStart = System.currentTimeMillis();
        List<RetrievalResult> results = hybridSearchService.search(retrievalRequest);
        long retrievalLatency = System.currentTimeMillis() - retrievalStart;

        String prompt = promptOrchestrationService.buildPrompt(chatRequest.getMessage(), results);
        log.info("PROMPT: \n{}", prompt);

        long generationStart = System.currentTimeMillis();
        String aiResponse = chatClient.prompt()
                .user(prompt)
                .call()
                .content();
        long generationLatency = System.currentTimeMillis() - generationStart;

        List<String> sources = results.stream()
                .map(result -> (String) result.getMetadata().get("source"))
                .distinct()
                .toList();

        long totalLatency = System.currentTimeMillis() - start;

        log.info("QUERY: {}", chatRequest.getMessage());
        log.info("RETRIEVAL RESULTS: {}", results.size());
        log.info("PROMPT SIZE: {} chars", prompt.length());

        log.info("RETRIEVAL LATENCY: {} ms", retrievalLatency);
        log.info("GENERATION LATENCY: {} ms", generationLatency);
        log.info("TOTAL LATENCY: {} ms",  totalLatency);

        return new ChatResponse(aiResponse, sources);
    }
}
