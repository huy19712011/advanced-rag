package org.example.advancedrag.retrieval;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.advancedrag.dto.RetrievalRequest;
import org.example.advancedrag.model.RetrievalResult;
import org.example.advancedrag.query.MultiQueryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class MultiQueryRetrievalService {

    private final MultiQueryService multiQueryService;
    private final HybridSearchService hybridSearchService;
    private final RerankingService rerankingService;

    public List<RetrievalResult> retrieve(String query) {

        List<String> generatedQueries = multiQueryService.generateQueries(query);

        Map<String, RetrievalResult> mergedResults = new LinkedHashMap<>();

        for (String generatedQuery : generatedQueries) {

            RetrievalRequest request = new RetrievalRequest();
            request.setQuery(generatedQuery);

            List<RetrievalResult> results = hybridSearchService.search(request);

            for (RetrievalResult result : results) {

                RetrievalResult existing = mergedResults.get(result.getContent());

                if (existing != null) {

                    double boostedScore = existing.getFinalScore() + 0.1;
                    existing.setFinalScore(boostedScore);

                } else {

                    mergedResults.put(result.getContent(), result);
                }
            }
        }

        List<RetrievalResult> finalResults = new ArrayList<>(mergedResults.values());
        return rerankingService.rerank(finalResults);

    }
}
