package com.llmrix.model.router.core.api;

/**
 * Response that can be decorated with the target selected by the router.
 */
public interface RoutedResponse<T extends RoutedResponse<T>> {
    /** Returns provider usage reported for this response. */
    Usage usage();

    /** Returns a copy associated with the router target that produced it. */
    T routedBy(String targetId);
}
