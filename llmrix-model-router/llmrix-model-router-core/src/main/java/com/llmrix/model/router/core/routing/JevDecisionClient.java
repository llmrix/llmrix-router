package com.llmrix.model.router.core.routing;

import com.llmrix.model.router.core.api.ModelRequest;

import java.util.List;
import java.util.Set;
import java.time.Duration;

/**
 * Provider-neutral SPI for a JEV decision service. Implementations own the
 * remote protocol; the router owns candidate boundaries and fallback safety.
 */
@FunctionalInterface
public interface JevDecisionClient {
    JevDecision decide(ModelRequest request, Set<String> allowedTags, List<RouteCandidate> candidates);

    /**
     * Timeout-aware hook for remote implementations. The default preserves
     * compatibility with simple clients and leaves timeout enforcement to them.
     */
    default JevDecision decide(ModelRequest request, Set<String> allowedTags,
                               List<RouteCandidate> candidates, Duration timeout) {
        return decide(request, allowedTags, candidates);
    }
}
