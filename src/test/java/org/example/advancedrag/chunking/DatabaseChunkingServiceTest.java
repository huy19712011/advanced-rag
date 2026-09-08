package org.example.advancedrag.chunking;

import lombok.extern.slf4j.Slf4j;
import org.example.advancedrag.ingestion.DatabaseIngestionService;
import org.example.advancedrag.model.KnowledgeChunk;
import org.example.advancedrag.model.KnowledgeDocument;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class DatabaseChunkingServiceTest {

    @Autowired
    private DatabaseIngestionService databaseIngestionService;

    @Autowired
    private DatabaseChunkingService databaseChunkingService;

    @Test
    void shouldChunkSupportTickets() {
        List<KnowledgeDocument> documents = databaseIngestionService.loadSupportTickets();
        KnowledgeDocument document = documents.get(0);

        List<KnowledgeChunk> chunks = databaseChunkingService.chunkDocument(document);
        log.info("Total Chunks: {}", chunks.size());
        for (KnowledgeChunk chunk : chunks) {
            log.info("Chunk Content:\n{}", chunk.getContent());
            log.info("Chunk Metadata: {}", chunk.getMetadata());
            log.info("----------------------------------------");

        }
    }
}
