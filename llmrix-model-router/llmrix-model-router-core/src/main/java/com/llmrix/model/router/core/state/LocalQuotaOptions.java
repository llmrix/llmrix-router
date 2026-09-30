package com.llmrix.model.router.core.state;

import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.Duration;

/** Bounds the in-process quota partition registry without evicting active quota state. */
@Getter
@Accessors(fluent = true)
public final class LocalQuotaOptions {
    public static final LocalQuotaOptions DEFAULT = new LocalQuotaOptions(10_000, Duration.ofMinutes(2));

    private final int maxPartitions;
    private final Duration idleTimeout;

    public LocalQuotaOptions(int maxPartitions, Duration idleTimeout) {
        if (maxPartitions < 1) throw new IllegalArgumentException("maxPartitions must be > 0");
        if (idleTimeout == null || idleTimeout.isZero() || idleTimeout.isNegative()) {
            throw new IllegalArgumentException("idleTimeout must be positive");
        }
        if (idleTimeout.compareTo(Duration.ofMinutes(1)) < 0) {
            throw new IllegalArgumentException("idleTimeout must be at least one minute");
        }
        this.maxPartitions = maxPartitions;
        this.idleTimeout = idleTimeout;
    }

}
