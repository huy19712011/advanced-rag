package org.example.advancedrag.chunking;

import lombok.extern.slf4j.Slf4j;
import org.example.advancedrag.model.KnowledgeChunk;
import org.example.advancedrag.model.KnowledgeDocument;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class DatabaseChunkingService {

    public List<KnowledgeChunk> chunkDocument(KnowledgeDocument document) {

        KnowledgeChunk chunk = KnowledgeChunk.builder()
                .chunkId(UUID.randomUUID().toString())
                .documentId(document.getDocumentId())
                .sourceType(document.getSourceType())
                .source(document.getSource())
                .content(document.getContent())
                .chunkIndex(0)
                .metadata(document.getMetadata())
                .build();

        return Collections.singletonList(chunk);
    }
}
