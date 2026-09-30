package com.llmrix.model.router.core.api.chat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.List;

/** Public data type used by the model routing API. */
@Getter
@EqualsAndHashCode
@Accessors(fluent = true)
public final class GenerationOptions {
    /** Value of the `temperature` property. */
    private final Double temperature;
    /** Value of the `topP` property. */
    private final Double topP;
    /** Value of the `maxOutputTokens` property. */
    private final Integer maxOutputTokens;
    /** Value of the `stop` property. */
    private final List<String> stop;
    /** Value of the `seed` property. */
    private final Long seed;
    /** Value of the `candidateCount` property. */
    private final Integer candidateCount;
    /** Value of the `logprobs` property. */
    private final Boolean logprobs;
    /** Value of the `user` property. */
    private final String user;

    public static final GenerationOptions DEFAULT = new GenerationOptions(null, null, null, List.of(), null, null, null, null);

    /** Creates an instance of this API type. */
    public GenerationOptions(Double temperature, Double topP, Integer maxOutputTokens, List<String> stop) {
        this(temperature, topP, maxOutputTokens, stop, null, null, null, null);
    }

    /** Creates an instance of this API type. */
    public GenerationOptions(Double temperature, Double topP, Integer maxOutputTokens, List<String> stop,
                             Long seed, Integer candidateCount, Boolean logprobs, String user) {
        if (temperature != null && (!Double.isFinite(temperature) || temperature < 0 || temperature > 2)) {
            throw new IllegalArgumentException("temperature must be between 0 and 2");
        }
        if (topP != null && (!Double.isFinite(topP) || topP <= 0 || topP > 1)) {
            throw new IllegalArgumentException("topP must be between 0 (exclusive) and 1");
        }
        if (maxOutputTokens != null && maxOutputTokens < 1) {
            throw new IllegalArgumentException("maxOutputTokens must be > 0");
        }
        stop = stop == null ? List.of() : List.copyOf(stop);
        if (stop.stream().anyMatch(value -> value == null || value.isEmpty())) {
            throw new IllegalArgumentException("stop values must not be null or empty");
        }
        if (candidateCount != null && candidateCount < 1) {
            throw new IllegalArgumentException("candidateCount must be > 0");
        }
        if (user != null && user.isBlank()) throw new IllegalArgumentException("user must not be blank");
        this.temperature = temperature;
        this.topP = topP;
        this.maxOutputTokens = maxOutputTokens;
        this.stop = stop;
        this.seed = seed;
        this.candidateCount = candidateCount;
        this.logprobs = logprobs;
        this.user = user;
    }

    /** Executes the associated model API operation. */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Double temperature;
        private Double topP;
        private Integer maxOutputTokens;
        private List<String> stop = List.of();
        private Long seed;
        private Integer candidateCount;
        private Boolean logprobs;
        private String user;

        /** Configures this builder or creates the configured API object. */
        public Builder temperature(Double value) {
            temperature = value;
            return this;
        }

        /** Configures this builder or creates the configured API object. */
        public Builder topP(Double value) {
            topP = value;
            return this;
        }

        /** Configures this builder or creates the configured API object. */
        public Builder maxOutputTokens(Integer value) {
            maxOutputTokens = value;
            return this;
        }

        /** Configures this builder or creates the configured API object. */
        public Builder stop(String... values) {
            stop = List.of(values);
            return this;
        }

        /** Configures this builder or creates the configured API object. */
        public Builder seed(Long value) {
            seed = value;
            return this;
        }

        /** Configures this builder or creates the configured API object. */
        public Builder candidateCount(Integer value) {
            candidateCount = value;
            return this;
        }

        /** Configures this builder or creates the configured API object. */
        public Builder logprobs(Boolean value) {
            logprobs = value;
            return this;
        }

        /** Configures this builder or creates the configured API object. */
        public Builder user(String value) {
            user = value;
            return this;
        }

        /** Builds immutable generation options from the configured values. */
        public GenerationOptions build() {
            return new GenerationOptions(
                    temperature, topP, maxOutputTokens, stop, seed, candidateCount, logprobs, user);
        }
    }
}
