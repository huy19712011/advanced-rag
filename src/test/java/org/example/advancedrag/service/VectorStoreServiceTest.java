package org.example.advancedrag.service;

import lombok.extern.slf4j.Slf4j;
import org.example.advancedrag.chunking.SemanticChunkingService;
import org.example.advancedrag.ingestion.WikiIngestionService;
import org.example.advancedrag.model.KnowledgeChunk;
import org.example.advancedrag.model.KnowledgeDocument;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class VectorStoreServiceTest {

    @Autowired
    private VectorStoreService vectorStoreService;

    @Autowired
    private WikiIngestionService wikiIngestionService;

    @Autowired
    private SemanticChunkingService semanticChunkingService;

    @Test
    void shouldStoreSemanticChunksInVectorStore() {

        KnowledgeDocument document = wikiIngestionService.loadDocuments().get(0);

        List<KnowledgeChunk> chunks = semanticChunkingService.chunkDocument(document);

        vectorStoreService.storeChunks(chunks);
    }
}
