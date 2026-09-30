package com.llmrix.model.router.core.api.chat;

/** A typed piece of multimodal chat message content. */
public sealed interface ContentPart permits TextPart, ImagePart, AudioPart, VideoPart, FilePart, ToolCallPart, ToolResultPart {
}
