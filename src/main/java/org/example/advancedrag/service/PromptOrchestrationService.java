package org.example.advancedrag.service;

import org.example.advancedrag.model.RetrievalResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromptOrchestrationService {

    public String buildPrompt(String query, List<RetrievalResult> results) {

        StringBuilder contextBuilder = new StringBuilder();

        for (RetrievalResult result : results) {

            contextBuilder.append(result.getContent())
                    .append("\n------------------------------------------\n");
        }

        return """
                You are an enterprise AI assistant.
                
                Answer only using the provided context.
                
                If the answer is not present in the context, say you do not know.
                
                Context:
                %s
                
                User Question:
                %s
                
                """.formatted(contextBuilder.toString(), query);
    }
}
