package com.llmrix.model.router.core.api.chat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

/** Public data type used by the model routing API. */
@Getter
@EqualsAndHashCode
@Accessors(fluent = true)
public final class ToolCallDelta {
    /** Value of the `index` property. */
    private final int index;
    /** Value of the `id` property. */
    private final String id;
    /** Value of the `name` property. */
    private final String name;
    /** Value of the `arguments` property. */
    private final String arguments;

    /** Creates an instance of this API type. */
    public ToolCallDelta(int index, String id, String name, String arguments) {
        if (index < 0) throw new IllegalArgumentException("tool call index must be >= 0");
        if (id != null && id.isBlank()) id = null;
        if (name != null && name.isBlank()) name = null;
        if (arguments == null) arguments = "";
        this.index = index;
        this.id = id;
        this.name = name;
        this.arguments = arguments;
    }

}
