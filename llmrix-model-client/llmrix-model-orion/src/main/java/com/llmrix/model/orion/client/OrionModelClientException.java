package com.llmrix.model.orion.client;

import lombok.Getter;
import lombok.experimental.Accessors;

/** A transport or protocol failure returned by a remote LLM Router server. */
@Getter
@Accessors(fluent = true)
public final class OrionModelClientException extends RuntimeException {
    private final int statusCode;

    public OrionModelClientException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public OrionModelClientException(String message, Throwable cause) {
        super(message, cause);
        this.statusCode = -1;
    }

}
