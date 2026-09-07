package org.example.advancedrag.ingestion;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.advancedrag.model.KnowledgeDocument;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class IngestionOrchestrator {

    private final WikiIngestionService  wikiIngestionService;
    private final PdfIngestionService pdfIngestionService;
    private final DatabaseIngestionService  databaseIngestionService;
    private final MultimodalIngestionService  multimodalIngestionService;

    public List<KnowledgeDocument> ingestAll() {
        List<KnowledgeDocument> documents = new ArrayList<>();

        documents.addAll(wikiIngestionService.loadDocuments());

        documents.addAll(pdfIngestionService.loadPolicyDocuments());
        documents.addAll(pdfIngestionService.loadReports());

        documents.addAll(databaseIngestionService.loadSupportTickets());
        documents.addAll(databaseIngestionService.loadIncidentReports());

        documents.addAll(multimodalIngestionService.loadDocuments());

        return documents;
    }
}
