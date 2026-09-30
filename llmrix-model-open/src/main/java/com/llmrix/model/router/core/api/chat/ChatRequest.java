package com.llmrix.model.router.core.api.chat;

import com.llmrix.model.router.core.api.ModelRequest;
import com.llmrix.model.router.core.routing.RoutingHints;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Request containing messages and generation controls for chat completion. */
public final class ChatRequest implements ModelRequest {
    /** Ordered conversation messages sent to the model. */
    private final List<Message> messages;
    /** Router selection hints. */
    private final RoutingHints routingHints;
    /** Optional caller-provided input token estimate. */
    private final Integer estimatedInputTokens;
    /** Sampling and output length controls. */
    private final GenerationOptions generationOptions;
    /** Tools made available to the model. */
    private final List<ToolDefinition> tools;
    /** Tool invocation policy. */
    private final ToolChoice toolChoice;
    /** Requested response format. */
    private final ResponseFormat responseFormat;
    /** Streaming controls. */
    private final StreamOptions streamOptions;
    /** Optional prompt-cache hints. */
    private final PromptCacheOptions promptCache;

    private ChatRequest(Builder builder) {
        if (builder.messages.isEmpty()) {
            throw new IllegalArgumentException("at least one message is required");
        }
        this.messages = List.copyOf(builder.messages);
        this.routingHints = builder.routingHints == null ? RoutingHints.none() : builder.routingHints;
        this.estimatedInputTokens = builder.estimatedInputTokens;
        this.generationOptions = builder.generationOptions == null ? GenerationOptions.DEFAULT : builder.generationOptions;
        this.tools = List.copyOf(builder.tools);
        this.toolChoice = builder.toolChoice;
        this.responseFormat = builder.responseFormat;
        this.streamOptions = builder.streamOptions == null ? StreamOptions.DEFAULT : builder.streamOptions;
        this.promptCache = builder.promptCache;
        if (toolChoice != null && tools.isEmpty()) {
            throw new IllegalArgumentException("toolChoice requires at least one tool");
        }
        java.util.Set<String> toolNames = new java.util.HashSet<>();
        for (ToolDefinition tool : tools) {
            if (!toolNames.add(tool.name())) throw new IllegalArgumentException("duplicate tool name: " + tool.name());
        }
        if (toolChoice != null && toolChoice.mode() == ToolChoice.Mode.NAMED
                && !toolNames.contains(toolChoice.name())) {
            throw new IllegalArgumentException("toolChoice references unknown tool: " + toolChoice.name());
        }
    }

    /** Executes the associated model API operation. */
    public static ChatRequest user(String message) {
        return builder().userMessage(message).build();
    }

    /** Executes the associated model API operation. */
    public static Builder builder() {
        return new Builder();
    }

    /** Executes the associated model API operation. */
    public List<Message> messages() {
        return messages;
    }

    /** Executes the associated model API operation. */
    public RoutingHints routingHints() {
        return routingHints;
    }

    /** Executes the associated model API operation. */
    public GenerationOptions generationOptions() {
        return generationOptions;
    }

    /** Executes the associated model API operation. */
    public List<ToolDefinition> tools() {
        return tools;
    }

    /** Executes the associated model API operation. */
    public ToolChoice toolChoice() {
        return toolChoice;
    }

    /** Executes the associated model API operation. */
    public ResponseFormat responseFormat() {
        return responseFormat;
    }

    /** Executes the associated model API operation. */
    public StreamOptions streamOptions() {
        return streamOptions;
    }

    /** Executes the associated model API operation. */
    public PromptCacheOptions promptCache() {
        return promptCache;
    }

    /** Executes the associated model API operation. */
    public int estimatedInputTokens() {
        if (estimatedInputTokens != null) {
            return estimatedInputTokens;
        }
        long characters = messages.stream()
                .flatMap(message -> message.contents().stream())
                .mapToLong(ChatRequest::estimatedCharacters)
                .sum();
        characters += tools.stream().mapToLong(tool ->
                tool.name().length()
                        + (tool.description() == null ? 0 : tool.description().length())
                        + tool.parameters().toString().length()).sum();
        if (characters > Integer.MAX_VALUE) return Integer.MAX_VALUE / 4;
        return (int) Math.max(1, (characters + 3) / 4);
    }

    /** Implements the API contract. */
    @Override
    /** Executes the associated model API operation. */
    public int estimatedOutputTokens() {
        Integer configured = generationOptions.maxOutputTokens();
        return configured == null ? 512 : configured;
    }

    private static int estimatedCharacters(ContentPart part) {
        if (part instanceof TextPart text) return text.text().length();
        if (part instanceof ImagePart image) return image.url().length();
        if (part instanceof AudioPart audio) return audio.data().length();
        if (part instanceof VideoPart video) return video.url().length();
        if (part instanceof FilePart file) {
            return (file.url() == null ? file.fileId().length() : file.url().length())
                    + (file.filename() == null ? 0 : file.filename().length());
        }
        if (part instanceof ToolCallPart call) return call.name().length() + call.arguments().length();
        if (part instanceof ToolResultPart result) return result.result().length();
        return 0;
    }

    public static final class Builder {
        private final List<Message> messages = new ArrayList<>();
        private RoutingHints routingHints;
        private Integer estimatedInputTokens;
        private GenerationOptions generationOptions;
        private List<ToolDefinition> tools = List.of();
        private ToolChoice toolChoice;
        private ResponseFormat responseFormat;
        private StreamOptions streamOptions;
        private PromptCacheOptions promptCache;

        /** Configures this builder or creates the configured API object. */
        public Builder message(Message message) {
            messages.add(Objects.requireNonNull(message, "message"));
            return this;
        }

        /** Configures this builder or creates the configured API object. */
        public Builder messages(List<Message> messages) {
            this.messages.clear();
            this.messages.addAll(Objects.requireNonNull(messages, "messages"));
            return this;
        }

        /** Configures this builder or creates the configured API object. */
        public Builder userMessage(String content) {
            return message(Message.user(content));
        }

        /** Configures this builder or creates the configured API object. */
        public Builder systemMessage(String content) {
            return message(Message.system(content));
        }

        /** Configures this builder or creates the configured API object. */
        public Builder assistantMessage(String content) {
            return message(Message.assistant(content));
        }

        /** Configures this builder or creates the configured API object. */
        public Builder routingHints(RoutingHints routingHints) {
            this.routingHints = routingHints;
            return this;
        }

        /** Configures this builder or creates the configured API object. */
        public Builder estimatedInputTokens(int estimatedInputTokens) {
            if (estimatedInputTokens < 0) {
                throw new IllegalArgumentException("estimatedInputTokens must be >= 0");
            }
            this.estimatedInputTokens = estimatedInputTokens;
            return this;
        }

        /** Configures this builder or creates the configured API object. */
        public Builder generationOptions(GenerationOptions value) {
            generationOptions = Objects.requireNonNull(value, "generationOptions");
            return this;
        }

        /** Configures this builder or creates the configured API object. */
        public Builder tools(List<ToolDefinition> values) {
            tools = List.copyOf(Objects.requireNonNull(values, "tools"));
            return this;
        }

        /** Configures this builder or creates the configured API object. */
        public Builder tools(ToolDefinition... values) {
            return tools(List.of(values));
        }

        /** Configures this builder or creates the configured API object. */
        public Builder toolChoice(ToolChoice value) {
            toolChoice = Objects.requireNonNull(value, "toolChoice");
            return this;
        }

        /** Configures this builder or creates the configured API object. */
        public Builder responseFormat(ResponseFormat value) {
            responseFormat = Objects.requireNonNull(value, "responseFormat");
            return this;
        }

        /** Configures this builder or creates the configured API object. */
        public Builder streamOptions(StreamOptions value) {
            streamOptions = Objects.requireNonNull(value, "streamOptions");
            return this;
        }

        /** Configures this builder or creates the configured API object. */
        public Builder promptCache(PromptCacheOptions value) {
            promptCache = Objects.requireNonNull(value, "promptCache");
            return this;
        }

        /** Builds a validated chat request from the configured values. */
        public ChatRequest build() {
            return new ChatRequest(this);
        }
    }
}
