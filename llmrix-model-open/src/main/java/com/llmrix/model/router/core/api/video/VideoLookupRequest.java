package com.llmrix.model.router.core.api.video;

import com.llmrix.model.router.core.api.ModelRequest;
import com.llmrix.model.router.core.routing.RoutingHints;
import lombok.Getter;
import lombok.experimental.Accessors;

/** Public data type used by the model routing API. */
@Getter
@Accessors(fluent = true)
public final class VideoLookupRequest implements ModelRequest {
    /** Value of the `videoId` property. */
    private final String videoId;
    /** Value of the `routingHints` property. */
    private final RoutingHints routingHints;

    /** Creates an instance of this API type. */
    public VideoLookupRequest(String videoId, RoutingHints routingHints) {
        if (videoId == null || videoId.isBlank()) throw new IllegalArgumentException("video id must not be blank");
        this.videoId = videoId;
        this.routingHints = routingHints == null ? RoutingHints.none() : routingHints;
    }

    /** Implements the API contract. */
    @Override
    /** Executes the associated model API operation. */
    public int estimatedInputTokens() {
        return 1;
    }
}
