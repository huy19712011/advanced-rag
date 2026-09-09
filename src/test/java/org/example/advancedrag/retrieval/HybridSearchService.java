package org.example.advancedrag.retrieval;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.advancedrag.dto.RetrievalRequest;
import org.example.advancedrag.model.RetrievalResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class HybridSearchService {

    private final RetrievalService retrievalService;
    private final KeywordSearchService keywordSearchService;

    public List<RetrievalResult> search(RetrievalRequest request) {

        List<RetrievalResult> vectorResults = retrievalService.retrieve(request);

        List<RetrievalResult> keywordResults = keywordSearchService.search(request.getQuery());

        Map<String, RetrievalResult> mergedResults = new LinkedHashMap<>();

        for (RetrievalResult result : vectorResults) {
            mergedResults.put(result.getContent(), result);
        }
        for (RetrievalResult result : keywordResults) {
            mergedResults.putIfAbsent(result.getContent(), result);
        }

        return new ArrayList<>(mergedResults.values());
    }
}
