package com.danimo.report.report.infrastructure.outputadapters.persistence.repository;

import com.danimo.report.report.infrastructure.outputadapters.persistence.entity.MovementDbEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface MovementDbEntityJpaRepository extends JpaRepository<MovementDbEntity, UUID> {
    List<MovementDbEntity> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);

    List<MovementDbEntity> findByLocationIdAndCreatedAtBetween(UUID locationId, LocalDateTime from, LocalDateTime to);
}
