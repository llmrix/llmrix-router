package com.llmrix.model.router.core.api.image;

import lombok.Value;
import lombok.experimental.Accessors;

/** Public data type used by the model routing API. */
@Value
@Accessors(fluent = true)
public class ImageData {
    /** Public value exposed by this API type. */
    String url;
    /** Public value exposed by this API type. */
    String base64;
    /** Public value exposed by this API type. */
    String revisedPrompt;
}
