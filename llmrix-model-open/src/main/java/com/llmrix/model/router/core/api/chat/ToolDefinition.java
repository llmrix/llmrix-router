package com.llmrix.model.router.core.api.chat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Map;

/** Public data type used by the model routing API. */
@Getter
@EqualsAndHashCode
@Accessors(fluent = true)
public final class ToolDefinition {
    /** Value of the `name` property. */
    private final String name;
    /** Value of the `description` property. */
    private final String description;
    /** Value of the `parameters` property. */
    private final Map<String, Object> parameters;
    /** Value of the `strict` property. */
    private final boolean strict;

    /** Creates an instance of this API type. */
    public ToolDefinition(String name, String description, Map<String, Object> parameters, boolean strict) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("tool name must not be blank");
        parameters = parameters == null ? Map.of() : Map.copyOf(parameters);
        this.name = name;
        this.description = description;
        this.parameters = parameters;
        this.strict = strict;
    }

    /** Creates an instance of this API type. */
    public ToolDefinition(String name, String description, Map<String, Object> parameters) {
        this(name, description, parameters, false);
    }

}
