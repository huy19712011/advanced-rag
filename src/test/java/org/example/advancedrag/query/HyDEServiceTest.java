package org.example.advancedrag.query;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class HyDEServiceTest {

    @Autowired
    private HyDEService hyDEService;

    @Test
    void shouldGenerateHypotheticalDocument() {
        String query = "vpn issue";

        String document = hyDEService.generateHypotheticalDocument(query);
        log.info("QUERY: {}", query);

        log.info("HYPOTHETICAL DOCUMENT:\n{}", document);
    }
}
