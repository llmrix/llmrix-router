package com.llmrix.model.router.core.api.chat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Map;

/** Public data type used by the model routing API. */
@Getter
@EqualsAndHashCode
@Accessors(fluent = true)
public final class ResponseFormat {
    public enum Type {TEXT, JSON_OBJECT, JSON_SCHEMA}

    /** Value of the `type` property. */
    private final Type type;
    /** Value of the `name` property. */
    private final String name;
    /** Value of the `description` property. */
    private final String description;
    /** Value of the `schema` property. */
    private final Map<String, Object> schema;
    /** Value of the `strict` property. */
    private final Boolean strict;

    /** Creates an instance of this API type. */
    public ResponseFormat(Type type, String name, String description,
                          Map<String, Object> schema, Boolean strict) {
        if (type == null) throw new IllegalArgumentException("response format type must not be null");
        if (type == Type.JSON_SCHEMA && (name == null || name.isBlank())) {
            throw new IllegalArgumentException("json_schema response format requires a name");
        }
        schema = schema == null ? Map.of() : Map.copyOf(schema);
        this.type = type;
        this.name = name;
        this.description = description;
        this.schema = schema;
        this.strict = strict;
    }

    /** Executes the associated model API operation. */
    public static ResponseFormat text() {
        return new ResponseFormat(Type.TEXT, null, null, Map.of(), null);
    }

    /** Executes the associated model API operation. */
    public static ResponseFormat jsonObject() {
        return new ResponseFormat(Type.JSON_OBJECT, null, null, Map.of(), null);
    }

    /** Executes the associated model API operation. */
    public static ResponseFormat jsonSchema(String name, Map<String, Object> schema, boolean strict) {
        return new ResponseFormat(Type.JSON_SCHEMA, name, null, schema, strict);
    }

}
