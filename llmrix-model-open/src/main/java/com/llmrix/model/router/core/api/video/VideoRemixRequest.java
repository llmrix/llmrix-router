package com.llmrix.model.router.core.api.video;

import com.llmrix.model.router.core.api.ModelRequest;
import com.llmrix.model.router.core.routing.RoutingHints;
import lombok.Getter;
import lombok.experimental.Accessors;

/** Public data type used by the model routing API. */
@Getter
@Accessors(fluent = true)
public final class VideoRemixRequest implements ModelRequest {
    /** Value of the `videoId` property. */
    private final String videoId;
    /** Value of the `prompt` property. */
    private final String prompt;
    /** Value of the `routingHints` property. */
    private final RoutingHints routingHints;

    /** Creates an instance of this API type. */
    public VideoRemixRequest(String videoId, String prompt, RoutingHints routingHints) {
        if (videoId == null || videoId.isBlank()) throw new IllegalArgumentException("video id must not be blank");
        if (prompt == null || prompt.isBlank()) throw new IllegalArgumentException("video prompt must not be blank");
        this.videoId = videoId;
        this.prompt = prompt;
        this.routingHints = routingHints == null ? RoutingHints.none() : routingHints;
    }

    /** Implements the API contract. */
    @Override
    /** Executes the associated model API operation. */
    public int estimatedInputTokens() {
        return Math.max(1, (prompt.length() + 3) / 4);
    }
}
