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

-- Department table
CREATE TABLE factory.t_department (
                                      id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                      code VARCHAR(50) NOT NULL,
                                      leader VARCHAR(100),
                                      name VARCHAR(100) NOT NULL
);

-- Worker table
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

-- Work Orders ↔ Worker (M:N)
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

TRUNCATE factory.t_work_orders_for_worker,
         factory.t_work_order,
         factory.t_worker,
         factory.t_department,
         factory.t_product_type_parts,
         factory.t_part,
         factory.t_product_type
RESTART IDENTITY CASCADE;


-- Nejprve oddělení
INSERT INTO factory.t_department (code, leader, name)
VALUES
    ('DEP-MNT', 'Jan Novák', 'Údržba'),
    ('DEP-ASM', 'Petr Svoboda', 'Montáž'),
    ('DEP-QC', 'Alena Malá', 'Kontrola kvality');

-- Pracovníci
INSERT INTO factory.t_worker (name, surname, department_id, work_position)
SELECT 'Karel', 'Dvořák', id, 'Mechanik'
FROM factory.t_department WHERE code = 'DEP-MNT'
UNION ALL
SELECT 'Lucie', 'Králová', id, 'Montér'
FROM factory.t_department WHERE code = 'DEP-ASM'
UNION ALL
SELECT 'Tomáš', 'Bartoš', id, 'Kontrolor'
FROM factory.t_department WHERE code = 'DEP-QC';

-- Typy produktů
INSERT INTO factory.t_product_type (name, code)
VALUES
    ('Hydraulické čerpadlo', 'PT-HPUMP'),
    ('Převodovka', 'PT-GEARBOX'),
    ('Brzdový systém', 'PT-BRAKE');

-- Díly
INSERT INTO factory.t_part (name, code, material, machines)
VALUES
    ('Těsnění', 'PART-SEAL', 'Guma', 'Lis'),
    ('Hřídel', 'PART-SHAFT', 'Ocel', 'Soustruh'),
    ('Ložisko', 'PART-BEAR', 'Ocel', 'Lis'),
    ('Kryt', 'PART-COVER', 'Hliník', 'Frézka');

-- Vazby produkt ↔ díly
INSERT INTO factory.t_product_type_parts (product_type_id, part_id)
SELECT pt.id, p.id
FROM factory.t_product_type pt
         JOIN factory.t_part p ON
    (pt.code = 'PT-HPUMP' AND p.code IN ('PART-SEAL','PART-SHAFT'))
        OR (pt.code = 'PT-GEARBOX' AND p.code IN ('PART-SHAFT','PART-BEAR'))
        OR (pt.code = 'PT-BRAKE' AND p.code IN ('PART-SEAL','PART-COVER'));

-- Zakázky
INSERT INTO factory.t_work_order (code, name, description, state, product_type_id, machine_type)
SELECT 'WO-1001', 'Výroba čerpadla A', 'Série 50 ks', 'Nová', id, 'Lis'
FROM factory.t_product_type WHERE code = 'PT-HPUMP'
UNION ALL
SELECT 'WO-1002', 'Montáž převodovky B', 'Série 20 ks', 'Ve výrobě', id, 'Soustruh'
FROM factory.t_product_type WHERE code = 'PT-GEARBOX'
UNION ALL
SELECT 'WO-1003', 'Test brzdového systému C', 'Prototyp', 'Kontrola', id, 'Frézka'
FROM factory.t_product_type WHERE code = 'PT-BRAKE';

-- Vazby zakázky ↔ pracovníci
INSERT INTO factory.t_work_orders_for_worker (worker_id, work_order_id)
SELECT w.id, o.id
FROM factory.t_worker w
         JOIN factory.t_work_order o ON
    (w.name = 'Karel' AND o.code = 'WO-1001')
        OR (w.name = 'Lucie' AND o.code = 'WO-1002')
        OR (w.name = 'Tomáš' AND o.code = 'WO-1003');
