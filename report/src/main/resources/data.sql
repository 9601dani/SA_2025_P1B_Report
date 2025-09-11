INSERT INTO movements (
    id,
    service_type,
    payment_type,
    description,
    amount,
    location_id,
    location_name,
    created_at
) VALUES (
    '11111111-1111-1111-1111-111111111111',
    'RESTAURANT',
    'CREDIT',
    'Venta de Pizza Margarita',
    90.00,
    '123e4567-e89b-12d3-a456-426614174002',
    'Sucursal Sur',
    '2025-09-10T10:00:00'
);

INSERT INTO movements (
    id,
    service_type,
    payment_type,
    description,
    amount,
    location_id,
    location_name,
    created_at
) VALUES (
    '22222222-2222-2222-2222-222222222222',
    'RESTAURANT',
    'DEBIT',
    'Costo de producción Pizza Margarita',
    50.00,
    '550e8400-e29b-41d4-a716-446655440000',
    'Sucursal Central',
    '2025-09-10T10:05:00'
);

INSERT INTO movements (
    id,
    service_type,
    payment_type,
    description,
    amount,
    location_id,
    location_name,
    created_at
) VALUES (
    '33333333-3333-3333-3333-333333333333',
    'PAYROLL',
    'DEBIT',
    'Pago semanal empleados cocina',
    2500.00,
    '550e8400-e29b-41d4-a716-446655440000',
    'Sucursal Central',
    '2025-09-10T11:00:00'
);
