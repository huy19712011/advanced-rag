package org.example.advancedrag.retrieval;

import lombok.extern.slf4j.Slf4j;
import org.example.advancedrag.model.RetrievalResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class KeywordSearchServiceTest {

    @Autowired
    private KeywordSearchService keywordSearchService;

    @Test
    void shouldPerformKeywordSearch() {
        String query = "AUTH-403";

        List<RetrievalResult> results = keywordSearchService.search(query);
        log.info("QUERY: {}", query);
        log.info("TOTAL RESULTS: {}", results.size());

        for(RetrievalResult result : results) {
            log.info("----------------------------------------");
            log.info("CONTENT:\n{}", result.getContent());
            log.info("METADATA: {}", result.getMetadata());
        }

    }
}
