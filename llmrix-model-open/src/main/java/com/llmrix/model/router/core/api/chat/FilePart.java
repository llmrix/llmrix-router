package com.llmrix.model.router.core.api.chat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

/** Public data type used by the model routing API. */
@Getter
@EqualsAndHashCode
@Accessors(fluent = true)
public final class FilePart implements ContentPart {
    /** Value of the `url` property. */
    private final String url;
    /** Value of the `fileId` property. */
    private final String fileId;
    /** Value of the `filename` property. */
    private final String filename;

    /** Creates an instance of this API type. */
    public FilePart(String url) {
        this(url, null);
    }

    /** Creates an instance of this API type. */
    public FilePart(String url, String filename) {
        if (url == null || url.isBlank()) throw new IllegalArgumentException("file url or data must not be blank");
        if (filename != null && filename.isBlank()) throw new IllegalArgumentException("file filename must not be blank");
        this.url = url;
        this.fileId = null;
        this.filename = filename;
    }

    private FilePart(String fileId, String filename, boolean uploaded) {
        if (fileId == null || fileId.isBlank()) throw new IllegalArgumentException("file id must not be blank");
        if (filename != null && filename.isBlank()) throw new IllegalArgumentException("file filename must not be blank");
        this.url = null;
        this.fileId = fileId;
        this.filename = filename;
    }

    /** Executes the associated model API operation. */
    public static FilePart fileId(String fileId) {
        return fileId(fileId, null);
    }

    /** Executes the associated model API operation. */
    public static FilePart fileId(String fileId, String filename) {
        return new FilePart(fileId, filename, true);
    }
}
