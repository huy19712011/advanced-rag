package org.example.advancedrag;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
@Slf4j
public class OcrTest {

    @Test
    void shouldExtractTextFromImage() throws Exception{
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:/Program Files/Tesseract-OCR/tessdata");

        File imageFile = new File("data/assets/screenshots/auth_403_error.png");

        String extractedText = tesseract.doOCR(imageFile);
        log.info("OCR Result:\n{}", extractedText);

    }
}
