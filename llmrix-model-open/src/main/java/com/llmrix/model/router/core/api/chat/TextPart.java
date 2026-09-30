package com.llmrix.model.router.core.api.chat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Objects;

/** Public data type used by the model routing API. */
@Getter
@EqualsAndHashCode
@Accessors(fluent = true)
public final class TextPart implements ContentPart {
    /** Value of the `text` property. */
    private final String text;

    /** Creates an instance of this API type. */
    public TextPart(String text) {
        this.text = Objects.requireNonNull(text, "text");
    }

}
