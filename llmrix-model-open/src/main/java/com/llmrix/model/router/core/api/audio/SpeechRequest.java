package com.llmrix.model.router.core.api.audio;

import com.llmrix.model.router.core.api.ModelRequest;
import com.llmrix.model.router.core.routing.RoutingHints;
import lombok.Getter;
import lombok.experimental.Accessors;

/** Public data type used by the model routing API. */
@Getter
@Accessors(fluent = true)
public final class SpeechRequest implements ModelRequest {
    /** Value of the `input` property. */
    private final String input;
    /** Value of the `voice` property. */
    private final String voice;
    /** Value of the `responseFormat` property. */
    private final String responseFormat;
    /** Value of the `speed` property. */
    private final Double speed;
    /** Value of the `instructions` property. */
    private final String instructions;
    /** Value of the `routingHints` property. */
    private final RoutingHints routingHints;

    /** Creates an instance of this API type. */
    public SpeechRequest(String input, String voice, String responseFormat, Double speed,
                         String instructions, RoutingHints routingHints) {
        if (input == null || input.isBlank()) throw new IllegalArgumentException("speech input must not be blank");
        if (voice == null || voice.isBlank()) throw new IllegalArgumentException("voice must not be blank");
        if (speed != null && (!Double.isFinite(speed) || speed < 0.25 || speed > 4)) {
            throw new IllegalArgumentException("speed must be between 0.25 and 4");
        }
        this.input = input;
        this.voice = voice;
        this.responseFormat = responseFormat == null || responseFormat.isBlank() ? "mp3" : responseFormat;
        this.speed = speed;
        this.instructions = instructions;
        this.routingHints = routingHints == null ? RoutingHints.none() : routingHints;
    }

    /** Implements the API contract. */
    @Override
    /** Executes the associated model API operation. */
    public int estimatedInputTokens() {
        return Math.max(1, (input.length() + 3) / 4);
    }
}
