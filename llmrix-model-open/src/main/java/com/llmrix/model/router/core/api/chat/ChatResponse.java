package com.llmrix.model.router.core.api.chat;

import com.llmrix.model.router.core.api.RoutedResponse;
import com.llmrix.model.router.core.api.Usage;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/** Public data type used by the model routing API. */
@Getter
@EqualsAndHashCode
@Accessors(fluent = true)
public final class ChatResponse implements RoutedResponse<ChatResponse> {
    /** Generated assistant text. */
    private final String text;
    /** Provider model identifier. */
    private final String modelId;
    /** Reported token usage. */
    private final Usage usage;
    /** Value of the `metadata` property. */
    private final Map<String, Object> metadata;
    /** Value of the `toolCalls` property. */
    private final List<ToolCallPart> toolCalls;
    /** Value of the `finishReason` property. */
    private final String finishReason;

    /** Creates an instance of this API type. */
    public ChatResponse(String text, String modelId, Usage usage, Map<String, Object> metadata,
                        List<ToolCallPart> toolCalls, String finishReason) {
        this.text = Objects.requireNonNull(text, "text");
        this.modelId = modelId;
        this.usage = usage == null ? Usage.UNKNOWN : usage;
        this.metadata = metadata == null ? Map.of() : Map.copyOf(metadata);
        this.toolCalls = toolCalls == null ? List.of() : List.copyOf(toolCalls);
        this.finishReason = finishReason;
    }

    /** Creates an instance of this API type. */
    public ChatResponse(String text, String modelId, Usage usage, Map<String, Object> metadata) {
        this(text, modelId, usage, metadata, List.of(), "stop");
    }

    /** Creates an instance of this API type. */
    public ChatResponse(String text, String modelId, Usage usage, Map<String, Object> metadata,
                        List<ToolCallPart> toolCalls) {
        this(text, modelId, usage, metadata, toolCalls, toolCalls == null || toolCalls.isEmpty() ? "stop" : "tool_calls");
    }

    /** Executes the associated model API operation. */
    public static ChatResponse of(String text) {
        return new ChatResponse(text, null, Usage.UNKNOWN, Map.of(), List.of(), "stop");
    }

    /** Implements the API contract. */
    @Override
    /** Executes the associated model API operation. */
    public ChatResponse routedBy(String candidateId) {
        return new ChatResponse(text, candidateId, usage, metadata, toolCalls, finishReason);
    }

    /** Executes the associated model API operation. */
    public Message assistantMessage() {
        if (toolCalls.isEmpty()) return Message.assistant(text);
        java.util.ArrayList<ContentPart> parts = new java.util.ArrayList<>();
        if (!text.isEmpty()) parts.add(new TextPart(text));
        parts.addAll(toolCalls);
        return new Message("assistant", parts);
    }
}
