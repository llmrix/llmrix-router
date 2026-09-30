package com.llmrix.model.router.core.api.chat;

import com.llmrix.model.router.core.api.Usage;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.List;

/** Public data type used by the model routing API. */
@Getter
@EqualsAndHashCode
@Accessors(fluent = true)
public final class ChatChunk {
    /** Value of the `text` property. */
    private final String text;
    /** Value of the `finished` property. */
    private final boolean finished;
    /** Value of the `usage` property. */
    private final Usage usage;
    /** Value of the `toolCallDeltas` property. */
    private final List<ToolCallDelta> toolCallDeltas;
    /** Value of the `finishReason` property. */
    private final String finishReason;

    /** Creates an instance of this API type. */
    public ChatChunk(String text, boolean finished, Usage usage) {
        this(text, finished, usage, List.of(), finished ? "stop" : null);
    }

    /** Creates an instance of this API type. */
    public ChatChunk(String text, boolean finished, Usage usage, List<ToolCallDelta> toolCallDeltas) {
        this(text, finished, usage, toolCallDeltas, finished ? "stop" : null);
    }

    /** Creates an instance of this API type. */
    public ChatChunk(String text, boolean finished, Usage usage,
                     List<ToolCallDelta> toolCallDeltas, String finishReason) {
        this.text = text == null ? "" : text;
        this.finished = finished;
        this.usage = usage == null ? Usage.UNKNOWN : usage;
        this.toolCallDeltas = toolCallDeltas == null ? List.of() : List.copyOf(toolCallDeltas);
        this.finishReason = finishReason;
    }

}
