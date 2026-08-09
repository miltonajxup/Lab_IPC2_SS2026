CREATE DATABASE IF NOT EXISTS Practica1_SS2026;

USE Practica1_SS2026;

CREATE TABLE rol (
    id INT AUTO_INCREMENT,
    tipo VARCHAR(20) NOT NULL UNIQUE,
    CONSTRAINT pk_rol PRIMARY KEY (id)
);

INSERT INTO rol (tipo) VALUES 
('MESERO'),
('COCINA'), 
('BARISTA'), 
('ADMINISTRADOR');

CREATE TABLE jornada (
    id INT AUTO_INCREMENT,
    tipo VARCHAR(100) NOT NULL UNIQUE,
    CONSTRAINT pk_jornada PRIMARY KEY (id)
);

INSERT INTO jornada (tipo) VALUES 
('MATUTINA'),
('VESPERTINA'),
('NOCTURNA');

CREATE TABLE personal (
    dpi VARCHAR(13), 
    nombre VARCHAR(50) NOT NULL, 
    salario DECIMAL(6,2) NOT NULL, 
    fecha_contratacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    estado BOOLEAN DEFAULT TRUE,
    rol INT NOT NULL,
    jornada INT NOT NULL,
    CONSTRAINT pk_personal PRIMARY KEY (dpi),
    CONSTRAINT fk_rol FOREIGN KEY (rol) REFERENCES rol(id),
    CONSTRAINT fk_jornada FOREIGN KEY (jornada) REFERENCES jornada(id)
);

INSERT INTO personal (dpi, nombre, salario, rol, jornada) VALUES 
('1000000000001','Juan Pérez',3500.00,1,1),
('1000000000002','Ana López',4200.00,2,2),
('1000000000003','Carlos Méndez',3900.00,3,1),
('1000000000004','María García',6500.00,4,1),
('1000000000005','Pedro Ramírez',3500.00,1,2), 
('1000000000006','Byron Martinez',3500.00,1,1);

CREATE TABLE tipo_pago (
    id INT AUTO_INCREMENT,
    tipo VARCHAR(20) NOT NULL,
    CONSTRAINT pk_tipo_pago PRIMARY KEY (id)
);

INSERT INTO tipo_pago (tipo) VALUES 
('QUINCENA'),
('FIN_DE_MES');

CREATE TABLE pago (
    codigo_nomina INT AUTO_INCREMENT,
    fecha_emision DATE NOT NULL,
    monto_a_pagar DECIMAL(6,2) NOT NULL,
    estado BOOLEAN DEFAULT FALSE,
    empleado VARCHAR(13) NOT NULL,
    tipo INT NOT NULL,
    CONSTRAINT pk_pago PRIMARY KEY (codigo_nomina),
    CONSTRAINT fk_empleado FOREIGN KEY (empleado) REFERENCES personal(dpi),
    CONSTRAINT fk_tipo FOREIGN KEY (tipo) REFERENCES tipo_pago(id)
);

INSERT INTO pago (fecha_emision, monto_a_pagar, empleado, tipo) VALUES
('2026-1-10', 1750.00,'1000000000001',1),
('2026-2-10', 2100.00,'1000000000002',1),
('2026-3-25', 3900.00,'1000000000003',2),
('2026-3-25', 6500.00,'1000000000004',2),
('2026-4-10', 1750.00,'1000000000005',1);

CREATE TABLE mesa (
    numero_mesa INT AUTO_INCREMENT,
    capacidad INT NOT NULL,
    estado BOOLEAN DEFAULT FALSE,
    CONSTRAINT pk_mesa PRIMARY KEY (numero_mesa)
);

INSERT INTO mesa (capacidad) VALUES 
(4), (3), (6), (10), (2), (5), (7);

UPDATE mesa SET estado = TRUE WHERE numero_mesa = 2;
UPDATE mesa SET estado = TRUE WHERE numero_mesa = 5;

CREATE TABLE unidad_medida (
    id INT AUTO_INCREMENT,
    unidad VARCHAR(20) NOT NULL,
    CONSTRAINT pk_unidad_medida PRIMARY KEY (id)
);

INSERT INTO unidad_medida (unidad) VALUES 
('Kg'),
('g'), 
('L'),
('mL'),
('Unidad'), 
('Otro');

CREATE TABLE insumo (
    codigo INT AUTO_INCREMENT,
    nombre VARCHAR(30) NOT NULL,
    cantidad_stock DECIMAL(8,2) NOT NULL,
    stock_minimo DECIMAL(8,2) NOT NULL,
    costo DECIMAL(8,2) NOT NULL, 
    unidad INT NOT NULL,
    CONSTRAINT pk_insumo PRIMARY KEY (codigo),
    CONSTRAINT fk_insumo_unidad FOREIGN KEY (unidad) REFERENCES unidad_medida(id)
);

INSERT INTO insumo (nombre,cantidad_stock,stock_minimo,costo, unidad) VALUES
('Café',     30.00,10.00,0.15,2),
('Leche',    20.00,5.00, 12.00,3),
('Azúcar',   50.00,10.00,0.01,2),
('Chocolate',15.00,5.00, 0.08,2),
('Harina',   40.00,10.00,7.00,1),
('Huevos',   100.00,20.00,1.50,5),
('Crema Batida',12.00,4.00,0.12,2),
('Jarabe Vainilla',8.00,3.00,0.15,4);

CREATE TABLE gasto_insumo(
    codigo INT AUTO_INCREMENT,
    fecha_compra DATETIME DEFAULT CURRENT_TIMESTAMP,
    cantidad DECIMAL(8,2) NOT NULL,
    insumo INT NOT NULL,
    CONSTRAINT pk_gasto_insumo PRIMARY KEY (codigo),
    CONSTRAINT fk_gi_insumo FOREIGN KEY (insumo) REFERENCES insumo(codigo)
);

INSERT INTO gasto_insumo (cantidad, insumo) VALUES 
(30, 1),
(20, 2),
(50, 3),
(15, 4),
(40, 5),
(100, 6),
(12, 7), 
(8, 8);

CREATE TABLE categoria_producto (
    id INT AUTO_INCREMENT,
    categoria VARCHAR(20) NOT NULL, 
    CONSTRAINT pk_categoria PRIMARY KEY (id)
);

INSERT INTO categoria_producto (categoria) VALUES 
('BEBIDA_CALIENTE'),
('BEBIDA_FRIA'),
('POSTRE'), 
('COMIDA');

CREATE TABLE producto_menu (
    codigo INT AUTO_INCREMENT,
    nombre VARCHAR(30) NOT NULL,
    precio DECIMAL(6,2) NOT NULL,
    categoria INT NOT NULL,
    CONSTRAINT pk_producto PRIMARY KEY (codigo), 
    CONSTRAINT fk_pm_categoria FOREIGN KEY (categoria) REFERENCES categoria_producto(id)
);

INSERT INTO producto_menu (nombre,precio,categoria) VALUES
('Café Americano',18.00,1),
('Capuccino',28.00,1),
('Latte',30.00,1),
('Frappé',35.00,2),
('Cheesecake',26.00,3),
('Brownie',20.00,3),
('Sandwich Club',42.00,4),
('Panini',38.00,4);

CREATE TABLE receta (
    producto_id INT NOT NULL,
    insumo_id INT NOT NULL,
    cantidad_utilizada INT NOT NULL,
    CONSTRAINT pk_receta PRIMARY KEY (producto_id, insumo_id),
    CONSTRAINT fk_rec_producto FOREIGN KEY (producto_id) REFERENCES producto_menu(codigo),
    CONSTRAINT fk_rec_insumo FOREIGN KEY (insumo_id) REFERENCES insumo(codigo)
);

INSERT INTO receta (producto_id, insumo_id, cantidad_utilizada) VALUES
-- Café Americano
(1,1,20),   -- Café
(1,3,5),    -- Azúcar

-- Capuccino
(2,1,20),   -- Café
(2,2,200),  -- Leche
(2,3,5),    -- Azúcar

-- Latte
(3,1,20),   -- Café
(3,2,250),  -- Leche

-- Frappé
(4,1,15),   -- Café
(4,2,200),  -- Leche
(4,8,15),   -- Jarabe de vainilla

-- Cheesecake
(5,5,200),  -- Harina
(5,6,3),    -- Huevos

-- Brownie
(6,4,100),  -- Chocolate
(6,5,150),  -- Harina
(6,6,2),    -- Huevos

-- Sandwich Club
(7,5,120),  -- Harina (pan)
(7,6,1),    -- Huevo

-- Panini
(8,5,150),  -- Harina (pan)
(8,6,1);    -- Huevo

CREATE TABLE pedido (
    numero_pedido INT AUTO_INCREMENT,
    hora_ocupacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    hora_liberacion DATETIME,
    estado BOOLEAN DEFAULT FALSE,
    pago_total DECIMAL(6,2) NOT NULL,
    propina DECIMAL(4,2) DEFAULT 0.00,
    mesero VARCHAR(13) NOT NULL,
    mesa INT NOT NULL,
    CONSTRAINT pk_pedido PRIMARY KEY (numero_pedido),
    CONSTRAINT fk_pedido_mesero FOREIGN KEY (mesero) REFERENCES personal(dpi),
    CONSTRAINT fk_pedido_mesa FOREIGN KEY (mesa) REFERENCES mesa(numero_mesa)
);

INSERT INTO pedido (hora_liberacion,estado,pago_total,propina,mesero, mesa) VALUES
(NOW(),TRUE,46.00,5.00,'1000000000001', 1),
(NULL,FALSE,58.00,0.00,'1000000000001', 2),
(NOW(),TRUE,84.00,10.00,'1000000000005', 3),
(NULL,FALSE,35.00,0.00,'1000000000005', 5);

CREATE TABLE detalle_cuenta (
    id INT AUTO_INCREMENT,
    producto INT NOT NULL,
    precio DECIMAL(6,2) NOT NULL,
    unidades INT NOT NULL,
    sub_total DECIMAL(6,2) NOT NULL,
    pedido INT NOT NULL,
    CONSTRAINT pk_detalle_cuenta PRIMARY KEY (id),
    CONSTRAINT fk_dc_producto FOREIGN KEY (producto) REFERENCES producto_menu(codigo), 
    CONSTRAINT fk_dc_pedido FOREIGN KEY (pedido) REFERENCES pedido(numero_pedido)
);

INSERT INTO detalle_cuenta (producto,precio,unidades,sub_total,pedido) VALUES
(1,18.00,1,18.00,1),
(5,28.00,1,28.00,1),

(2,28.00,2,56.00,2),
(6,20.00,1,20.00,2),

(7,42.00,2,84.00,3),

(4,35.00,1,35.00,4);
