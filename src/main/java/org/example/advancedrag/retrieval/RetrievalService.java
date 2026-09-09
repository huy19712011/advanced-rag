package org.example.advancedrag.retrieval;

import lombok.RequiredArgsConstructor;
import org.example.advancedrag.dto.RetrievalRequest;
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

    public List<RetrievalResult> retrieve(RetrievalRequest request) {

        SearchRequest.Builder builder = SearchRequest.builder()
                .query(request.getQuery())
                .topK(3);

        if (request.getSourceType() != null) {
            builder.filterExpression("sourceType == '" + request.getSourceType() + "'");
        }

        List<Document> documents = vectorStore.similaritySearch(builder.build());

        List<RetrievalResult> results = new ArrayList<>();
        for (Document document : documents) {
            RetrievalResult result = new RetrievalResult();
            result.setContent(document.getText());
            result.setMetadata(document.getMetadata());

            Object distance = document.getMetadata().get("distance");
            if (distance instanceof Number number) {
                result.setScore(number.doubleValue());

                result.setFinalScore(1.0 - result.getScore());
            }
            result.setRetrievalType("VECTOR");
            results.add(result);
        }
        return results;
    }
}
