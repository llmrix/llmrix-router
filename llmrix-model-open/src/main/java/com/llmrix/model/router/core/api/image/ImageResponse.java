package com.llmrix.model.router.core.api.image;

import com.llmrix.model.router.core.api.RoutedResponse;
import com.llmrix.model.router.core.api.Usage;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.List;

/** Public data type used by the model routing API. */
@Getter
@EqualsAndHashCode
@Accessors(fluent = true)
public final class ImageResponse implements RoutedResponse<ImageResponse> {
    /** Creation timestamp supplied by the provider. */
    private final long created;
    /** Generated image entries. */
    private final List<ImageData> data;
    /** Provider model identifier. */
    private final String modelId;
    /** Reported token usage. */
    private final Usage usage;

    /** Creates an instance of this API type. */
    public ImageResponse(long created, List<ImageData> data, String modelId, Usage usage) {
        this.created = created;
        this.data = data == null ? List.of() : List.copyOf(data);
        this.modelId = modelId;
        this.usage = usage == null ? Usage.UNKNOWN : usage;
    }

    /** Implements the API contract. */
    @Override
    /** Executes the associated model API operation. */
    public ImageResponse routedBy(String targetId) {
        return new ImageResponse(created, data, targetId, usage);
    }
}
