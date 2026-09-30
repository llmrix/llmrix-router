package com.llmrix.model.router.core.api.video;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Arrays;

/** Immutable video input bytes and their media metadata. */
@Getter
@EqualsAndHashCode
@Accessors(fluent = true)
public final class VideoInput {
    /** Raw video bytes. */
    private final byte[] data;
    /** Original file name supplied with the bytes. */
    private final String filename;
    /** MIME type of the video bytes. */
    private final String mediaType;

    /** Creates a video input and defensively copies the supplied bytes. */
    /** Creates an immutable video input from raw bytes and metadata.
     *
     * @param data raw video bytes; must not be empty
     * @param filename source file name; must not be blank
     * @param mediaType MIME type, or the default binary type when blank
     */
    public VideoInput(byte[] data, String filename, String mediaType) {
        if (data == null || data.length == 0) throw new IllegalArgumentException("video input must not be empty");
        if (filename == null || filename.isBlank()) throw new IllegalArgumentException("video filename must not be blank");
        this.data = Arrays.copyOf(data, data.length);
        this.filename = filename;
        this.mediaType = mediaType == null || mediaType.isBlank() ? "application/octet-stream" : mediaType;
    }

    /** Returns a defensive copy of the raw video bytes. */
    public byte[] data() {
        return Arrays.copyOf(data, data.length);
    }
}
