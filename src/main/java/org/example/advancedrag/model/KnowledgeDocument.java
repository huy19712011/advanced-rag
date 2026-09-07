package org.example.advancedrag.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class KnowledgeDocument { //common Document

    private String documentId;
    private String content; // actual knowledge extracted from the source (pdf, wiki, image, ...)
    private String source; // where knowledge originated (from pdf, wiki, image, ...)
    private SourceType sourceType;
    private Map<String,Object> metadata; // metadata
}
