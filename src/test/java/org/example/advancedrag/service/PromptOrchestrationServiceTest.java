package org.example.advancedrag.service;

import lombok.extern.slf4j.Slf4j;
import org.example.advancedrag.dto.RetrievalRequest;
import org.example.advancedrag.model.RetrievalResult;
import org.example.advancedrag.retrieval.HybridSearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class PromptOrchestrationServiceTest {

    @Autowired
    private HybridSearchService hybridSearchService;
    @Autowired
    private PromptOrchestrationService promptOrchestrationService;

    @Test
    void shouldBuildGroundedPrompt() {
        RetrievalRequest request = new RetrievalRequest();
        request.setQuery("Why are users getting AUTH-403 errors?");

        List<RetrievalResult> results = hybridSearchService.search(request);
        String prompt = promptOrchestrationService.buildPrompt(request.getQuery(), results);

        log.info("PROMPT:\n{}", prompt);
    }
}
