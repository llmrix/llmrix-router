package com.llmrix.model.router.core.api.audio;

import com.llmrix.model.router.core.api.ModelRequest;
import com.llmrix.model.router.core.routing.RoutingHints;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.List;

/** Public data type used by the model routing API. */
@Getter
@Accessors(fluent = true)
public final class AudioTextRequest implements ModelRequest {
    /** Supported text response encodings. */
    public enum ResponseFormat {JSON, TEXT, SRT, VERBOSE_JSON, VTT}

    /** Value of the `input` property. */
    private final AudioInput input;
    /** Value of the `language` property. */
    private final String language;
    /** Value of the `prompt` property. */
    private final String prompt;
    /** Value of the `responseFormat` property. */
    private final ResponseFormat responseFormat;
    /** Value of the `temperature` property. */
    private final Double temperature;
    /** Value of the `timestampGranularities` property. */
    private final List<String> timestampGranularities;
    /** Value of the `routingHints` property. */
    private final RoutingHints routingHints;

    /** Creates an instance of this API type. */
    public AudioTextRequest(AudioInput input, String language, String prompt,
                            ResponseFormat responseFormat, Double temperature,
                            List<String> timestampGranularities, RoutingHints routingHints) {
        if (input == null) throw new IllegalArgumentException("audio input is required");
        if (temperature != null && (!Double.isFinite(temperature) || temperature < 0)) {
            throw new IllegalArgumentException("temperature must be finite and >= 0");
        }
        this.input = input;
        this.language = language;
        this.prompt = prompt;
        this.responseFormat = responseFormat == null ? ResponseFormat.JSON : responseFormat;
        this.temperature = temperature;
        this.timestampGranularities = timestampGranularities == null ? List.of() : List.copyOf(timestampGranularities);
        this.routingHints = routingHints == null ? RoutingHints.none() : routingHints;
    }

    /** Implements the API contract. */
    @Override
    /** Executes the associated model API operation. */
    public int estimatedInputTokens() {
        return Math.max(1, input.data().length / 4);
    }
}
