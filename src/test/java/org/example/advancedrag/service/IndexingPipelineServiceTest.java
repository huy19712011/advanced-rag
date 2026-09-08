package org.example.advancedrag.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class IndexingPipelineServiceTest {

    @Autowired
    private IndexingPipelineService indexingPipelineService;

    @Test
    void shouldIndexAllKnowledge() {
        indexingPipelineService.indexAll();
    }
}
