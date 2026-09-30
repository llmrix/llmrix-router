package com.llmrix.model.router.core.api.rerank;

import com.llmrix.model.router.core.api.RoutedResponse;
import com.llmrix.model.router.core.api.Usage;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.List;

/** Public data type used by the model routing API. */
@Getter
@EqualsAndHashCode
@Accessors(fluent = true)
public final class RerankResponse implements RoutedResponse<RerankResponse> {
    /** Ranked results, normally ordered by descending relevance. */
    private final List<RerankResult> results;
    /** Provider model identifier. */
    private final String modelId;
    /** Reported token usage. */
    private final Usage usage;

    /** Creates an instance of this API type. */
    public RerankResponse(List<RerankResult> results, String modelId, Usage usage) {
        this.results = results == null ? List.of() : List.copyOf(results);
        this.modelId = modelId;
        this.usage = usage == null ? Usage.UNKNOWN : usage;
    }

    /** Implements the API contract. */
    @Override
    /** Executes the associated model API operation. */
    public RerankResponse routedBy(String targetId) {
        return new RerankResponse(results, targetId, usage);
    }
}
