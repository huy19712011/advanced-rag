package org.example.advancedrag.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class GroundednessBenchmark {

    private final String query;
    private final Set<String> expectedTerms;
}
