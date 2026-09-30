package com.llmrix.model.router.core.api.video;

import com.llmrix.model.router.core.api.ModelRequest;
import com.llmrix.model.router.core.routing.RoutingHints;
import lombok.Getter;
import lombok.experimental.Accessors;

/** Public data type used by the model routing API. */
@Getter
@Accessors(fluent = true)
public final class VideoRequest implements ModelRequest {
    /** Text prompt describing the desired video. */
    private final String prompt;
    /** Requested duration in seconds. */
    private final String seconds;
    /** Requested video dimensions. */
    private final String size;
    /** Optional remote source video URL. */
    private final String inputReferenceUrl;
    /** Optional uploaded source video. */
    private final VideoInput inputReference;
    /** Router selection hints. */
    private final RoutingHints routingHints;

    /** Creates an instance of this API type. */
    public VideoRequest(String prompt, String seconds, String size, String inputReferenceUrl,
                        RoutingHints routingHints) {
        if (prompt == null || prompt.isBlank()) throw new IllegalArgumentException("video prompt must not be blank");
        this.prompt = prompt;
        this.seconds = seconds;
        this.size = size;
        this.inputReferenceUrl = inputReferenceUrl;
        this.inputReference = null;
        this.routingHints = routingHints == null ? RoutingHints.none() : routingHints;
    }

    /** Creates an instance of this API type. */
    public VideoRequest(String prompt, String seconds, String size, VideoInput inputReference,
                        RoutingHints routingHints) {
        if (prompt == null || prompt.isBlank()) throw new IllegalArgumentException("video prompt must not be blank");
        this.prompt = prompt;
        this.seconds = seconds;
        this.size = size;
        this.inputReferenceUrl = null;
        this.inputReference = inputReference;
        this.routingHints = routingHints == null ? RoutingHints.none() : routingHints;
    }

    /** Implements the API contract. */
    @Override
    /** Executes the associated model API operation. */
    public int estimatedInputTokens() {
        return Math.max(1, (prompt.length() + 3) / 4);
    }
}
