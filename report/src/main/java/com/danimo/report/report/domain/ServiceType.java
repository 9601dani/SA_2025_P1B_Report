package com.danimo.report.report.domain;

public enum ServiceType {
    RESTAURANT,
    HOTEL,
    PAYROLL,
    MAINTENANCE;

    public static ServiceType fromString(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("ServiceType no puede ser nulo o vacío");
        }
        try {
            return ServiceType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Valor inválido para ServiceType: " + value);
        }
    }
}
