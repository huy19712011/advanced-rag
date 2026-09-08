package org.example.advancedrag.chunking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.advancedrag.model.KnowledgeChunk;
import org.example.advancedrag.model.KnowledgeDocument;
import org.example.advancedrag.model.SourceType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChunkingOrchestrator {

    private final FixedSizeChunkingService  fixedSizeChunkingService;
    private final OverlapChunkingService overlapChunkingService;
    private final SemanticChunkingService  semanticChunkingService;
    private final DatabaseChunkingService databaseChunkingService;

    public List<KnowledgeChunk> chunkDocument(KnowledgeDocument document) {

        SourceType sourceType = document.getSourceType();

        return switch(sourceType) {
            case MARKDOWN -> semanticChunkingService.chunkDocument(document);
            case PDF,
                 IMAGE -> overlapChunkingService.chunkDocument(document);
            case DATABASE -> databaseChunkingService.chunkDocument(document);

            default -> fixedSizeChunkingService.chunkDocument(document);
        };
    }
}
