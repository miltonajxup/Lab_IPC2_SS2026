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

INSERT INTO personal (dpi, salario, rol, jornada) VALUES 
('1000000000001','Juan Pérez',3500.00,1,1),
('1000000000002','Ana López',4200.00,2,2),
('1000000000003','Carlos Méndez',3900.00,3,1),
('1000000000004','María García',6500.00,4,1),
('1000000000005','Pedro Ramírez',3500.00,1,2);

CREATE TABLE tipo_pago (
    id INT AUTO_INCREMENT,
    tipo VARCHAR(20) NOT NULL,
    CONSTRAINT pk_tipo_pago PRIMARY KEY (id)
);

INSERT INTO tipo_pago (tipo) VALUES 
('QUINCENA'),
('FIN_DE_MES');

CREATE TABLE estado_pago (
    id INT AUTO_INCREMENT, 
    estado VARCHAR(20) NOT NULL UNIQUE,
    CONSTRAINT pk_estado PRIMARY KEY (id)
);

INSERT INTO estado_pago (estado) VALUES 
('PENDIENTE'),
('PAGADO');

CREATE TABLE pago (
    codigo_nomina INT AUTO_INCREMENT,
    fecha_emision DATETIME DEFAULT CURRENT_TIMESTAMP,
    monto_a_pagar DECIMAL(6,2) NOT NULL,
    estado INT NOT NULL,
    empleado VARCHAR(12) NOT NULL,
    tipo INT NOT NULL,
    CONSTRAINT pk_pago PRIMARY KEY (codigo_nomina),
    CONSTRAINT fk_estado FOREIGN KEY (estado) REFERENCES estado_pago(id),
    CONSTRAINT fk_empleado FOREIGN KEY (empleado) REFERENCES personal(dpi),
    CONSTRAINT fk_tipo FOREIGN KEY (tipo) REFERENCES tipo_pago(id)
);

INSERT INTO pago
(monto_a_pagar, estado, empleado, tipo)
VALUES
(1750.00,2,'100000000001',1),
(2100.00,1,'100000000002',1),
(3900.00,2,'100000000003',2),
(6500.00,1,'100000000004',2),
(1750.00,2,'100000000005',1);

CREATE TABLE unidad_medida (
    id INT AUTO_INCREMENT,
    unidad VARCHAR(20) NOT NULL,
    CONSTRAINT pk_unidad_medida PRIMARY KEY (id)
);

INSERT INTO unidad_medida (unidad) VALUES 
('Kgs'),
('Lts'),
('Unidad'), 
('Otro');

CREATE TABLE insumo (
    codigo INT AUTO_INCREMENT,
    nombre VARCHAR(30) NOT NULL,
    cantidad_stock INT NOT NULL,
    stock_minimo INT NOT NULL,
    costo DECIMAL(4,2) NOT NULL,
    CONSTRAINT pk_insumo PRIMARY KEY (codigo)
);

INSERT INTO insumo (nombre,cantidad_stock,stock_minimo,costo) VALUES
('Café',30,10,25.50),
('Leche',20,5,12.00),
('Azúcar',50,10,6.50),
('Chocolate',15,5,18.75),
('Harina',40,10,8.25),
('Huevos',100,20,1.25),
('Crema Batida',12,4,22.50),
('Jarabe Vainilla',8,3,35.00);

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
    CONSTRAINT pk_producto PRIMARY KEY (codigo)
);

INSERT INTO producto_menu (nombre,precio) VALUES
('Café Americano',18.00),
('Capuccino',28.00),
('Latte',30.00),
('Frappé',35.00),
('Cheesecake',26.00),
('Brownie',20.00),
('Sandwich Club',42.00),
('Panini',38.00);

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
    mesero VARCHAR(12) NOT NULL,
    CONSTRAINT pk_pedido PRIMARY KEY (numero_pedido),
    CONSTRAINT fk_mesero FOREIGN KEY (mesero) REFERENCES personal(dpi)
);

INSERT INTO pedido (hora_liberacion,estado,pago_total,propina,mesero) VALUES
(NOW(),TRUE,46.00,5.00,'100000000001'),
(NULL,FALSE,58.00,0.00,'100000000005'),
(NOW(),TRUE,84.00,10.00,'100000000001'),
(NULL,FALSE,35.00,0.00,'100000000005');

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