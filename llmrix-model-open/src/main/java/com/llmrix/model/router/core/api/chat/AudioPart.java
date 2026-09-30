package com.llmrix.model.router.core.api.chat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

/** Public data type used by the model routing API. */
@Getter
@EqualsAndHashCode
@Accessors(fluent = true)
public final class AudioPart implements ContentPart {
    /** Value of the `data` property. */
    private final String data;
    /** Value of the `format` property. */
    private final String format;

    /** Creates an instance of this API type. */
    public AudioPart(String data, String format) {
        if (data == null || data.isBlank()) throw new IllegalArgumentException("audio data must not be blank");
        if (format == null || format.isBlank()) throw new IllegalArgumentException("audio format must not be blank");
        this.data = data;
        this.format = format;
    }

}
