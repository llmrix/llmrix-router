package com.llmrix.model.router.core.api.embedding;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.List;

/** Public data type used by the model routing API. */
@Getter
@EqualsAndHashCode
@Accessors(fluent = true)
public final class EmbeddingVector {
    /** Value of the `index` property. */
    private final int index;
    /** Value of the `values` property. */
    private final List<Double> values;
    /** Value of the `base64` property. */
    private final String base64;

    /** Creates an instance of this API type. */
    public EmbeddingVector(int index, List<Double> values, String base64) {
        if (index < 0) throw new IllegalArgumentException("index must be >= 0");
        if ((values == null) == (base64 == null)) {
            throw new IllegalArgumentException("exactly one embedding representation is required");
        }
        this.index = index;
        this.values = values == null ? null : List.copyOf(values);
        this.base64 = base64;
    }

    /** Executes the associated model API operation. */
    public static EmbeddingVector floats(int index, List<Double> values) {
        return new EmbeddingVector(index, values, null);
    }

    /** Executes the associated model API operation. */
    public static EmbeddingVector base64(int index, String value) {
        return new EmbeddingVector(index, null, value);
    }
}
