package com.llmrix.model.orion.spring.boot.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;

@ConfigurationProperties("llmrix.model.orion")
@Getter
@Setter
public class OrionModelClientProperties {
    private boolean enabled = true;
    private String baseUrl = "http://localhost:8080/v1";
    private String apiKey;
    private String defaultModel;
    private Defaults defaults = new Defaults();
    private Duration connectTimeout = Duration.ofSeconds(10);
    private Duration timeout = Duration.ofSeconds(60);
    private Map<String, String> headers = new LinkedHashMap<>();

    @Getter
    @Setter
    public static class Defaults {
        private String chat;
        private String embedding;
        private String rerank;
        private String audio;
        private String image;
        private String video;

    }
}
