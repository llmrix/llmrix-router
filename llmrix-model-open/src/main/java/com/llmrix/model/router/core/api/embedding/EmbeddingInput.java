package com.llmrix.model.router.core.api.embedding;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Objects;

/** Public data type used by the model routing API. */
@Getter
@EqualsAndHashCode
@Accessors(fluent = true)
public final class EmbeddingInput {
    /** Value of the `text` property. */
    private final String text;
    /** Value of the `tokens` property. */
    private final List<Integer> tokens;

    private EmbeddingInput(String text, List<Integer> tokens) {
        this.text = text;
        this.tokens = tokens == null ? null : List.copyOf(tokens);
    }

    /** Executes the associated model API operation. */
    public static EmbeddingInput text(String value) {
        if (value == null) throw new IllegalArgumentException("embedding text must not be null");
        return new EmbeddingInput(value, null);
    }

    /** Executes the associated model API operation. */
    public static EmbeddingInput tokens(List<Integer> value) {
        Objects.requireNonNull(value, "tokens");
        if (value.isEmpty() || value.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("embedding tokens must not be empty or contain null");
        }
        return new EmbeddingInput(null, value);
    }

    /** Executes the associated model API operation. */
    public boolean tokenized() {
        return tokens != null;
    }
}
