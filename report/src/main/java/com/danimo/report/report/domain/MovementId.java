package com.danimo.report.report.domain;

import lombok.Value;

import java.util.UUID;

@Value
public class MovementId {
    private final UUID id;

    public MovementId(UUID id) {
        this.id = id;
    }

    public static MovementId generate() {
        return new MovementId(UUID.randomUUID());
    }
    public static MovementId fromUuid(UUID uuid) {
        return new MovementId(uuid);
    }
}
