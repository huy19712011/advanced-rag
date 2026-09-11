package org.example.advancedrag.retrieval;

import lombok.extern.slf4j.Slf4j;
import org.example.advancedrag.dto.RetrievalRequest;
import org.example.advancedrag.model.RetrievalBenchmark;
import org.example.advancedrag.model.RetrievalResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SpringBootTest
@Slf4j
public class RetrievalStrategyComparisonTest {

    @Autowired
    private RetrievalService similaritySearchService;
    @Autowired
    private KeywordSearchService keywordSearchService;
    @Autowired
    private HybridSearchService hybridSearchService;

    @Test
    void shouldCompareRetrievalStrategies() {
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

        Map<String, Double> strategyRecallSum = new HashMap<>();
        strategyRecallSum.put("VECTOR", 0.0);
        strategyRecallSum.put("KEYWORD", 0.0);
        strategyRecallSum.put("HYBRID", 0.0);

        for (RetrievalBenchmark benchmark : benchmarks) {
            log.info("============================");
            log.info("QUERY: {}", benchmark.getQuery());

            RetrievalRequest request = new RetrievalRequest();
            request.setQuery(benchmark.getQuery());
            double vectorRecall = evaluateStrategy("VECTOR", similaritySearchService.retrieve(request), benchmark);
            strategyRecallSum.put("VECTOR", strategyRecallSum.get("VECTOR") + vectorRecall);

            double keywordRecall = evaluateStrategy("KEYWORD", keywordSearchService.search(benchmark.getQuery()), benchmark);
            strategyRecallSum.put("KEYWORD", strategyRecallSum.get("KEYWORD") + keywordRecall);

            double hybridRecall = evaluateStrategy("HYBRID", hybridSearchService.search(request), benchmark);
            strategyRecallSum.put("HYBRID", strategyRecallSum.get("HYBRID") + hybridRecall);
        }

        log.info("========================");
        log.info("OVERALL RESULTS");

        double numBenchmarks = benchmarks.size();
        log.info("VECTOR AVERAGE RECALL: {}", strategyRecallSum.get("VECTOR")/numBenchmarks );
        log.info("KEYWORD AVERAGE RECALL: {}", strategyRecallSum.get("KEYWORD")/numBenchmarks );
        log.info("HYBRID AVERAGE RECALL: {}", strategyRecallSum.get("HYBRID")/numBenchmarks );
    }

    private double evaluateStrategy(String strategy, List<RetrievalResult> results, RetrievalBenchmark benchmark) {
        long matchedSources = results.stream()
                .map(result -> (String) result.getMetadata().get("source"))
                .filter(benchmark.getExpectedSources()::contains)
                .count();
        double recall = (double) matchedSources / benchmark.getExpectedSources().size();
        log.info("{} RECALL: {}", strategy, recall);
        return recall;
    }
}
