package org.example.advancedrag.ingestion;

import lombok.extern.slf4j.Slf4j;
import org.example.advancedrag.model.KnowledgeDocument;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class PdfIngestionServiceTest {

    @Autowired
    private PdfIngestionService pdfIngestionService;

    @Test
    void shouldLoadPolicyDocuments() {
        List<KnowledgeDocument> documents = pdfIngestionService.loadPolicyDocuments();
        log.info("Documents Loaded: {}", documents.size());

        for (KnowledgeDocument document : documents) {
            log.info("Source: {}", document.getSource());
            String content = document.getContent();

            log.info("Content Preview: {}", content.substring(0, Math.min(200, content.length())));

            log.info("Metadata: {}", document.getMetadata());
            log.info("----------------------------------------");
        }
    }

    @Test
    void shouldLoadReports() {
        List<KnowledgeDocument> documents = pdfIngestionService.loadReports();
        log.info("Documents Loaded: {}", documents.size());

        for (KnowledgeDocument document : documents) {
            log.info("Source: {}", document.getSource());
            String content = document.getContent();

            log.info("Content Preview: {}", content.substring(0, Math.min(200, content.length())));

            log.info("Metadata: {}", document.getMetadata());
            log.info("--------------------------------------");
        }
    }
}
