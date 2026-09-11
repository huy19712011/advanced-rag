package org.example.advancedrag.service;

import lombok.RequiredArgsConstructor;
import org.example.advancedrag.model.KnowledgeChunk;
import org.example.advancedrag.security.PiiDetectionUtil;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VectorStoreService {

    private final VectorStore vectorStore;

    public void storeChunks(List<KnowledgeChunk> chunks) {

        List<Document> documents = new ArrayList<>();
        for (var chunk : chunks ) {

            Document document = new Document(
                    chunk.getContent(),
                    chunk.getMetadata()
            );

            // add some more metadata
            document.getMetadata().put("chunkId", chunk.getChunkId());
            document.getMetadata().put("documentId", chunk.getDocumentId());
            document.getMetadata().put("source",  chunk.getSource());
            document.getMetadata().put("sourceType", chunk.getSourceType());

            boolean containsPii = PiiDetectionUtil.containsPii(chunk.getContent());
            document.getMetadata().put("containsPii", containsPii);

            documents.add(document);
        }

        vectorStore.add(documents);
    }
}
