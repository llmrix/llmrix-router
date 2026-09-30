package com.llmrix.model.router.core.api.chat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

/** Public data type used by the model routing API. */
@Getter
@EqualsAndHashCode
@Accessors(fluent = true)
public final class VideoPart implements ContentPart {
    /** Value of the `url` property. */
    private final String url;

    /** Creates an instance of this API type. */
    public VideoPart(String url) {
        if (url == null || url.isBlank()) throw new IllegalArgumentException("video url must not be blank");
        this.url = url;
    }
}
