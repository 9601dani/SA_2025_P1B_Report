package com.danimo.report.movement.domain;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class MovementCreatedAt {
    private final LocalDateTime createdAt;

    public MovementCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static MovementCreatedAt generate() {
        return new MovementCreatedAt(LocalDateTime.now());
    }

    public static MovementCreatedAt fromLocalDateTime(LocalDateTime createdAt) {
        return new MovementCreatedAt(createdAt);
    }
}
