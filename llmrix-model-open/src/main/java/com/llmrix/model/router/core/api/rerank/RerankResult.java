package com.llmrix.model.router.core.api.rerank;

import lombok.Getter;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/** Public data type used by the model routing API. */
@Getter
@EqualsAndHashCode
@Accessors(fluent = true)
public final class RerankResult {
    /** Value of the `index` property. */
    private final int index;
    /** Value of the `relevanceScore` property. */
    private final double relevanceScore;
    /** Value of the `document` property. */
    private final String document;

    /** Creates an instance of this API type. */
    public RerankResult(int index, double relevanceScore, String document) {
        this.index = index;
        this.relevanceScore = relevanceScore;
        this.document = document;
    }
}
