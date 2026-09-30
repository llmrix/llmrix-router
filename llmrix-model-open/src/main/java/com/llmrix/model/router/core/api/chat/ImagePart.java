package com.llmrix.model.router.core.api.chat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

/** Public data type used by the model routing API. */
@Getter
@EqualsAndHashCode
@Accessors(fluent = true)
public final class ImagePart implements ContentPart {
    /** Value of the `url` property. */
    private final String url;
    /** Value of the `detail` property. */
    private final String detail;

    /** Creates an instance of this API type. */
    public ImagePart(String url, String detail) {
        if (url == null || url.isBlank()) throw new IllegalArgumentException("image url must not be blank");
        if (detail != null && !detail.equals("auto") && !detail.equals("low") && !detail.equals("high")) {
            throw new IllegalArgumentException("image detail must be auto, low or high");
        }
        this.url = url;
        this.detail = detail;
    }

    /** Creates an instance of this API type. */
    public ImagePart(String url) {
        this(url, null);
    }

}
