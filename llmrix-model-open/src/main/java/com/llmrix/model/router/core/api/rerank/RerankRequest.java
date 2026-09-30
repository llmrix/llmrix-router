package com.llmrix.model.router.core.api.rerank;

import com.llmrix.model.router.core.api.ModelRequest;
import com.llmrix.model.router.core.routing.RoutingHints;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Objects;

/** Public data type used by the model routing API. */
@Getter
@Accessors(fluent = true)
public final class RerankRequest implements ModelRequest {
    /** Query used to score candidate documents. */
    private final String query;
    /** Candidate documents in their original order. */
    private final List<String> documents;
    /** Maximum number of results to return. */
    private final Integer topN;
    /** Whether result documents should be copied into the response. */
    private final boolean returnDocuments;
    /** Router selection hints. */
    private final RoutingHints routingHints;

    /** Creates an instance of this API type. */
    public RerankRequest(String query, List<String> documents, Integer topN,
                         Boolean returnDocuments, RoutingHints routingHints) {
        if (query == null || query.isBlank()) throw new IllegalArgumentException("rerank query must not be blank");
        if (documents == null || documents.isEmpty()) throw new IllegalArgumentException("rerank documents must not be empty");
        if (documents.stream().anyMatch(Objects::isNull))
            throw new IllegalArgumentException("rerank documents must not contain null");
        if (topN != null && topN < 1) throw new IllegalArgumentException("topN must be > 0");
        this.query = query;
        this.documents = List.copyOf(documents);
        this.topN = topN;
        this.returnDocuments = Boolean.TRUE.equals(returnDocuments);
        this.routingHints = routingHints == null ? RoutingHints.none() : routingHints;
    }

    /** Creates an instance of this API type. */
    public RerankRequest(String query, List<String> documents) {
        this(query, documents, null, false, null);
    }

    /** Implements the API contract. */
    @Override
    /** Executes the associated model API operation. */
    public int estimatedInputTokens() {
        long estimate = query.length();
        estimate += documents.stream().mapToLong(String::length).sum();
        return (int) Math.min(Integer.MAX_VALUE, Math.max(1, (estimate + 3L) / 4L));
    }
}
