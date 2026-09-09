package org.example.advancedrag.query;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class QueryDecompositionServiceTest {

    @Autowired
    private QueryDecompositionService queryDecompositionService;

    @Test
    void shouldDecomposeQuery() {
        String query = "Why are users getting AUTH-403 errors after deployment?";

        List<String> subqueries = queryDecompositionService.decompose(query);

        log.info("ORIGINAL QUERY: {}", query);

        for (String subquery : subqueries) {
            log.info("SUBQUERY: {}", subquery);
        }

    }
}
