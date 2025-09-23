-- Schema
CREATE SCHEMA IF NOT EXISTS factory;

-- Drop all tables in correct dependency order
DROP TABLE IF EXISTS factory.t_work_orders_for_worker CASCADE;
DROP TABLE IF EXISTS factory.t_work_order CASCADE;
DROP TABLE IF EXISTS factory.t_worker CASCADE;
DROP TABLE IF EXISTS factory.t_department CASCADE;
DROP TABLE IF EXISTS factory.t_product_type_parts CASCADE;
DROP TABLE IF EXISTS factory.t_part CASCADE;
DROP TABLE IF EXISTS factory.t_product_type CASCADE;

-- model.Department table
CREATE TABLE factory.t_department (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code VARCHAR(50) NOT NULL,
    leader VARCHAR(100),
    name VARCHAR(100) NOT NULL
);

-- model.Worker table
CREATE TABLE factory.t_worker (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    surname VARCHAR(100) NOT NULL,
    department_id UUID NOT NULL,
    work_position VARCHAR(100),
    CONSTRAINT fk_worker_department
        FOREIGN KEY (department_id)
        REFERENCES factory.t_department (id)
        ON DELETE CASCADE
);

-- Product Type table
CREATE TABLE factory.t_product_type (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE
);

-- Part table
CREATE TABLE factory.t_part (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE,
    material VARCHAR(50) NOT NULL,
    machines VARCHAR(50) NOT NULL
);

-- Relation table: Product Type ↔ Parts (M:N)
CREATE TABLE factory.t_product_type_parts (
    product_type_id UUID NOT NULL,
    part_id UUID NOT NULL,
    PRIMARY KEY (product_type_id, part_id),
    CONSTRAINT fk_product_type_parts_product
        FOREIGN KEY (product_type_id)
        REFERENCES factory.t_product_type (id)
        ON DELETE CASCADE,
    CONSTRAINT fk_product_type_parts_part
        FOREIGN KEY (part_id)
        REFERENCES factory.t_part (id)
        ON DELETE CASCADE
);

-- Work Order table (final merged version)
CREATE TABLE factory.t_work_order (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    state VARCHAR(50),
    product_type_id UUID NOT NULL,
    machine_type VARCHAR(100),
    CONSTRAINT fk_work_order_product_type
        FOREIGN KEY (product_type_id)
        REFERENCES factory.t_product_type (id)
        ON DELETE RESTRICT
);

-- Work Orders ↔ model.Worker (M:N)
CREATE TABLE factory.t_work_orders_for_worker (
    worker_id UUID NOT NULL,
    work_order_id UUID NOT NULL,
    PRIMARY KEY (worker_id, work_order_id),
    CONSTRAINT fk_work_orders_for_worker_worker
        FOREIGN KEY (worker_id)
        REFERENCES factory.t_worker (id)
        ON DELETE CASCADE,
    CONSTRAINT fk_work_orders_for_worker_order
        FOREIGN KEY (work_order_id)
        REFERENCES factory.t_work_order (id)
        ON DELETE CASCADE
);
