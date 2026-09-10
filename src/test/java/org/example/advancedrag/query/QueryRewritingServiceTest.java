package org.example.advancedrag.query;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class QueryRewritingServiceTest {

    @Autowired
    private QueryRewritingService queryRewritingService;

    @Test
    void shouldRewriteQuery() {
        String originalQuery = "server down";

        String rewrittenQuery =  queryRewritingService.rewrite(originalQuery);

        log.info("ORIGINAL QUERY: {}", originalQuery);
        log.info("REWRITTEN QUERY: {}", rewrittenQuery);
    }
}
