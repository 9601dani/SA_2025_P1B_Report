package com.danimo.report.report.infrastructure.outputadapters.persistence.entity.mapper;

import com.danimo.report.report.domain.*;
import com.danimo.report.report.infrastructure.outputadapters.persistence.entity.MovementDbEntity;
import org.springframework.stereotype.Component;

@Component
public class MovementPersistenceMapper {
    public Movement toDomain(MovementDbEntity dbEntity){
        if(dbEntity == null) return null;

        return new Movement(
                MovementId.fromUuid(dbEntity.getId()),
                dbEntity.getServiceType(),
                dbEntity.getPaymentType(),
                dbEntity.getDescription(),
                MovementAmount.fromBigDecimal(dbEntity.getAmount()),
                dbEntity.getLocationId(),
                dbEntity.getLocationName(),
                MovementCreatedAt.fromLocalDateTime(dbEntity.getCreatedAt())
        );
    }

    public MovementDbEntity toDbEntity(Movement movement){
        if(movement == null) return null;

        return new MovementDbEntity(
                movement.getId().getId(),
                movement.getServiceType(),
                movement.getPaymentType(),
                movement.getDescription(),
                movement.getAmount().getAmount(),
                movement.getLocationId(),
                movement.getLocationName(),
                movement.getCreatedAt().getCreatedAt()
        );
    }
}
