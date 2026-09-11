package org.example.advancedrag.retrieval;

import lombok.extern.slf4j.Slf4j;
import org.example.advancedrag.dto.RetrievalRequest;
import org.example.advancedrag.model.RetrievalResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

@SpringBootTest
@Slf4j
public class RetrievalEvaluationTest {

    @Autowired
    private HybridSearchService  hybridSearchService;

    @Test
    void shouldEvaluateRetrievalQuality() {
        String query = "AUTH-403";

        Set<String> expectedSources = Set.of(
                "wiki-repo/engineering/authentication-flow.md",
                "wiki-repo/engineering/troubleshooting-common-issues.md"
        );

        RetrievalRequest request = new RetrievalRequest();
        request.setQuery(query);

        List<RetrievalResult> results = hybridSearchService.search(request);
        long matchedSources = results.stream()
                .map(result -> (String) result.getMetadata().get("source"))
                .filter(expectedSources::contains)
                .count();

        double recall = (double) matchedSources / expectedSources.size();

        log.info("QUERY: {}", query);
        log.info("RETRIEVED SOURCES:");
        results.forEach(result -> log.info("- {}", result.getMetadata().get("source")));

        log.info("EXPECTED SOURCES: {} ", expectedSources);
        log.info("MATCHED SOURCES: {} ", matchedSources);
        log.info("RECALL: {}", recall);
    }
}
