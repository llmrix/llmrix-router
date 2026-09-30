package com.llmrix.model.router.core.api.chat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
/** Public data type used by the model routing API. */
@Getter
@EqualsAndHashCode
@Accessors(fluent = true)
public final class PromptCacheOptions {
    /** Value of the `key` property. */
    private final String key;
    /** Value of the `retention` property. */
    private final String retention;

    /** Creates an instance of this API type. */
    public PromptCacheOptions(String key, String retention) {
        if (key == null || key.isBlank()) throw new IllegalArgumentException("prompt cache key must not be blank");
        if (retention != null && retention.isBlank()) throw new IllegalArgumentException("prompt cache retention must not be blank");
        this.key = key;
        this.retention = retention;
    }

    @Override public String toString() { return "PromptCacheOptions[key=" + key + ", retention=" + retention + "]"; }
}
