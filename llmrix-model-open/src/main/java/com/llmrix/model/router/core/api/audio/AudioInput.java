package com.llmrix.model.router.core.api.audio;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Arrays;

/** Immutable audio input bytes and their media metadata. */
@Getter
@EqualsAndHashCode
@Accessors(fluent = true)
public final class AudioInput {
    /** Raw audio bytes. */
    private final byte[] data;
    /** Original file name supplied with the bytes. */
    private final String filename;
    /** MIME type of the audio bytes. */
    private final String mediaType;

    /** Creates an audio input and defensively copies the supplied bytes. */
    public AudioInput(byte[] data, String filename, String mediaType) {
        if (data == null || data.length == 0) throw new IllegalArgumentException("audio data must not be empty");
        if (filename == null || filename.isBlank())
            throw new IllegalArgumentException("audio filename must not be blank");
        this.data = Arrays.copyOf(data, data.length);
        this.filename = filename;
        this.mediaType = mediaType == null || mediaType.isBlank() ? "application/octet-stream" : mediaType;
    }

    /** Returns a defensive copy of the raw audio bytes. */
    public byte[] data() {
        return Arrays.copyOf(data, data.length);
    }
}
