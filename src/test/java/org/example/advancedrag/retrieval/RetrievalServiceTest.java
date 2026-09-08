package org.example.advancedrag.retrieval;

import lombok.extern.slf4j.Slf4j;
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

        String query = "Users not able to connect to VPN";

        List<Document> results = retrievalService.retrieve(query);

        log.info("QUERY: {}", query);
        log.info("TOTAL RESULTS: {}", results.size());
        for(Document result: results) {
            log.info("----------------------------------------");
            log.info("CONTENT:\n{}", result.getText());
            log.info("METADATA: {}", result.getMetadata());
        }

    }
}
