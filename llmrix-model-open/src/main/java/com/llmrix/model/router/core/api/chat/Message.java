package com.llmrix.model.router.core.api.chat;

import java.util.List;
import java.util.Objects;

/** A role-labelled chat message containing one or more content parts. */
public final class Message {
    /** Value of the `role` property. */
    private final String role;
    /** Value of the `contents` property. */
    private final List<ContentPart> contents;

    /** Creates an instance of this API type. */
    public Message(String role, String content) {
        this(role, List.of(new TextPart(content)));
    }

    /** Creates an instance of this API type. */
    public Message(String role, List<? extends ContentPart> contents) {
        if (role == null || role.isBlank()) throw new IllegalArgumentException("role must not be blank");
        Objects.requireNonNull(contents, "contents");
        if (contents.isEmpty()) throw new IllegalArgumentException("contents must not be empty");
        long toolResults = contents.stream().filter(ToolResultPart.class::isInstance).count();
        long toolCalls = contents.stream().filter(ToolCallPart.class::isInstance).count();
        if (toolResults > 0 && (!"tool".equals(role) || contents.size() != 1)) {
            throw new IllegalArgumentException("tool result must be the only content of a tool message");
        }
        if ("tool".equals(role) && toolResults != 1) {
            throw new IllegalArgumentException("tool message requires exactly one tool result");
        }
        if (toolCalls > 0 && !"assistant".equals(role)) {
            throw new IllegalArgumentException("tool calls require an assistant message");
        }
        this.role = role;
        this.contents = List.copyOf(contents);
    }

    /** Executes the associated model API operation. */
    public String role() {
        return role;
    }

    /** Executes the associated model API operation. */
    public List<ContentPart> contents() {
        return contents;
    }

    /** Executes the associated model API operation. */
    public boolean textOnly() {
        return contents.stream().allMatch(TextPart.class::isInstance);
    }

    /** Executes the associated model API operation. */
    public String content() {
        return contents.stream()
                .filter(TextPart.class::isInstance)
                .map(TextPart.class::cast)
                .map(TextPart::text)
                .reduce("", String::concat);
    }

    /** Executes the associated model API operation. */
    public static Message system(String content) {
        return new Message("system", content);
    }

    /** Executes the associated model API operation. */
    public static Message user(String content) {
        return new Message("user", content);
    }

    /** Executes the associated model API operation. */
    public static Message user(ContentPart... contents) {
        return new Message("user", List.of(contents));
    }

    /** Executes the associated model API operation. */
    public static Message assistant(String content) {
        return new Message("assistant", content);
    }

    /** Executes the associated model API operation. */
    public static Message assistant(ToolCallPart... toolCalls) {
        return new Message("assistant", List.of(toolCalls));
    }

    /** Executes the associated model API operation. */
    public static Message tool(String toolCallId, String result) {
        return new Message("tool", List.of(new ToolResultPart(toolCallId, result)));
    }

    /** Implements the API contract. */
    @Override
    /** Executes the associated model API operation. */
    public boolean equals(Object other) {
        return other instanceof Message message && role.equals(message.role) && contents.equals(message.contents);
    }

    /** Implements the API contract. */
    @Override
    /** Executes the associated model API operation. */
    public int hashCode() {
        return Objects.hash(role, contents);
    }

    /** Implements the API contract. */
    @Override
    /** Executes the associated model API operation. */
    public String toString() {
        return "Message[role=" + role + ", contents=" + contents + "]";
    }
}
