package com.llmrix.model.router.core.api.rerank;

/** Provider-neutral document relevance ranking operation. */
public interface RerankModel {
    /**
     * Ranks candidate documents by their relevance to a query.
     *
     * @param request query, candidate documents, and result options
     * @return ranked documents with relevance scores
     */
    RerankResponse rerank(RerankRequest request);
}
