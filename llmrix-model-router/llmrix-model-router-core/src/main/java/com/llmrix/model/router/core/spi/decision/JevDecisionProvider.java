package com.llmrix.model.router.core.spi.decision;

import com.llmrix.model.router.core.routing.JevDecisionClient;

/** Creates a JEV decision client from the configured decision integration. */
public interface JevDecisionProvider {
    String id();

    JevDecisionClient create(JevDecisionProviderRequest request);
}
