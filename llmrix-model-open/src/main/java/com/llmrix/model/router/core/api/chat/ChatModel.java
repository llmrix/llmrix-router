package com.llmrix.model.router.core.api.chat;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Flow;

/** Provider-neutral synchronous and streaming chat completion contract. */
public interface ChatModel {
    /**
     * Generates a complete chat response for the supplied conversation.
     *
     * @param request conversation messages and generation options
     * @return the completed assistant response
     */
    ChatResponse chat(ChatRequest request);

    /**
     * Generates a response for a single user message.
     *
     * @param userMessage text of the user message
     * @return the completed assistant response
     */
    default ChatResponse chat(String userMessage) {
        return chat(ChatRequest.user(userMessage));
    }

    /**
     * Starts asynchronous chat generation.
     *
     * @param request conversation messages and generation options
     * @return a stage completed with the assistant response
     */
    default CompletionStage<ChatResponse> chatAsync(ChatRequest request) {
        return CompletableFuture.supplyAsync(() -> chat(request));
    }

    /**
     * Starts streaming chat generation and publishes incremental response chunks.
     *
     * @param request conversation messages and streaming options
     * @return a publisher of response chunks
     * @throws UnsupportedOperationException if streaming is not supported
     */
    default Flow.Publisher<ChatChunk> stream(ChatRequest request) {
        throw new UnsupportedOperationException("streaming is not supported by this model");
    }

    /**
     * Reports whether this adapter has a native streaming protocol.
     *
     * @return {@code true} when native streaming is supported
     */
    default boolean supportsStreaming() { return false; }

    /**
     * Reports whether this adapter accepts tool definitions and tool results.
     *
     * @return {@code true} when tool calling is supported
     */
    default boolean supportsTools() { return false; }

    /**
     * Reports whether this adapter supports structured response formats.
     *
     * @return {@code true} when structured output is supported
     */
    default boolean supportsStructuredOutput() { return false; }

    /**
     * Reports whether prompt-cache hints can be sent to the provider.
     *
     * @return {@code true} when prompt caching is supported
     */
    default boolean supportsPromptCache() { return false; }
}
