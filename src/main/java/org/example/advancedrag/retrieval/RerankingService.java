package org.example.advancedrag.retrieval;

import org.example.advancedrag.model.RetrievalResult;
import org.example.advancedrag.model.SourceType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RerankingService {

    public List<RetrievalResult> rerank(List<RetrievalResult> results) {

        for(RetrievalResult result: results){
            double score = result.getFinalScore();

            Map<String, Object> metadata =  result.getMetadata();
            SourceType sourceType = SourceType.valueOf((String) metadata.get("sourceType"));

            if(SourceType.IMAGE.equals(sourceType)){
                score += 0.2;
            }
            if(SourceType.DATABASE.equals(sourceType)){
                score += 0.1;
            }
            if(SourceType.MARKDOWN.equals(sourceType)){
                score += 0.15;
            }

            Object priority = metadata.get("priority");
            if("HIGH".equals(priority)){
                score += 0.2;
            }
            result.setFinalScore(score);
        }

        results.sort((a,b) -> Double.compare(b.getFinalScore(), a.getFinalScore()));

        return results;
    }
}
