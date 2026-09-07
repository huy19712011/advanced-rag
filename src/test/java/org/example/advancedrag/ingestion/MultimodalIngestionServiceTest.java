package org.example.advancedrag.ingestion;

import lombok.extern.slf4j.Slf4j;
import org.example.advancedrag.model.KnowledgeDocument;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class MultimodalIngestionServiceTest {

    @Autowired
    private MultimodalIngestionService multimodalIngestionService;

    @Test
    void shouldLoadMultimodalDocuments() {
        List<KnowledgeDocument> documents =  multimodalIngestionService.loadDocuments();
        log.info("Documents Loaded: {}", documents.size());

        for (KnowledgeDocument document : documents) {

            log.info("Source: {}", document.getSource());
            log.info("Content:\n{}", document.getContent());
            log.info("Metadata: {}", document.getMetadata());
            log.info("----------------------------------------");

        }

    }
}
