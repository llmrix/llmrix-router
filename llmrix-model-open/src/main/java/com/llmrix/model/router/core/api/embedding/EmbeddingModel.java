package com.llmrix.model.router.core.api.embedding;

/** Provider-neutral text and token embedding operation. */
public interface EmbeddingModel {
    /**
     * Creates vector representations for the supplied inputs.
     *
     * @param request embedding inputs and output encoding options
     * @return the generated embedding vectors and usage information
     */
    EmbeddingResponse embed(EmbeddingRequest request);
}
