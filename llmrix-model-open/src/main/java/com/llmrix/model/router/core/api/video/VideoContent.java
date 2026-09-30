package com.llmrix.model.router.core.api.video;

import com.llmrix.model.router.core.api.RoutedResponse;
import com.llmrix.model.router.core.api.Usage;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

/** Public data type used by the model routing API. */
@Getter
@EqualsAndHashCode
@Accessors(fluent = true)
public final class VideoContent implements RoutedResponse<VideoContent> {
    /** Value of the `data` property. */
    private final byte[] data;
    /** Value of the `mediaType` property. */
    private final String mediaType;
    /** Value of the `modelId` property. */
    private final String modelId;
    /** Value of the `usage` property. */
    private final Usage usage;

    /** Creates an instance of this API type. */
    public VideoContent(byte[] data, String mediaType, String modelId, Usage usage) {
        if (data == null) throw new IllegalArgumentException("video content must not be null");
        this.data = java.util.Arrays.copyOf(data, data.length);
        this.mediaType = mediaType == null ? "video/mp4" : mediaType;
        this.modelId = modelId;
        this.usage = usage == null ? Usage.UNKNOWN : usage;
    }

    /** Executes the associated model API operation. */
    public byte[] data() {
        return java.util.Arrays.copyOf(data, data.length);
    }

    /** Implements the API contract. */
    @Override
    /** Executes the associated model API operation. */
    public VideoContent routedBy(String targetId) {
        return new VideoContent(data, mediaType, targetId, usage);
    }
}
