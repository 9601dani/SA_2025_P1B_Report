package com.danimo.report.movement.domain;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class MovementAmount {
    private BigDecimal amount;

    public MovementAmount(BigDecimal amount) {
        if(amount == null){
            throw new IllegalArgumentException("El monto no puede ser nulo");
        }
        if(amount.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("El monto no puede ser negativo");
        }

        this.amount = amount;
    }

    public static MovementAmount fromBigDecimal(BigDecimal amount) {
        return new MovementAmount(amount);
    }
}
