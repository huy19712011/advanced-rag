package org.example.advancedrag.retrieval;

import lombok.extern.slf4j.Slf4j;
import org.example.advancedrag.dto.RetrievalRequest;
import org.example.advancedrag.model.RetrievalBenchmark;
import org.example.advancedrag.model.RetrievalResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

@SpringBootTest
@Slf4j
public class RetrievalBenchmarkTest {

    @Autowired
    private HybridSearchService hybridSearchService;

    @Test
    void shouldEvaluateBenchmarkDataset() {
        List<RetrievalBenchmark> benchmarks = List.of(
                new RetrievalBenchmark("AUTH-403", Set.of(
                        "wiki-repo/engineering/authentication-flow.md",
                        "wiki-repo/engineering/troubleshooting-common-issues.md"
                )),
                new RetrievalBenchmark("vpn timeout", Set.of(
                        "support_tickets/TKT-1001",
                        "assets/screenshots/vpn_timeout_screen.png"
                )),
                new RetrievalBenchmark("kafka lag issue", Set.of(
                        "support_tickets/TKT-1051",
                        "wiki-repo/engineering/platform-overview.md"
                ))
        );

        double totalRecall = 0;
        for (RetrievalBenchmark benchmark : benchmarks) {
            RetrievalRequest request = new RetrievalRequest();
            request.setQuery(benchmark.getQuery());

            List<RetrievalResult> results = hybridSearchService.search(request);

            long matchedSources = results.stream()
                    .map(result -> (String) result.getMetadata().get("source"))
                    .filter(benchmark.getExpectedSources()::contains)
                    .count();

            double recall = (double) matchedSources / benchmark.getExpectedSources().size();

            totalRecall += recall;

            log.info("-------------------------------------");
            log.info("QUERY: {}", benchmark.getQuery());
            log.info("RETRIEVED SOURCES:");
            results.forEach(result -> log.info("- {}", result.getMetadata().get("source")));

            log.info("EXPECTED SOURCES: {} ", benchmark.getExpectedSources());
            log.info("MATCHED SOURCES: {} ", matchedSources);
            log.info("RECALL: {}", recall);
        }

        double averageRecall = totalRecall / benchmarks.size();

        log.info("===============================");
        log.info("BENCHMARKS EXECUTED: {}", benchmarks.size());
        log.info("AVERAGE RECALL: {}", averageRecall);

    }
}
