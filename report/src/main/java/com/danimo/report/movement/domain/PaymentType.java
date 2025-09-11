package com.danimo.report.movement.domain;

public enum PaymentType {
    CREDIT,
    DEBIT;

    public static PaymentType fromString(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("PaymentType no puede ser nulo o vacío");
        }
        try {
            return PaymentType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Valor inválido para PaymentType: " + value);
        }
    }
}
