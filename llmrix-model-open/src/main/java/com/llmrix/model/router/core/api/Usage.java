package com.llmrix.model.router.core.api;

import lombok.Value;
import lombok.experimental.Accessors;

/** Token usage counters returned by a model operation. */
@Value
@Accessors(fluent = true)
public class Usage {
    /** Sentinel usage value used when a provider does not report usage. */
    public static final Usage UNKNOWN = new Usage(-1, -1);

    /** Number of uncached input tokens. */
    long inputTokens;
    /** Number of generated output tokens. */
    long outputTokens;
    /** Number of input tokens served from cache. */
    long cachedInputTokens;
    /** Number of tokens written to the provider cache. */
    long cacheWriteTokens;
    /** Number of reasoning tokens, when reported. */
    long reasoningTokens;

    /** Creates usage with input and output token counts. */
    public Usage(long inputTokens, long outputTokens) {
        this(inputTokens, outputTokens, 0, 0, 0);
    }

    /** Creates an instance of this API type. */
    public Usage(long inputTokens, long outputTokens, long cachedInputTokens,
                 long cacheWriteTokens, long reasoningTokens) {
        this.inputTokens = inputTokens;
        this.outputTokens = outputTokens;
        this.cachedInputTokens = cachedInputTokens;
        this.cacheWriteTokens = cacheWriteTokens;
        this.reasoningTokens = reasoningTokens;
    }

    /** Returns total input plus output tokens, or -1 when either is unknown. */
    public long totalTokens() {
        return inputTokens < 0 || outputTokens < 0 ? -1 : inputTokens + outputTokens;
    }
}
