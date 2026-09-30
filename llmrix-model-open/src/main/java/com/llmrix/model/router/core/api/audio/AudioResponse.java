package com.llmrix.model.router.core.api.audio;

import com.llmrix.model.router.core.api.RoutedResponse;
import com.llmrix.model.router.core.api.Usage;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Arrays;

/** Public data type used by the model routing API. */
@Getter
@EqualsAndHashCode
@Accessors(fluent = true)
public final class AudioResponse implements RoutedResponse<AudioResponse> {
    /** Value of the `data` property. */
    private final byte[] data;
    /** Value of the `mediaType` property. */
    private final String mediaType;
    /** Value of the `modelId` property. */
    private final String modelId;
    /** Value of the `usage` property. */
    private final Usage usage;

    /** Creates an instance of this API type. */
    public AudioResponse(byte[] data, String mediaType, String modelId, Usage usage) {
        this.data = data == null ? new byte[0] : Arrays.copyOf(data, data.length);
        this.mediaType = mediaType == null ? "application/octet-stream" : mediaType;
        this.modelId = modelId;
        this.usage = usage == null ? Usage.UNKNOWN : usage;
    }

    /** Executes the associated model API operation. */
    public byte[] data() {
        return Arrays.copyOf(data, data.length);
    }

    /** Implements the API contract. */
    @Override
    /** Executes the associated model API operation. */
    public AudioResponse routedBy(String targetId) {
        return new AudioResponse(data, mediaType, targetId, usage);
    }
}
