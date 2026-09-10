package org.example.advancedrag.retrieval;

import lombok.extern.slf4j.Slf4j;
import org.example.advancedrag.model.RetrievalResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class RetrievalFallbackServiceTest {

    @Autowired
    private RetrievalFallbackService retrievalFallbackService;

    @Test
    void shouldRetrieve() {
        List<RetrievalResult> results = retrievalFallbackService.retrieve("gateway timeout");

        for(RetrievalResult result: results) {
            log.info("--------------------------------");
            log.info("CONTENT:\n{}", result.getContent());
            log.info("TYPE: {}", result.getRetrievalType());
            log.info("SCORE: {}", result.getFinalScore());
            log.info("METADATA: {}", result.getMetadata());

        }
    }
}
