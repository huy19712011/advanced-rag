package org.example.advancedrag.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.advancedrag.chunking.ChunkingOrchestrator;
import org.example.advancedrag.ingestion.IngestionOrchestrator;
import org.example.advancedrag.model.KnowledgeChunk;
import org.example.advancedrag.model.KnowledgeDocument;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IndexingPipelineService {

    private final IngestionOrchestrator ingestionOrchestrator;
    private final ChunkingOrchestrator chunkingOrchestrator;
    private final VectorStoreService vectorStoreService;

    public void indexAll() {
        List<KnowledgeDocument> documents = ingestionOrchestrator.ingestAll();

        List<KnowledgeChunk> allChunks = new ArrayList<>();

        for (KnowledgeDocument document : documents) {

            allChunks.addAll(chunkingOrchestrator.chunkDocument(document));
        }

        vectorStoreService.storeChunks(allChunks);
    }
}
