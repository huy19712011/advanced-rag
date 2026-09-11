package org.example.advancedrag.service;

import lombok.extern.slf4j.Slf4j;
import org.example.advancedrag.dto.ChatRequest;
import org.example.advancedrag.dto.ChatResponse;
import org.example.advancedrag.model.GroundednessBenchmark;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

@SpringBootTest
@Slf4j
public class GroundednessEvaluationTest {

    @Autowired
    private ChatService chatService;

    @Test
    void shouldEvaluateGroundedness() {
        List<GroundednessBenchmark> benchmarks = List.of(
                new GroundednessBenchmark("AUTH-403", Set.of("stale gateway cache", "oauth scope")),
                new GroundednessBenchmark("vpn timeout", Set.of("login", "password reset")),
                new GroundednessBenchmark("show me lunch menu for today",  Set.of("I do not know"))
        );

        double totalGroundedness = 0;

        for(GroundednessBenchmark benchmark : benchmarks) {
            ChatRequest request = new ChatRequest();
            request.setMessage(benchmark.getQuery());

            ChatResponse response = chatService.getResponse(request, "admin");
            String answer = response.getResponse().toLowerCase();

            long matchedTerms = benchmark.getExpectedTerms().stream()
                    .map(String::toLowerCase)
                    .filter(answer::contains)
                    .count();

            double groundedness = (double) matchedTerms / benchmark.getExpectedTerms().size();
            totalGroundedness += groundedness;

            log.info("-----------------------------");
            log.info("QUERY: {}", benchmark.getQuery());
            log.info("ANSWER: {}", answer);
            log.info("EXPECTED TERMS: {}", benchmark.getExpectedTerms());
            log.info("MATCHED TERMS: {}", matchedTerms);
            log.info("GROUNDEDNESS: {}", groundedness);
        }

        double averageGroundedness = totalGroundedness / benchmarks.size();
        log.info("AVERAGE GROUNDEDNESS: {}", averageGroundedness);
    }
}
