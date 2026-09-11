package org.example.advancedrag.retrieval;

import lombok.extern.slf4j.Slf4j;
import org.example.advancedrag.dto.RetrievalRequest;
import org.example.advancedrag.model.RetrievalResult;
import org.example.advancedrag.security.TenantContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class TenantIsolationTest {

    @Autowired
    private RetrievalService  retrievalService;

    @AfterEach
    void cleanup() {
        TenantContext.clear();
    }

    @Test
    void shouldRestrictResultsToTenant() {
        TenantContext.setTenant("TENANT_BETA");

        RetrievalRequest request = new RetrievalRequest();
        request.setQuery("VPN timeout");
        List<RetrievalResult> results = retrievalService.retrieve(request);

        log.info("TOTAL RESULTS: {}", results.size());

        for (RetrievalResult result : results) {
            log.info("----------------------------------------");
            log.info("CONTENT:\n{}", result.getContent());
            log.info("SCORE:\n{}", result.getScore());
            log.info("METADATA: {}", result.getMetadata());
        }
    }
}
