package com.llmrix.model.router.core.api.chat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

/** Public data type used by the model routing API. */
@Getter
@EqualsAndHashCode
@Accessors(fluent = true)
public final class ToolChoice {
    public enum Mode {AUTO, NONE, REQUIRED, NAMED}

    /** Value of the `mode` property. */
    private final Mode mode;
    /** Value of the `name` property. */
    private final String name;

    /** Creates an instance of this API type. */
    public ToolChoice(Mode mode, String name) {
        if (mode == null) throw new IllegalArgumentException("tool choice mode must not be null");
        if (mode == Mode.NAMED && (name == null || name.isBlank())) {
            throw new IllegalArgumentException("named tool choice requires a name");
        }
        if (mode != Mode.NAMED && name != null) {
            throw new IllegalArgumentException("only named tool choice accepts a name");
        }
        this.mode = mode;
        this.name = name;
    }

    /** Executes the associated model API operation. */
    public static ToolChoice auto() {
        return new ToolChoice(Mode.AUTO, null);
    }

    /** Executes the associated model API operation. */
    public static ToolChoice none() {
        return new ToolChoice(Mode.NONE, null);
    }

    /** Executes the associated model API operation. */
    public static ToolChoice required() {
        return new ToolChoice(Mode.REQUIRED, null);
    }

    /** Executes the associated model API operation. */
    public static ToolChoice named(String name) {
        return new ToolChoice(Mode.NAMED, name);
    }

}
