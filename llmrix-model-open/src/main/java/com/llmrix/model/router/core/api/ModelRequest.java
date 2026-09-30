package com.llmrix.model.router.core.api;

import com.llmrix.model.router.core.routing.RoutingHints;

/**
 * Common information required by the routing engine for any model operation.
 */
public interface ModelRequest {
    /** Returns hints used by the router when selecting a target. */
    RoutingHints routingHints();

    /** Returns the estimated number of input tokens for budgeting. */
    int estimatedInputTokens();

    /** Returns the estimated output-token budget, or zero when unknown. */
    default int estimatedOutputTokens() {
        return 0;
    }
}
