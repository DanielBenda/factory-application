------------------------------------------------------------
-- SCHEMA
------------------------------------------------------------
CREATE SCHEMA IF NOT EXISTS factory;

------------------------------------------------------------
-- DROP ALL TABLES
------------------------------------------------------------
DROP TABLE IF EXISTS factory.t_work_order_operation CASCADE;
DROP TABLE IF EXISTS factory.t_operation CASCADE;
DROP TABLE IF EXISTS factory.t_machine CASCADE;
DROP TABLE IF EXISTS factory.t_part_machine_type CASCADE;
DROP TABLE IF EXISTS factory.t_machine_type CASCADE;
DROP TABLE IF EXISTS factory.t_work_orders_for_worker CASCADE;
DROP TABLE IF EXISTS factory.t_work_order CASCADE;
DROP TABLE IF EXISTS factory.t_worker CASCADE;
DROP TABLE IF EXISTS factory.t_department CASCADE;
DROP TABLE IF EXISTS factory.t_product_type_parts CASCADE;
DROP TABLE IF EXISTS factory.t_part CASCADE;
DROP TABLE IF EXISTS factory.t_product_type CASCADE;

------------------------------------------------------------
-- TABLES
------------------------------------------------------------

CREATE TABLE factory.t_department
(
    id     UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code   VARCHAR(50)  NOT NULL,
    leader VARCHAR(100),
    name   VARCHAR(100) NOT NULL
);

CREATE TABLE factory.t_worker
(
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name          VARCHAR(100) NOT NULL,
    surname       VARCHAR(100) NOT NULL,
    department_id UUID         NOT NULL,
    work_position VARCHAR(100),
    system_role   VARCHAR(50),
    created_by    VARCHAR(100),
    created       DATE,
    FOREIGN KEY (department_id) REFERENCES factory.t_department (id) ON DELETE CASCADE
);

CREATE TABLE factory.t_product_type
(
    id   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50)  NOT NULL UNIQUE
);

CREATE TABLE factory.t_part
(
    id       UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name     VARCHAR(100) NOT NULL,
    code     VARCHAR(50)  NOT NULL UNIQUE,
    material VARCHAR(50)  NOT NULL
);

CREATE TABLE factory.t_product_type_parts
(
    product_type_id UUID NOT NULL,
    part_id         UUID NOT NULL,
    PRIMARY KEY (product_type_id, part_id),
    FOREIGN KEY (product_type_id) REFERENCES factory.t_product_type (id) ON DELETE CASCADE,
    FOREIGN KEY (part_id) REFERENCES factory.t_part (id) ON DELETE CASCADE
);

CREATE TABLE factory.t_machine_type
(
    id   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100)
);

CREATE TABLE factory.t_machine
(
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    machine_type_id UUID               NOT NULL,
    code            VARCHAR(50) UNIQUE NOT NULL,
    name            VARCHAR(100),
    year            INT,
    FOREIGN KEY (machine_type_id) REFERENCES factory.t_machine_type (id) ON DELETE CASCADE
);

CREATE TABLE factory.t_part_machine_type
(
    part_id         UUID NOT NULL,
    machine_type_id UUID NOT NULL,
    PRIMARY KEY (part_id, machine_type_id),
    FOREIGN KEY (part_id) REFERENCES factory.t_part (id) ON DELETE CASCADE,
    FOREIGN KEY (machine_type_id) REFERENCES factory.t_machine_type (id) ON DELETE CASCADE
);

CREATE TABLE factory.t_work_order
(
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code            VARCHAR(50)  NOT NULL,
    name            VARCHAR(100) NOT NULL,
    description     TEXT,
    state           VARCHAR(50),
    created         TIMESTAMP,
    created_by      UUID         NOT NULL,
    product_type_id UUID         NOT NULL,
    FOREIGN KEY (product_type_id) REFERENCES factory.t_product_type (id) ON DELETE RESTRICT,
    FOREIGN KEY (created_by) REFERENCES factory.t_worker (id) ON DELETE RESTRICT
);

CREATE TABLE factory.t_work_orders_for_worker
(
    worker_id     UUID NOT NULL,
    work_order_id UUID NOT NULL,
    PRIMARY KEY (worker_id, work_order_id),
    FOREIGN KEY (worker_id) REFERENCES factory.t_worker (id) ON DELETE CASCADE,
    FOREIGN KEY (work_order_id) REFERENCES factory.t_work_order (id) ON DELETE CASCADE
);

CREATE TABLE factory.t_operation
(
    id                     UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    part_id                UUID         NOT NULL,
    sequence               INT          NOT NULL,
    name                   VARCHAR(100) NOT NULL,
    estimated_time_minutes INT,
    FOREIGN KEY (part_id) REFERENCES factory.t_part (id) ON DELETE CASCADE
);

CREATE TABLE factory.t_work_order_operation
(
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    work_order_id UUID NOT NULL,
    operation_id  UUID NOT NULL,
    worker_id     UUID,
    machine_id    UUID,
    start_time    TIMESTAMP,
    end_time      TIMESTAMP,
    FOREIGN KEY (work_order_id) REFERENCES factory.t_work_order (id) ON DELETE CASCADE,
    FOREIGN KEY (operation_id) REFERENCES factory.t_operation (id) ON DELETE CASCADE,
    FOREIGN KEY (worker_id) REFERENCES factory.t_worker (id),
    FOREIGN KEY (machine_id) REFERENCES factory.t_machine (id)
);

------------------------------------------------------------
-- TRUNCATE
------------------------------------------------------------
TRUNCATE factory.t_work_order_operation,
    factory.t_operation,
    factory.t_machine,
    factory.t_part_machine_type,
    factory.t_machine_type,
    factory.t_work_orders_for_worker,
    factory.t_work_order,
    factory.t_worker,
    factory.t_department,
    factory.t_product_type_parts,
    factory.t_part,
    factory.t_product_type
    RESTART IDENTITY CASCADE;

------------------------------------------------------------
-- INSERT BASE MASTER DATA
------------------------------------------------------------

-- Departments
INSERT INTO factory.t_department (code, leader, name)
VALUES ('DEP-MNT', 'Jan Novák', 'Údržba'),
       ('DEP-CNC', 'Petr Kříž', 'CNC výroba'),
       ('DEP-QC', 'Alena Malá', 'Kontrola kvality');

-- Workers
INSERT INTO factory.t_worker (name, surname, department_id, work_position, system_role, created_by, created)
SELECT w.name,
       w.surname,
       d.id,
       w.pos,
       w.role,
       'ADMIN',
       NOW()::date
FROM (VALUES
          -- CNC zaměstnanci
          ('Lucie', 'Králová', 'CNC operátor', 'employee'),
          ('Marek', 'Sedlák', 'CNC operátor', 'employee'),
          ('David', 'Urban', 'Seřizovač', 'employee'),
          ('Pavel', 'Hrabal', 'CNC operátor', 'employee'),
          ('Roman', 'Nedvěd', 'CNC operátor', 'employee'),
          ('Ivana', 'Konečná', 'Mistr CNC', 'leader'),
          ('Vojtěch', 'Kříž', 'Vedoucí CNC výroby', 'boss'),

          -- Údržba
          ('Karel', 'Dvořák', 'Mechanik', 'employee'),
          ('Michal', 'Janda', 'Elektrikář', 'employee'),
          ('Petr', 'Říha', 'Mistr údržby', 'leader'),
          ('Radek', 'Holík', 'Vedoucí údržby', 'boss'),

          -- Kontrola kvality
          ('Tomáš', 'Bartoš', 'Kontrolor', 'employee'),
          ('Jana', 'Jílková', 'Kontrolor', 'employee'),
          ('Šárka', 'Pokorná', 'Mistr kontroly', 'leader'),

          -- Montáž
          ('Lucie', 'Bláhová', 'Montér', 'employee'),
          ('Filip', 'Veselý', 'Montér', 'employee'),
          ('Martin', 'Křížek', 'Mistr montáže', 'leader'),
          ('Daniel', 'Kučera', 'Vedoucí montáže', 'boss')) AS w(name, surname, pos, role)

         JOIN factory.t_department d ON
    (w.pos LIKE '%CNC%' AND d.code = 'DEP-CNC')
        OR (w.pos LIKE '%operátor%' AND d.code = 'DEP-CNC')
        OR (w.pos LIKE '%Seřizovač%' AND d.code = 'DEP-CNC')
        OR (w.pos = 'Mechanik' AND d.code = 'DEP-MNT')
        OR (w.pos = 'Elektrikář' AND d.code = 'DEP-MNT')
        OR (w.pos LIKE '%údržby%' AND d.code = 'DEP-MNT')
        OR (w.pos LIKE '%Kontrolor%' AND d.code = 'DEP-QC')
        OR (w.pos LIKE '%kontroly%' AND d.code = 'DEP-QC')
        OR (w.pos LIKE '%Montér%' AND d.code = 'DEP-ASM')
        OR (w.pos LIKE '%montáže%' AND d.code = 'DEP-ASM');


-- Machine types
INSERT INTO factory.t_machine_type (code, name)
VALUES ('MT-CNC3', '3-osé CNC'),
       ('MT-CNC5', '5-osé CNC'),
       ('MT-LASER', 'Laserová řezačka'),
       ('MT-MILL', 'Frézka'),
       ('MT-LATHE', 'Soustruh'),
       ('MT-GRIND', 'Broušení');

-- Machines (10 machines)
INSERT INTO factory.t_machine (machine_type_id, code, name, year)
SELECT mt.id, CONCAT('MC-', n), CONCAT(mt.name, ' ', n), (2010 + (n % 10))
FROM factory.t_machine_type mt
         CROSS JOIN generate_series(1, 10) AS n
ORDER BY mt.id
LIMIT 10;

-- Product types
INSERT INTO factory.t_product_type (name, code)
VALUES ('Hydraulické čerpadlo', 'PT-HPUMP'),
       ('Převodovka', 'PT-GEARBOX'),
       ('Brzdový systém', 'PT-BRAKE'),
       ('Turbína', 'PT-TURB'),
       ('Pístová jednotka', 'PT-PISTON');

-- Parts (20 pcs using cycle)
INSERT INTO factory.t_part (name, code, material)
SELECT CONCAT('Díl ', n),
       CONCAT('PART-', n),
       (ARRAY ['Ocel', 'Hliník', 'Guma', 'Titan'])[1 + (n % 4)]
FROM generate_series(1, 20) AS n;

-- Part ↔ machine type relations
INSERT INTO factory.t_part_machine_type (part_id, machine_type_id)
SELECT p.id, mt.id
FROM factory.t_part p
         JOIN factory.t_machine_type mt ON (mt.code IN ('MT-CNC3', 'MT-CNC5', 'MT-LATHE'))
LIMIT 40;

-- Product ↔ parts (random distribution)
INSERT INTO factory.t_product_type_parts
SELECT pt.id, p.id
FROM factory.t_product_type pt
         CROSS JOIN LATERAL (
    SELECT id FROM factory.t_part ORDER BY random() LIMIT 4
    ) p;

-- Operations (50 ops)
INSERT INTO factory.t_operation (part_id, sequence, name, estimated_time_minutes)
SELECT p.id,
       gs,
       CONCAT('Operace ', gs),
       5 + (gs * 3)
FROM factory.t_part p
         CROSS JOIN generate_series(1, 5) AS gs;

-- Work orders ST-1…ST-30
INSERT INTO factory.t_work_order (code, name, description, state, created, created_by, product_type_id)
SELECT CONCAT('ST-', n),
       CONCAT('Zakázka ', n),
       'Automaticky generovaná zakázka',
       (ARRAY ['Nová','Ve výrobě','Kontrola','Hotovo'])[1 + (n % 4)],
       NOW() - (n || ' days')::interval,
       w.id,
       pt.id
FROM generate_series(1, 30) AS n
         JOIN factory.t_worker w ON w.work_position LIKE '%operátor%'
         JOIN factory.t_product_type pt ON pt.code IN ('PT-HPUMP', 'PT-GEARBOX', 'PT-BRAKE')
ORDER BY random()
LIMIT 30;

-- Assign workers to work orders
INSERT INTO factory.t_work_orders_for_worker (worker_id, work_order_id)
SELECT w.id,
       wo.id
FROM factory.t_worker w
         JOIN factory.t_work_order wo ON wo.id IS NOT NULL
ORDER BY random()
LIMIT 60;

-- Work order operations (simulate workflow)
INSERT INTO factory.t_work_order_operation (work_order_id, operation_id, worker_id, machine_id, start_time, end_time)
SELECT wo.id,
       o.id,
       w.id,
       m.id,
       NOW() - ((random() * 10)::int || ' hours')::interval,
       NOW() - ((random() * 5)::int || ' hours')::interval
FROM factory.t_work_order wo
         JOIN factory.t_operation o ON random() < 0.2
         JOIN factory.t_worker w ON w.work_position LIKE '%operátor%'
         JOIN factory.t_machine m ON m.id IS NOT NULL
LIMIT 200;