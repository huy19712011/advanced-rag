package org.example.advancedrag.ingestion;

import lombok.extern.slf4j.Slf4j;
import org.example.advancedrag.model.KnowledgeDocument;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class DatabaseIngestionServiceTest {

    @Autowired
    private DatabaseIngestionService databaseIngestionService;

    @Test
    void shouldLoadSupportTickets() {

        List<KnowledgeDocument> documents = databaseIngestionService.loadSupportTickets();

        log.info("Documents Loaded: {}", documents.size());

        for (KnowledgeDocument document : documents) {
            log.info("Source: {}", document.getSource());
            log.info("Content: {}", document.getContent());
            log.info("Metadata: {}", document.getMetadata());
            log.info("----------------------------------------");
        }
    }

    @Test
    void shouldLoadIncidentReports() {

        List<KnowledgeDocument> documents = databaseIngestionService.loadIncidentReports();

        log.info("Documents Loaded: {}", documents.size());

        for (KnowledgeDocument document : documents) {
            log.info("Source: {}", document.getSource());
            log.info("Content: {}", document.getContent());
            log.info("Metadata: {}", document.getMetadata());
            log.info("--------------------------------------");
        }
    }
}
