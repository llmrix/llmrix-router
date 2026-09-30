package com.llmrix.model.router.core.routing;

import com.llmrix.model.router.core.api.chat.ChatRequest;
import com.llmrix.model.router.core.api.chat.ChatResponse;
import com.llmrix.model.router.core.model.ModelTarget;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JevRoutingStrategyTest {
    @Test
    void restrictsPreferenceToConfiguredCandidateTagsAndUsesFallbackStrategy() {
        ModelTarget general = target("general", "general", 1);
        ModelTarget coding = target("coding", "coding,reasoning", 10);
        AtomicReference<Set<String>> allowed = new AtomicReference<>();
        JevRoutingStrategy strategy = new JevRoutingStrategy((request, tags, candidates) -> {
            allowed.set(tags);
            return new JevDecision(List.of("coding"), 0.95);
        }, Strategies.priority(), 0.70);

        ModelTarget selected = strategy.select(ChatRequest.user("review Java"), List.of(
                new RouteCandidate(general, true, 0, 0),
                new RouteCandidate(coding, true, 0, 0)));

        assertEquals("coding", selected.id());
        assertEquals(Set.of("general", "coding", "reasoning"), allowed.get());
    }

    @Test
    void fallsBackWhenConfidenceIsTooLowOrDecisionFails() {
        ModelTarget first = target("first", "general", 1);
        ModelTarget second = target("second", "coding", 2);
        List<RouteCandidate> candidates = List.of(
                new RouteCandidate(first, true, 0, 0),
                new RouteCandidate(second, true, 0, 0));

        JevRoutingStrategy lowConfidence = new JevRoutingStrategy(
                (request, tags, available) -> new JevDecision(List.of("coding"), 0.2),
                Strategies.priority(), 0.70);
        assertEquals("first", lowConfidence.select(ChatRequest.user("hello"), candidates).id());

        JevRoutingStrategy failing = new JevRoutingStrategy(
                (request, tags, available) -> { throw new IllegalStateException("jev unavailable"); },
                Strategies.priority(), 0.70);
        assertEquals("first", failing.select(ChatRequest.user("hello"), candidates).id());
    }

    @Test
    void ignoresUnknownPreferredTagsAndFallsBack() {
        ModelTarget target = target("general", "general", 1);
        JevRoutingStrategy strategy = new JevRoutingStrategy(
                (request, tags, candidates) -> new JevDecision(List.of("unknown"), 0.99),
                Strategies.priority(), 0.70);

        assertEquals("general", strategy.select(ChatRequest.user("hello"),
                List.of(new RouteCandidate(target, true, 0, 0))).id());
        assertTrue(target.metadata().containsKey(JevRoutingStrategy.ROUTING_TAGS_METADATA));
    }

    private static ModelTarget target(String id, String tags, int priority) {
        return ModelTarget.builder(id, request -> ChatResponse.of(id))
                .priority(priority)
                .metadata(Map.of(JevRoutingStrategy.ROUTING_TAGS_METADATA, tags))
                .build();
    }
}
