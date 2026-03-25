------------------------------------------------------------
-- SCHEMA
------------------------------------------------------------
CREATE SCHEMA IF NOT EXISTS factory;
SET search_path TO factory, pg_catalog;

CREATE EXTENSION IF NOT EXISTS pg_trgm WITH SCHEMA factory;
------------------------------------------------------------
-- DROP ALL TABLES
------------------------------------------------------------
DROP TABLE IF EXISTS factory.t_part_operation CASCADE;
DROP TABLE IF EXISTS factory.t_operation_type CASCADE;
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
-- TABLE DEFINITIONS
------------------------------------------------------------

------------------------------------------------------------
-- Departments
------------------------------------------------------------

CREATE TABLE factory.t_department
(
    id     UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code   VARCHAR(50)  NOT NULL,
    leader VARCHAR(100),
    name   VARCHAR(100) NOT NULL
);

------------------------------------------------------------
-- Workers
------------------------------------------------------------
CREATE TABLE factory.t_worker
(
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    created       DATE         NOT NULL,
    created_by    VARCHAR(100) NOT NULL,
    department_id UUID         NOT NULL,
    name          VARCHAR(100) NOT NULL,
    surname       VARCHAR(100) NOT NULL,
    system_role   VARCHAR(50)  NOT NULL,
    work_position VARCHAR(100),

    FOREIGN KEY (department_id) REFERENCES factory.t_department (id) ON DELETE CASCADE
);

------------------------------------------------------------
-- Product Types
------------------------------------------------------------
CREATE TABLE factory.t_product_type
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code        VARCHAR(50)  NOT NULL UNIQUE,
    created     TIMESTAMP    NOT NULL,
    created_by  VARCHAR(100) NOT NULL,
    description VARCHAR,
    name        VARCHAR(100) NOT NULL
);

------------------------------------------------------------
-- Parts
------------------------------------------------------------
CREATE TABLE factory.t_part
(
    id       UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code     VARCHAR(50)  NOT NULL UNIQUE,
    material VARCHAR(50)  NOT NULL,
    name     VARCHAR(100) NOT NULL
);

------------------------------------------------------------
-- Product ↔ Parts (M:N)
------------------------------------------------------------
CREATE TABLE factory.t_product_type_parts
(
    product_type_id UUID NOT NULL,
    part_id         UUID NOT NULL,
    PRIMARY KEY (product_type_id, part_id),
    FOREIGN KEY (product_type_id) REFERENCES factory.t_product_type (id) ON DELETE CASCADE,
    FOREIGN KEY (part_id) REFERENCES factory.t_part (id) ON DELETE CASCADE
);

------------------------------------------------------------
-- Machine Types
------------------------------------------------------------
CREATE TABLE factory.t_machine_type
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code        VARCHAR(50)  NOT NULL UNIQUE,
    description VARCHAR,
    name        VARCHAR(100) NOT NULL
);

------------------------------------------------------------
-- Machines
------------------------------------------------------------
CREATE TABLE factory.t_machine
(
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code            VARCHAR(50) UNIQUE NOT NULL,
    machine_type_id UUID               NOT NULL,
    name            VARCHAR(100),
    year            INT,

    FOREIGN KEY (machine_type_id) REFERENCES factory.t_machine_type (id) ON DELETE CASCADE
);

------------------------------------------------------------
-- Part ↔ Machine type (M:N)
------------------------------------------------------------
CREATE TABLE factory.t_part_machine_type
(
    part_id         UUID NOT NULL,
    machine_type_id UUID NOT NULL,
    PRIMARY KEY (part_id, machine_type_id),
    FOREIGN KEY (part_id) REFERENCES factory.t_part (id) ON DELETE CASCADE,
    FOREIGN KEY (machine_type_id) REFERENCES factory.t_machine_type (id) ON DELETE CASCADE
);

------------------------------------------------------------
-- Work Orders
------------------------------------------------------------
CREATE TABLE factory.t_work_order
(
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code            VARCHAR(50)  NOT NULL UNIQUE,
    name            VARCHAR(100) NOT NULL,
    description     TEXT,
    state           VARCHAR(50),
    completedBy     UUID         NOT NULL,
    created         TIMESTAMP,
    created_by      UUID         NOT NULL,
    product_type_id UUID         NOT NULL,

    FOREIGN KEY (created_by) REFERENCES factory.t_worker (id) ON DELETE RESTRICT,
    FOREIGN KEY (product_type_id) REFERENCES factory.t_product_type (id) ON DELETE RESTRICT
);

------------------------------------------------------------
-- Work Orders ↔ Workers (M:N)
------------------------------------------------------------
CREATE TABLE factory.t_work_orders_for_worker
(
    worker_id     UUID NOT NULL,
    work_order_id UUID NOT NULL,
    PRIMARY KEY (worker_id, work_order_id),

    FOREIGN KEY (worker_id) REFERENCES factory.t_worker (id) ON DELETE CASCADE,
    FOREIGN KEY (work_order_id) REFERENCES factory.t_work_order (id) ON DELETE CASCADE
);

------------------------------------------------------------
-- Operation Types (master data)
------------------------------------------------------------
CREATE TABLE factory.t_operation_type
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    description VARCHAR,
    code            VARCHAR(50)  NOT NULL UNIQUE,
    name        VARCHAR(100) NOT NULL,
    created     DATE         NOT NULL,
    created_by  VARCHAR(100) NOT NULL
);

------------------------------------------------------------
-- Operations
------------------------------------------------------------
CREATE TABLE factory.t_operation
(
    id                     UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    operation_type_id      UUID         NOT NULL,
    name                   VARCHAR(100) NOT NULL,
    description            VARCHAR(255),
    estimated_time_minutes INT,

    FOREIGN KEY (operation_type_id)
        REFERENCES factory.t_operation_type (id)
        ON DELETE RESTRICT
);

------------------------------------------------------------
-- Part ↔ Operation (M:N)
------------------------------------------------------------
CREATE TABLE factory.t_part_operation
(
    part_id      UUID NOT NULL,
    operation_id UUID NOT NULL,
    sequence     INT  NOT NULL,

    PRIMARY KEY (part_id, operation_id),
    FOREIGN KEY (part_id) REFERENCES factory.t_part (id) ON DELETE CASCADE,
    FOREIGN KEY (operation_id) REFERENCES factory.t_operation (id) ON DELETE CASCADE
);
------------------------------------------------------------
-- INDEXES
------------------------------------------------------------

------------------------------------------------------------
-- machine type
------------------------------------------------------------

CREATE INDEX idx_machine_type_name_trgm
    ON factory.t_machine_type
        USING gin (LOWER(name) gin_trgm_ops);

CREATE INDEX idx_machine_type_code_trgm
    ON factory.t_machine_type
        USING gin (LOWER(code) gin_trgm_ops);

------------------------------------------------------------
-- operation type
------------------------------------------------------------

CREATE INDEX idx_operation_type_name_trgm
    ON factory.t_operation_type
        USING gin (LOWER(name) gin_trgm_ops);

CREATE INDEX idx_operation_type_code_trgm
    ON factory.t_operation_type
        USING gin (LOWER(code) gin_trgm_ops);

------------------------------------------------------------
-- TRUNCATE ALL
------------------------------------------------------------
TRUNCATE
    factory.t_part_operation,
    factory.t_operation_type,
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
INSERT INTO factory.t_machine_type (code, description, name)
VALUES ('MT-CNC3', '3-osé vertikální CNC centrum vhodné pro sériové obrábění menších a středních dílů.', '3-osé CNC'),
       ('MT-CNC5', '5-osé simultánní CNC centrum pro komplexní díly, vysokou přesnost a 1-up obrábění.', '5-osé CNC'),
       ('MT-LASER', 'Laserová řezačka pro přesné dělení plechu a profilů s vysokou rychlostí.', 'Laserová řezačka'),
       ('MT-MILL', 'Klasická nebo CNC frézka pro obrábění rovných ploch, kapes a tvarových prvků.', 'Frézka'),
       ('MT-LATHE', 'CNC soustruh určený pro rotační díly, hřídele a pouzdra.', 'Soustruh'),
       ('MT-GRIND', 'Bruska pro finální obrábění povrchů s velmi vysokou přesností.', 'Broušení');

-- Machines
INSERT INTO factory.t_machine (code, machine_type_id, name, year)
SELECT CONCAT('MC-', n),
       mt.id,
       CONCAT(mt.name, ' ', n),
       2010 + (n % 10)
FROM factory.t_machine_type mt
         CROSS JOIN generate_series(1, 10) AS n
ORDER BY mt.code, n
LIMIT 10;

-- Product types
INSERT INTO factory.t_product_type (code, created, created_by, description, name)
VALUES ('PT-HPUMP', NOW(), 'ADMIN', 'Hydraulické čerpadlo pro průmyslové použití.', 'Hydraulické čerpadlo'),
       ('PT-GEARBOX', NOW(), 'ADMIN', 'Převodová jednotka s přesným obráběním.', 'Převodovka'),
       ('PT-BRAKE', NOW(), 'ADMIN', 'Modul brzdového systému s vysokou spolehlivostí.', 'Brzdový systém'),
       ('PT-TURB', NOW(), 'ADMIN', 'Turbínové těleso pro vysokootáčkové aplikace.', 'Turbína'),
       ('PT-PISTON', NOW(), 'ADMIN', 'Pístová jednotka určená pro tlakové agregáty.', 'Pístová jednotka');

-- Parts
INSERT INTO factory.t_part (code, material, name)
SELECT CONCAT('PART-', n),
       (ARRAY ['Ocel','Hliník','Guma','Titan'])[1 + (n % 4)],
       CONCAT('Díl ', n)
FROM generate_series(1, 20) n;

-- Operation types
INSERT INTO factory.t_operation_type (code, name, description, created, created_by)
VALUES ('FREZ', 'Frézování', 'Třískové obrábění pomocí frézy', CURRENT_DATE, 'ADMIN'),
       ('SOUS', 'Soustružení', 'Obrábění rotačních dílů', CURRENT_DATE, 'ADMIN'),
       ('LASER', 'Laserování', 'Řezání materiálu laserem', CURRENT_DATE, 'ADMIN'),
       ('BEND', 'Ohýbání', 'Tváření materiálu za studena', CURRENT_DATE, 'ADMIN'),
       ('BROU', 'Broušení', 'Dokončovací operace povrchu', CURRENT_DATE, 'ADMIN'),
       ('DRILL', 'Vrtání', 'Vytváření otvorů', CURRENT_DATE, 'ADMIN');

-- Operations
INSERT INTO factory.t_operation (operation_type_id, name, description, estimated_time_minutes)
SELECT ot.id, o.name, o.description, o.time
FROM (VALUES ('Frézování', 'Hrubovací frézování', 'Hrubé obrábění ploch', 12),
             ('Frézování', 'Dokončovací frézování', 'Přesné obrábění', 8),
             ('Soustružení', 'Soustružení hřídele', 'Rotační obrábění', 10),
             ('Laserování', 'Laserový řez', 'Řezání plechu', 5),
             ('Ohýbání', 'Ohyb plechu', 'Ohyb na lisu', 6),
             ('Broušení', 'Jemné broušení', 'Finální povrch', 7)) AS o(type, name, description, time)
         JOIN factory.t_operation_type ot
              ON ot.name = o.type;


-- Part ↔ Operations
INSERT INTO factory.t_part_operation (part_id, operation_id, sequence)
SELECT p.id, o.id, ROW_NUMBER() OVER (PARTITION BY p.id ORDER BY o.id)
FROM factory.t_part p
         CROSS JOIN factory.t_operation o
WHERE random() < 0.35;

------------------------------------------------------------
-- DONE
------------------------------------------------------------
