package com.llmrix.model.router.integrations.decision;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.llmrix.model.router.core.api.ModelRequest;
import com.llmrix.model.router.core.api.chat.ChatRequest;
import com.llmrix.model.router.core.routing.JevDecision;
import com.llmrix.model.router.core.routing.JevDecisionClient;
import com.llmrix.model.router.core.routing.RouteCandidate;
import com.llmrix.model.router.core.spi.decision.JevDecisionProvider;
import com.llmrix.model.router.core.spi.decision.JevDecisionProviderRequest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Built-in client for TypeSafe Jev's SystemOne evaluation endpoint. */
public final class TypesafeJevDecisionProvider implements JevDecisionProvider {
    public static final String ID = "jev";
    private static final String DEFAULT_BASE_URL = "https://api.typesafe.ai";
    private static final String DEFAULT_MODEL = "jev-latest";

    private final ObjectMapper mapper;
    private final HttpClient httpClient;

    public TypesafeJevDecisionProvider() {
        this(new ObjectMapper(), HttpClient.newHttpClient());
    }

    TypesafeJevDecisionProvider(ObjectMapper mapper, HttpClient httpClient) {
        this.mapper = Objects.requireNonNull(mapper, "mapper");
        this.httpClient = Objects.requireNonNull(httpClient, "httpClient");
    }

    @Override
    public String id() {
        return ID;
    }

    @Override
    public JevDecisionClient create(JevDecisionProviderRequest request) {
        Objects.requireNonNull(request, "request");
        String apiKey = requireText(request.apiKey(), "decision.api-key");
        if (request.authenticator() != null && !request.authenticator().isBlank()
                && !"bearer".equalsIgnoreCase(request.authenticator())) {
            throw new IllegalArgumentException("built-in JEV provider supports only decision.authenticator=bearer");
        }
        String baseUrl = textOr(request.baseUrl(), DEFAULT_BASE_URL).replaceAll("/+$", "");
        String model = optionText(request.options(), "model", DEFAULT_MODEL);
        return new Client(baseUrl, apiKey, model, request.options());
    }

    private final class Client implements JevDecisionClient {
        private final String baseUrl;
        private final String apiKey;
        private final String model;
        private final Map<String, Object> options;

        private Client(String baseUrl, String apiKey, String model, Map<String, Object> options) {
            this.baseUrl = baseUrl;
            this.apiKey = apiKey;
            this.model = model;
            this.options = options;
        }

        @Override
        public JevDecision decide(ModelRequest request, Set<String> allowedTags,
                                  List<RouteCandidate> candidates) {
            return decide(request, allowedTags, candidates, Duration.ofSeconds(30));
        }

        @Override
        public JevDecision decide(ModelRequest request, Set<String> allowedTags,
                                  List<RouteCandidate> candidates, Duration timeout) {
            if (!(request instanceof ChatRequest chat)) return null;
            try {
                ObjectNode body = mapper.createObjectNode();
                body.put("model", model);
                body.set("state", state(chat, candidates));
                ObjectNode questions = body.putObject("questions");
                ObjectNode route = questions.putObject("route");
                route.put("type", "choice");
                route.put("instructions", optionText(options, "instructions",
                        "Choose the routing tag whose models are best suited to answer the user's request. Return only a tag from the options."));
                ObjectNode choices = route.putObject("criteria");
                for (String tag : allowedTags) choices.put(tag, "Models tagged " + tag);

                HttpRequest httpRequest = HttpRequest.newBuilder(URI.create(baseUrl + "/v1/systemone"))
                        .timeout(timeout)
                        .header("Authorization", "Bearer " + apiKey)
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(body)))
                        .build();
                HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() / 100 != 2) {
                    throw new IllegalStateException("JEV decision request failed with HTTP " + response.statusCode());
                }
                JsonNode answer = mapper.readTree(response.body()).path("answers").path("route");
                String choice = answer.path("choice").asText("").trim().toLowerCase(Locale.ROOT);
                double confidence = answer.path("confidence").asDouble(-1);
                if (choice.isEmpty() || !Double.isFinite(confidence)) return null;
                return new JevDecision(List.of(choice), confidence);
            } catch (InterruptedException interrupted) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("JEV decision request interrupted", interrupted);
            } catch (Exception failure) {
                throw new IllegalStateException("JEV decision request failed", failure);
            }
        }
    }

    private JsonNode state(ChatRequest request, List<RouteCandidate> candidates) {
        ObjectNode state = mapper.createObjectNode();
        List<Map<String, String>> messages = new ArrayList<>();
        request.messages().forEach(message -> messages.add(Map.of("role", message.role(), "content", message.content())));
        state.set("messages", mapper.valueToTree(messages));
        List<Map<String, Object>> models = new ArrayList<>();
        for (RouteCandidate candidate : candidates) {
            Map<String, Object> model = new LinkedHashMap<>();
            model.put("id", candidate.id());
            model.put("tags", candidate.target().metadata().getOrDefault("routing-tags", ""));
            model.put("available", candidate.available());
            models.add(model);
        }
        state.set("candidates", mapper.valueToTree(models));
        return state;
    }

    private static String optionText(Map<String, Object> options, String name, String fallback) {
        Object value = options == null ? null : options.get(name);
        return value == null || value.toString().isBlank() ? fallback : value.toString();
    }

    private static String textOr(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value;
    }

    private static String requireText(String value, String name) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(name + " must not be blank");
        return value;
    }
}
