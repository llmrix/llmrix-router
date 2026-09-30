package com.llmrix.model.router.core.api.embedding;

import com.llmrix.model.router.core.api.ModelRequest;
import com.llmrix.model.router.core.routing.RoutingHints;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Objects;

/** Public data type used by the model routing API. */
@Getter
@Accessors(fluent = true)
public final class EmbeddingRequest implements ModelRequest {
    /** Representation requested for returned vectors. */
    public enum EncodingFormat {FLOAT, BASE64}

    /** Text or token sequences to embed. */
    private final List<EmbeddingInput> inputs;
    /** Requested vector encoding. */
    private final EncodingFormat encodingFormat;
    /** Optional output dimensionality. */
    private final Integer dimensions;
    /** Optional end-user identifier. */
    private final String user;
    /** Router selection hints. */
    private final RoutingHints routingHints;

    /** Creates an instance of this API type. */
    public EmbeddingRequest(List<EmbeddingInput> inputs, EncodingFormat encodingFormat,
                            Integer dimensions, String user, RoutingHints routingHints) {
        if (inputs == null || inputs.isEmpty()) throw new IllegalArgumentException("embedding input must not be empty");
        this.inputs = List.copyOf(inputs);
        if (this.inputs.stream().anyMatch(Objects::isNull))
            throw new IllegalArgumentException("embedding input contains null");
        if (dimensions != null && dimensions < 1) throw new IllegalArgumentException("dimensions must be > 0");
        this.encodingFormat = encodingFormat == null ? EncodingFormat.FLOAT : encodingFormat;
        this.dimensions = dimensions;
        this.user = user;
        this.routingHints = routingHints == null ? RoutingHints.none() : routingHints;
    }

    /** Executes the associated model API operation. */
    public static EmbeddingRequest text(String text) {
        return new EmbeddingRequest(List.of(EmbeddingInput.text(text)), null, null, null, null);
    }

    /** Implements the API contract. */
    @Override
    /** Executes the associated model API operation. */
    public int estimatedInputTokens() {
        long estimate = inputs.stream().mapToLong(input -> input.tokenized()
                ? input.tokens().size() : Math.max(1, (input.text().length() + 3L) / 4L)).sum();
        return (int) Math.min(Integer.MAX_VALUE, estimate);
    }
}
