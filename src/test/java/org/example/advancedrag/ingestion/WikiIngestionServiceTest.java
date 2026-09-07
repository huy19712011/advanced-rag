package org.example.advancedrag.ingestion;

import lombok.extern.slf4j.Slf4j;
import org.example.advancedrag.model.KnowledgeDocument;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class WikiIngestionServiceTest {

    @Autowired
    private WikiIngestionService wikiIngestionService;

    @Test
    void shouldLoadWikiDocuments() {

        List<KnowledgeDocument> documents = wikiIngestionService.loadDocuments();

        log.info("Documents Loaded: {}", documents.size());
        for (KnowledgeDocument document : documents) {
            log.info("Source: {}", document.getSource());

            log.info("Content Preview: {}", document.getContent().substring(0,
                    Math.min(100, document.getContent().length()))
            );
            log.info("Metadata: {}", document.getMetadata());

            log.info("===============================================================");
        }

    }
}
