package com.llmrix.model.router.core.routing;

import com.llmrix.model.router.core.api.ModelRequest;
import com.llmrix.model.router.core.api.chat.ChatRequest;
import com.llmrix.model.router.core.model.ModelTarget;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.time.Duration;

/**
 * Adds JEV semantic preference to an existing strategy without expanding the
 * statically configured candidate set.
 */
public final class JevRoutingStrategy implements RoutingStrategy {
    public static final String ROUTING_TAGS_METADATA = "routing-tags";

    private final JevDecisionClient client;
    private final RoutingStrategy fallback;
    private final double minimumConfidence;
    private final Duration timeout;

    public JevRoutingStrategy(JevDecisionClient client, RoutingStrategy fallback, double minimumConfidence) {
        this(client, fallback, minimumConfidence, Duration.ofSeconds(2));
    }

    public JevRoutingStrategy(JevDecisionClient client, RoutingStrategy fallback,
                              double minimumConfidence, Duration timeout) {
        this.client = Objects.requireNonNull(client, "client");
        this.fallback = Objects.requireNonNull(fallback, "fallback");
        if (!Double.isFinite(minimumConfidence) || minimumConfidence < 0 || minimumConfidence > 1) {
            throw new IllegalArgumentException("minimumConfidence must be between 0 and 1");
        }
        this.minimumConfidence = minimumConfidence;
        if (timeout == null || timeout.isZero() || timeout.isNegative()) {
            throw new IllegalArgumentException("timeout must be positive");
        }
        this.timeout = timeout;
    }

    @Override
    public ModelTarget select(ModelRequest request, List<RouteCandidate> candidates) {
        if (!(request instanceof ChatRequest)) {
            return fallback.select(request, candidates == null ? List.of() : candidates);
        }
        if (candidates == null || candidates.isEmpty()) {
            return fallback.select(request, candidates == null ? List.of() : candidates);
        }
        Set<String> allowedTags = candidates.stream()
                .flatMap(candidate -> routingTags(candidate.target()).stream())
                .collect(java.util.stream.Collectors.toCollection(LinkedHashSet::new));
        if (allowedTags.isEmpty()) return fallback.select(request, candidates);

        try {
            JevDecision decision = client.decide(request, Set.copyOf(allowedTags), List.copyOf(candidates), timeout);
            if (decision == null || decision.confidence() < minimumConfidence) {
                return fallback.select(request, candidates);
            }
            Set<String> preferred = decision.preferredTags().stream()
                    .map(JevRoutingStrategy::normalize)
                    .collect(java.util.stream.Collectors.toUnmodifiableSet());
            if (!allowedTags.containsAll(preferred)) return fallback.select(request, candidates);
            if (preferred.isEmpty()) return fallback.select(request, candidates);

            List<RouteCandidate> matched = new ArrayList<>();
            for (RouteCandidate candidate : candidates) {
                if (routingTags(candidate.target()).stream().anyMatch(preferred::contains)) matched.add(candidate);
            }
            return matched.isEmpty() ? fallback.select(request, candidates) : fallback.select(request, List.copyOf(matched));
        } catch (RuntimeException ignored) {
            // JEV is an optional policy enhancement; its failure must not affect baseline routing.
            return fallback.select(request, candidates);
        }
    }

    static Set<String> routingTags(ModelTarget target) {
        String raw = target.metadata().get(ROUTING_TAGS_METADATA);
        if (raw == null || raw.isBlank()) return Set.of();
        Set<String> tags = new LinkedHashSet<>();
        for (String value : raw.split(",")) {
            String normalized = normalize(value);
            if (!normalized.isEmpty()) tags.add(normalized);
        }
        return Set.copyOf(tags);
    }

    private static String normalize(String value) {
        return value == null ? "" : value.trim().toLowerCase(Locale.ROOT);
    }
}
