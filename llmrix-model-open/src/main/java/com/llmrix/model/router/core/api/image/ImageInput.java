package com.llmrix.model.router.core.api.image;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Arrays;

/** Public data type used by the model routing API. */
@Getter
@EqualsAndHashCode
@Accessors(fluent = true)
public final class ImageInput {
    /** Value of the `data` property. */
    private final byte[] data;
    /** Value of the `filename` property. */
    private final String filename;
    /** Value of the `mediaType` property. */
    private final String mediaType;

    /** Creates an instance of this API type. */
    public ImageInput(byte[] data, String filename, String mediaType) {
        if (data == null || data.length == 0) throw new IllegalArgumentException("image data must not be empty");
        if (filename == null || filename.isBlank())
            throw new IllegalArgumentException("image filename must not be blank");
        this.data = Arrays.copyOf(data, data.length);
        this.filename = filename;
        this.mediaType = mediaType == null || mediaType.isBlank() ? "application/octet-stream" : mediaType;
    }

    /** Executes the associated model API operation. */
    public byte[] data() {
        return Arrays.copyOf(data, data.length);
    }
}
