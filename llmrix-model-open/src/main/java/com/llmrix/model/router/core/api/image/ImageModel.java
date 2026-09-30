package com.llmrix.model.router.core.api.image;

/** Provider-neutral image generation and editing operations. */
public interface ImageModel {
    /**
     * Generates one or more images from a text prompt.
     *
     * @param request image prompt and generation options
     * @return the generated images and provider metadata
     */
    ImageResponse generate(ImageRequest request);

    /**
     * Edits one or more source images according to a text prompt.
     *
     * @param request source images, optional mask, prompt, and output options
     * @return the edited images and provider metadata
     */
    ImageResponse edit(ImageEditRequest request);
}
