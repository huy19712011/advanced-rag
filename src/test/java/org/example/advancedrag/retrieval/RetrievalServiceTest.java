package org.example.advancedrag.retrieval;

import lombok.extern.slf4j.Slf4j;
import org.example.advancedrag.dto.RetrievalRequest;
import org.example.advancedrag.model.RetrievalResult;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class RetrievalServiceTest {

    @Autowired
    private RetrievalService retrievalService;

    @Test
    void shouldRetrieveRelevantChunks() {

        String query = "AUTH-403";

        RetrievalRequest request = new RetrievalRequest(query, "DATABASE");
        //request.setEnvironment("PRODUCTION");

        List<RetrievalResult> results = retrievalService.retrieve(request);

        log.info("QUERY: {}", query);
        log.info("TOTAL RESULTS: {}", results.size());
        for(RetrievalResult result: results) {
            log.info("----------------------------------------");
            log.info("CONTENT:\n{}", result.getContent());
            log.info("SCORE:\n{}", result.getScore());
            log.info("METADATA: {}", result.getMetadata());
        }

    }
}
