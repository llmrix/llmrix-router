package com.llmrix.model.router.core.api.chat;

import lombok.Value;
import lombok.experimental.Accessors;

/** Public data type used by the model routing API. */
@Value
@Accessors(fluent = true)
public class StreamOptions {
    /** Default options that include usage in streaming responses. */
    public static final StreamOptions DEFAULT = new StreamOptions(true);

    /** Public value exposed by this API type. */
    boolean includeUsage;
}
