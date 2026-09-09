package org.example.advancedrag.query;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class MultiQueryServiceTest {

    @Autowired
    private MultiQueryService multiQueryService;

    @Test
    public void testMultiQueryService() {
        String query = "AUTH-403 issue";

        List<String> queries = multiQueryService.generateQueries(query);

        log.info("ORIGINAL QUERY: {}", query);
        for(String q : queries) {
            log.info("GENERATED QUERY: {}", q);
        }
    }
}
