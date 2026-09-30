package com.llmrix.model.router.core.api;

import com.llmrix.model.router.core.api.audio.AudioModel;
import com.llmrix.model.router.core.api.chat.ChatModel;
import com.llmrix.model.router.core.api.chat.ChatRequest;
import com.llmrix.model.router.core.api.embedding.EmbeddingModel;
import com.llmrix.model.router.core.api.rerank.RerankModel;
import com.llmrix.model.router.core.api.image.ImageModel;
import com.llmrix.model.router.core.api.video.VideoModel;
import com.llmrix.model.router.core.model.ModelFeature;
import com.llmrix.model.router.core.model.ModelOperation;

import java.util.Objects;
import java.util.Optional;

/**
 * Typed capabilities exposed by one configured provider model.
 */
public final class ModelClient {
    /** Chat capability exposed by the configured model, when available. */
    private final ChatModel chat;
    /** Embedding capability exposed by the configured model, when available. */
    private final EmbeddingModel embeddings;
    /** Reranking capability exposed by the configured model, when available. */
    private final RerankModel rerank;
    /** Audio capability exposed by the configured model, when available. */
    private final AudioModel audio;
    /** Image capability exposed by the configured model, when available. */
    private final ImageModel images;
    /** Video capability exposed by the configured model, when available. */
    private final VideoModel videos;

    private ModelClient(Builder builder) {
        this.chat = builder.chat;
        this.embeddings = builder.embeddings;
        this.rerank = builder.rerank;
        this.audio = builder.audio;
        this.images = builder.images;
        this.videos = builder.videos;
        if (chat == null && embeddings == null && rerank == null && audio == null && images == null && videos == null) {
            throw new IllegalArgumentException("at least one model capability is required");
        }
    }

    /** Creates a builder for a capability set. */
    public static Builder builder() {
        return new Builder();
    }

    /** Creates a client exposing only the supplied chat capability. */
    public static ModelClient chat(ChatModel model) {
        return builder().chat(model).build();
    }

    /** Executes the associated model API operation. */
    public Optional<ChatModel> chat() {
        return Optional.ofNullable(chat);
    }

    /** Executes the associated model API operation. */
    public Optional<EmbeddingModel> embeddings() {
        return Optional.ofNullable(embeddings);
    }

    /** Executes the associated model API operation. */
    public Optional<RerankModel> rerank() {
        return Optional.ofNullable(rerank);
    }

    /** Executes the associated model API operation. */
    public Optional<AudioModel> audio() {
        return Optional.ofNullable(audio);
    }

    /** Executes the associated model API operation. */
    public Optional<ImageModel> images() {
        return Optional.ofNullable(images);
    }

    /** Executes the associated model API operation. */
    public Optional<VideoModel> videos() {
        return Optional.ofNullable(videos);
    }

    /** Executes the associated model API operation. */
    public ChatModel requireChat() {
        return require(chat, "chat");
    }

    /** Executes the associated model API operation. */
    public EmbeddingModel requireEmbeddings() {
        return require(embeddings, "embeddings");
    }

    /** Executes the associated model API operation. */
    public RerankModel requireRerank() {
        return require(rerank, "rerank");
    }

    /** Executes the associated model API operation. */
    public AudioModel requireAudio() {
        return require(audio, "audio");
    }

    /** Executes the associated model API operation. */
    public ImageModel requireImages() {
        return require(images, "images");
    }

    /** Executes the associated model API operation. */
    public VideoModel requireVideos() {
        return require(videos, "videos");
    }

    /** Returns whether this client supports the requested operation. */
    public boolean supports(ModelOperation operation) {
        return switch (operation) {
            case CHAT -> chat != null;
            case EMBEDDINGS -> embeddings != null;
            case RERANK -> rerank != null;
            case AUDIO_TRANSCRIPTION, AUDIO_TRANSLATION, TEXT_TO_SPEECH -> audio != null;
            case IMAGE_GENERATION, IMAGE_EDIT -> images != null;
            case VIDEO_GENERATION -> videos != null;
        };
    }

    /** Returns whether this client supports the requested cross-cutting feature. */
    public boolean supports(ModelFeature feature) {
        return switch (feature) {
            case STREAMING -> chat != null && (chat.supportsStreaming() || overrides(chat, "stream", ChatRequest.class));
            case TOOLS -> chat != null && chat.supportsTools();
            case STRUCTURED_OUTPUT -> chat != null && chat.supportsStructuredOutput();
            case PROMPT_CACHE -> chat != null && chat.supportsPromptCache();
        };
    }

    private static <T> T require(T capability, String name) {
        if (capability == null) throw new UnsupportedOperationException("model does not support " + name);
        return capability;
    }

    private static boolean overrides(Object target, String method, Class<?> parameter) {
        try {
            return target.getClass().getMethod(method, parameter).getDeclaringClass() != ChatModel.class;
        } catch (NoSuchMethodException ignored) {
            return false;
        }
    }

    public static final class Builder {
        private ChatModel chat;
        private EmbeddingModel embeddings;
        private RerankModel rerank;
        private AudioModel audio;
        private ImageModel images;
        private VideoModel videos;

        /** Configures this builder or creates the configured API object. */
        public Builder chat(ChatModel value) {
            chat = Objects.requireNonNull(value);
            return this;
        }

        /** Configures this builder or creates the configured API object. */
        public Builder embeddings(EmbeddingModel value) {
            embeddings = Objects.requireNonNull(value);
            return this;
        }

        /** Configures this builder or creates the configured API object. */
        public Builder rerank(RerankModel value) {
            rerank = Objects.requireNonNull(value);
            return this;
        }

        /** Configures this builder or creates the configured API object. */
        public Builder audio(AudioModel value) {
            audio = Objects.requireNonNull(value);
            return this;
        }

        /** Configures this builder or creates the configured API object. */
        public Builder images(ImageModel value) {
            images = Objects.requireNonNull(value);
            return this;
        }

        /** Configures this builder or creates the configured API object. */
        public Builder videos(VideoModel value) {
            videos = Objects.requireNonNull(value);
            return this;
        }

        /** Configures this builder or creates the configured API object. */
        public ModelClient build() {
            return new ModelClient(this);
        }
    }
}
