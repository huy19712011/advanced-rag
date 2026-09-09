package org.example.advancedrag.retrieval;

import lombok.extern.slf4j.Slf4j;
import org.example.advancedrag.model.RetrievalResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class MultiQueryRetrievalServiceTest {

    @Autowired
    private MultiQueryRetrievalService multiQueryRetrievalService;

    @Test
    public void testMultiQueryRetrievalService() {
        String query = "AUTH-403 issue";

        List<RetrievalResult> results = multiQueryRetrievalService.retrieve(query);

        log.info("QUERY: {}", query);
        log.info("TOTAL RESULTS: {}", results.size());

        for (RetrievalResult result : results) {

            log.info("--------------------------------");

            log.info("FINAL SCORE: {}", result.getFinalScore());
            log.info("TYPE: {}", result.getRetrievalType());
            log.info("CONTENT:\n{}", result.getContent());
            log.info("METADATA: {}", result.getMetadata());
        }
    }
}
