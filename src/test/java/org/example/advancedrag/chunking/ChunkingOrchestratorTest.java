package org.example.advancedrag.chunking;

import lombok.extern.slf4j.Slf4j;
import org.example.advancedrag.ingestion.DatabaseIngestionService;
import org.example.advancedrag.ingestion.PdfIngestionService;
import org.example.advancedrag.ingestion.WikiIngestionService;
import org.example.advancedrag.model.KnowledgeChunk;
import org.example.advancedrag.model.KnowledgeDocument;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class ChunkingOrchestratorTest {

    @Autowired
    private ChunkingOrchestrator orchestrator;

    @Autowired private WikiIngestionService wikiIngestionService;
    @Autowired private PdfIngestionService pdfIngestionService;
    @Autowired private DatabaseIngestionService databaseIngestionService;

    @Test
    void shouldChunkDifferentDocumentTypes() {
        KnowledgeDocument wikiDocument = wikiIngestionService.loadDocuments().get(0);
        KnowledgeDocument pdfDocument = pdfIngestionService.loadPolicyDocuments().get(0);
        KnowledgeDocument dbDocument = databaseIngestionService.loadSupportTickets().get(0);

        testChunking(wikiDocument);
        testChunking(pdfDocument);
        testChunking(dbDocument);
    }

    private void testChunking(KnowledgeDocument document) {
        log.info("========================================");
        log.info("SOURCE TYPE: {}", document.getSourceType());
        log.info("SOURCE: {}", document.getSource());

        List<KnowledgeChunk> chunks = orchestrator.chunkDocument(document);
        log.info("TOTAL CHUNKS: {}", chunks.size());

        for (KnowledgeChunk chunk : chunks) {
            log.info("----------------------------------------");
            log.info("CONTENT:\n{}", chunk.getContent());

        }

    }
}
