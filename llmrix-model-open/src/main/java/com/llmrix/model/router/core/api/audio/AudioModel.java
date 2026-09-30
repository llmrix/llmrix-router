package com.llmrix.model.router.core.api.audio;

/** Provider-neutral operations for audio transcription, translation, and speech synthesis. */
public interface AudioModel {
    /**
     * Transcribes spoken audio into text.
     *
     * @param request transcription options and the source audio input
     * @return the transcribed audio response
     */
    AudioResponse transcribe(AudioTextRequest request);

    /**
     * Translates spoken audio into text.
     *
     * @param request translation options and the source audio input
     * @return the translated audio response
     */
    AudioResponse translate(AudioTextRequest request);

    /**
     * Synthesizes speech from text.
     *
     * @param request speech text, voice, format, and playback options
     * @return the synthesized audio response
     */
    AudioResponse speech(SpeechRequest request);
}
