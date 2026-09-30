package com.llmrix.model.router.core.api.chat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Objects;

/** Public data type used by the model routing API. */
@Getter
@EqualsAndHashCode
@Accessors(fluent = true)
public final class ToolResultPart implements ContentPart {
    /** Value of the `toolCallId` property. */
    private final String toolCallId;
    /** Value of the `result` property. */
    private final String result;

    /** Creates an instance of this API type. */
    public ToolResultPart(String toolCallId, String result) {
        if (toolCallId == null || toolCallId.isBlank()) {
            throw new IllegalArgumentException("toolCallId must not be blank");
        }
        this.toolCallId = toolCallId;
        this.result = Objects.requireNonNull(result, "result");
    }

}
