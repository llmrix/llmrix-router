package com.llmrix.model.router.core.routing;

import java.util.List;
import java.util.Objects;

/** Typed, provider-neutral result returned by a JEV decision client. */
public record JevDecision(List<String> preferredTags, double confidence) {
    public JevDecision {
        if (preferredTags == null) throw new IllegalArgumentException("preferredTags must not be null");
        preferredTags = preferredTags.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(value -> !value.isEmpty())
                .distinct()
                .toList();
        if (!Double.isFinite(confidence) || confidence < 0 || confidence > 1) {
            throw new IllegalArgumentException("confidence must be between 0 and 1");
        }
    }
}
