package com.llmrix.model.router.core.api.embedding;

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
public final class EmbeddingResponse implements RoutedResponse<EmbeddingResponse> {
    /** Returned vectors in request order. */
    private final List<EmbeddingVector> data;
    /** Provider model identifier. */
    private final String modelId;
    /** Reported token usage. */
    private final Usage usage;

    /** Creates an instance of this API type. */
    public EmbeddingResponse(List<EmbeddingVector> data, String modelId, Usage usage) {
        this.data = data == null ? List.of() : List.copyOf(data);
        this.modelId = modelId;
        this.usage = usage == null ? Usage.UNKNOWN : usage;
    }

    /** Implements the API contract. */
    @Override
    /** Executes the associated model API operation. */
    public EmbeddingResponse routedBy(String targetId) {
        return new EmbeddingResponse(data, targetId, usage);
    }
}
