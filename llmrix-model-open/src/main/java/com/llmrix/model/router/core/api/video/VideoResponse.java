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
public final class VideoResponse implements RoutedResponse<VideoResponse> {
    /** Provider job identifier. */
    private final String id;
    /** Provider resource type. */
    private final String object;
    /** Current lifecycle status. */
    private final String status;
    /** Requested provider model. */
    private final String model;
    /** Value of the `createdAt` property. */
    private final Long createdAt;
    /** Value of the `completedAt` property. */
    private final Long completedAt;
    /** Value of the `expiresAt` property. */
    private final Long expiresAt;
    /** Value of the `progress` property. */
    private final Integer progress;
    /** Value of the `error` property. */
    private final String error;
    /** Value of the `modelId` property. */
    private final String modelId;
    /** Value of the `usage` property. */
    private final Usage usage;

    /** Creates an instance of this API type. */
    public VideoResponse(String id, String object, String status, String model, Long createdAt,
                         Long completedAt, Long expiresAt, Integer progress, String error,
                         String modelId, Usage usage) {
        this.id = id;
        this.object = object;
        this.status = status;
        this.model = model;
        this.createdAt = createdAt;
        this.completedAt = completedAt;
        this.expiresAt = expiresAt;
        this.progress = progress;
        this.error = error;
        this.modelId = modelId;
        this.usage = usage == null ? Usage.UNKNOWN : usage;
    }

    /** Implements the API contract. */
    @Override
    /** Executes the associated model API operation. */
    public VideoResponse routedBy(String targetId) {
        return new VideoResponse(id, object, status, model, createdAt, completedAt, expiresAt,
                progress, error, targetId, usage);
    }
}
