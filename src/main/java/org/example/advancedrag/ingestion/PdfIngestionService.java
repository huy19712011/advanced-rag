package org.example.advancedrag.ingestion;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.example.advancedrag.model.KnowledgeDocument;
import org.example.advancedrag.model.SourceType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@Slf4j
public class PdfIngestionService {

    private static final Path DATA_PATH = Path.of("data");
    private static final Path DOCUMENTS_PATH = DATA_PATH.resolve("documents");
    private static final Path REPORTS_PATH = DATA_PATH.resolve("reports");

    public List<KnowledgeDocument> loadPolicyDocuments() {
        return loadDocuments(DOCUMENTS_PATH);
    }

    public List<KnowledgeDocument> loadReports() {
        return loadDocuments(REPORTS_PATH);
    }

    public List<KnowledgeDocument> loadDocuments(Path rootPath) {

        try (Stream<Path> pathStream = Files.walk(rootPath)) {
            return pathStream
                    .filter(Files::isRegularFile)
                    .filter(this::isPdfFile)
                    .map(this::toKnowledgeDocument)
                    .toList();

        } catch (
                IOException e) {
            log.error(e.getMessage());
        }
        return Collections.emptyList();
    }

    private KnowledgeDocument toKnowledgeDocument(Path path) {
        try (PDDocument pdfDocument = PDDocument.load(path.toFile())) {

            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            String content = pdfTextStripper.getText(pdfDocument);

            String relativeSource = DATA_PATH
                    .relativize(path)
                    .toString()
                    .replace("\\", "/");

            return new KnowledgeDocument(
                    UUID.randomUUID().toString(),
                    content,
                    relativeSource,
                    SourceType.PDF,
                    Map.of("fileName", path.getFileName(), "contentLength", content.length())
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isPdfFile(Path path) {
        return path.toString().endsWith(".pdf");
    }
}
