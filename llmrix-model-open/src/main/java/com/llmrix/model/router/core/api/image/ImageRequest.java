package com.llmrix.model.router.core.api.image;

import com.llmrix.model.router.core.api.ModelRequest;
import com.llmrix.model.router.core.routing.RoutingHints;
import lombok.Getter;
import lombok.experimental.Accessors;

/** Public data type used by the model routing API. */
@Getter
@Accessors(fluent = true)
public class ImageRequest implements ModelRequest {
    /** Text prompt describing the desired image. */
    private final String prompt;
    /** Number of images requested. */
    private final Integer count;
    /** Requested image dimensions. */
    private final String size;
    /** Provider quality preset. */
    private final String quality;
    /** Provider style preset. */
    private final String style;
    /** Requested response representation. */
    private final String responseFormat;
    /** End-user identifier for provider auditing. */
    private final String user;
    /** Requested background treatment. */
    private final String background;
    /** Requested output image format. */
    private final String outputFormat;
    /** Output compression level, when supported. */
    private final Integer outputCompression;
    /** Router selection hints. */
    private final RoutingHints routingHints;

    /** Creates an instance of this API type. */
    public ImageRequest(String prompt, Integer count, String size, String quality, String style,
                        String responseFormat, String user, String background, String outputFormat,
                        Integer outputCompression, RoutingHints routingHints) {
        if (prompt == null || prompt.isBlank()) throw new IllegalArgumentException("image prompt must not be blank");
        if (count != null && count < 1) throw new IllegalArgumentException("image count must be > 0");
        if (outputCompression != null && (outputCompression < 0 || outputCompression > 100)) {
            throw new IllegalArgumentException("output compression must be between 0 and 100");
        }
        this.prompt = prompt;
        this.count = count;
        this.size = size;
        this.quality = quality;
        this.style = style;
        this.responseFormat = responseFormat;
        this.user = user;
        this.background = background;
        this.outputFormat = outputFormat;
        this.outputCompression = outputCompression;
        this.routingHints = routingHints == null ? RoutingHints.none() : routingHints;
    }

    /** Implements the API contract. */
    @Override
    /** Executes the associated model API operation. */
    public int estimatedInputTokens() {
        return Math.max(1, (prompt.length() + 3) / 4);
    }
}
