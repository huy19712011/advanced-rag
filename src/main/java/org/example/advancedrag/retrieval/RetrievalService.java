package org.example.advancedrag.retrieval;

import lombok.RequiredArgsConstructor;
import org.example.advancedrag.model.RetrievalResult;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RetrievalService {

    private final VectorStore vectorStore;

    public List<RetrievalResult> retrieve(String query) {

        SearchRequest searchRequest = SearchRequest.builder()
                .query(query)
                .topK(3)
                .build();

        List<Document> documents = vectorStore.similaritySearch(searchRequest);

        List<RetrievalResult> results = new ArrayList<>();
        for (Document document : documents) {
            RetrievalResult result = new RetrievalResult();
            result.setContent(document.getText());
            result.setMetadata(document.getMetadata());

            Object distance = document.getMetadata().get("distance");
            if (distance instanceof Number number) {
                result.setScore(number.doubleValue());
            }
            results.add(result);
        }
        return results;
    }
}
