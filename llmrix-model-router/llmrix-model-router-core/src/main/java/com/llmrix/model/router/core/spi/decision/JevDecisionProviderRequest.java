package com.llmrix.model.router.core.spi.decision;

import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Accessors(fluent = true)
public final class JevDecisionProviderRequest {
    private final String providerId;
    private final String baseUrl;
    private final String apiKey;
    private final String authenticator;
    private final Map<String, Object> options;

    public JevDecisionProviderRequest(String providerId, String baseUrl, String apiKey,
                                      String authenticator, Map<String, Object> options) {
        this.providerId = providerId;
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
        this.authenticator = authenticator;
        this.options = options == null || options.isEmpty()
                ? Collections.emptyMap()
                : Collections.unmodifiableMap(new LinkedHashMap<>(options));
    }
}
