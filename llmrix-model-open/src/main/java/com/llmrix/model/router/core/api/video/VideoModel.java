package com.llmrix.model.router.core.api.video;

/** Provider-neutral video generation lifecycle operations. */
public interface VideoModel {
    /**
     * Starts an asynchronous video generation job.
     *
     * @param request video prompt, duration, size, and optional reference input
     * @return the newly created job status
     */
    VideoResponse create(VideoRequest request);

    /**
     * Retrieves the current state and metadata of a video job.
     *
     * @param request identifier of the video job to retrieve
     * @return the current job status
     */
    VideoResponse retrieve(VideoLookupRequest request);

    /**
     * Retrieves generated video bytes for a completed job.
     *
     * @param request identifier of the completed video job
     * @return the generated video content
     */
    VideoContent content(VideoLookupRequest request);

    /**
     * Deletes a video generation job or its retained result.
     *
     * @param request identifier of the video job to delete
     * @return the provider response describing the deletion
     */
    VideoResponse delete(VideoLookupRequest request);

    /**
     * Starts a new video generation job by remixing an existing video.
     *
     * @param request source video identifier and remix prompt
     * @return the newly created remix job status
     */
    VideoResponse remix(VideoRemixRequest request);
}
