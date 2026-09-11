package org.example.advancedrag.service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
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
    private final MeterRegistry meterRegistry;

    public ChatResponse getResponse(ChatRequest chatRequest) {

        //long start = System.currentTimeMillis();

        RetrievalRequest retrievalRequest = new RetrievalRequest();
        retrievalRequest.setQuery(chatRequest.getMessage());

        //long retrievalStart = System.currentTimeMillis();
        Timer.Sample retrievalSample = Timer.start(meterRegistry);
        List<RetrievalResult> results = hybridSearchService.search(retrievalRequest);
        retrievalSample.stop(meterRegistry.timer("rag.retrieval.latency"));
        meterRegistry.counter("rag.retrieval.request").increment();
        //long retrievalLatency = System.currentTimeMillis() - retrievalStart;

        String prompt = promptOrchestrationService.buildPrompt(chatRequest.getMessage(), results);
        log.info("PROMPT: \n{}", prompt);
        meterRegistry.summary("rag.prompt.side").record(prompt.length());

        //long generationStart = System.currentTimeMillis();
        Timer.Sample generationSample = Timer.start(meterRegistry);
        String aiResponse = chatClient.prompt()
                .user(prompt)
                .call()
                .content();
        generationSample.stop(meterRegistry.timer("rag.generation.latency"));
        //long generationLatency = System.currentTimeMillis() - generationStart;

        List<String> sources = results.stream()
                .map(result -> (String) result.getMetadata().get("source"))
                .distinct()
                .toList();

        //long totalLatency = System.currentTimeMillis() - start;
        //
        //log.info("QUERY: {}", chatRequest.getMessage());
        //log.info("RETRIEVAL RESULTS: {}", results.size());
        //log.info("PROMPT SIZE: {} chars", prompt.length());
        //
        //log.info("RETRIEVAL LATENCY: {} ms", retrievalLatency);
        //log.info("GENERATION LATENCY: {} ms", generationLatency);
        //log.info("TOTAL LATENCY: {} ms",  totalLatency);

        return new ChatResponse(aiResponse, sources);
    }
}
