DROP DATABASE IF EXISTS Proyecto1_SS2026;
CREATE DATABASE IF NOT EXISTS Proyecto1_SS2026;

USE Proyecto1_SS2026;

CREATE TABLE sucursal (
    codigo_sucursal VARCHAR(20),
    nombre VARCHAR (40),
    ciudad VARCHAR (60),
    CONSTRAINT pk_sucursal PRIMARY KEY (codigo_sucursal)
);

CREATE TABLE chofer (
    dpi VARCHAR(13),
    nombre VARCHAR(50) NOT NULL,
    foto MEDIUMBLOB,
    numero_de_licencia VARCHAR(13) NOT NULL,
    tipo_de_licencia VARCHAR(10) NOT NULL,
    fecha_vencimiento DATE NOT NULL,
    numero_telefono DATE NOT NULL,
    salario_por_viaje DECIMAL(8,2),
    estado_operativo BOOLEAN DEFAULT TRUE
    sucursal_base VARCHAR(20) NOT NULL,
    sucursal_actual VARCHAR(20) NOT NULL,
    CONSTRAINT pk_chofer PRIMARY KEY (dpi),
    CONSTRAINT fk_cho_sucursal_base FOREIGN KEY (sucursal_base) REFERENCES sucursal(codigo_sucursal),
    CONSTRAINT fk_cho_sucursal_actual FOREIGN KEY (sucursal_actual) REFERENCES sucursal(codigo_sucursal)
);

CREATE TABLE bus (
    numero_placa VARCHAR(10),
    foto MEDIUMBLOB,
    marca VARCHAR(30) NOT NULL, 
    modelo VARCHAR(30) NOT NULL, 
    fecha_fabricacion DATE NOT NULL, 
    capacidad_pasajeros INT NOT NULL, 
    kilometraje INT NOT NULL,  
    estado_operativo BOOLEAN DEFAULT TRUE
    sucursal_base VARCHAR(20) NOT NULL,
    sucursal_actual VARCHAR(20) NOT NULL,
    CONSTRAINT pk_bus PRIMARY KEY (numero_placa),
    CONSTRAINT fk_bus_sucursal_base FOREIGN KEY (sucursal_base) REFERENCES sucursal(codigo_sucursal),
    CONSTRAINT fk_bus_sucursal_actual FOREIGN KEY (sucursal_actual) REFERENCES sucursal(codigo_sucursal)
);

CREATE TABLE depreciacion (
    id INT AUTO_INCREMENT, 
    monto_depreciacion DECIMAL(8,2), 
    CONSTRAINT pk_depreciacion PRIMARY KEY (id)
);

CREATE TABLE depreciacion_bus (
    id INT AUTO_INCREMENT, 
    kilometros_recorridos INT NOT NULL, 
    bus VARCHAR(10) NOT NULL, 
    depreciacion INT NOT NULL, 
    CONSTRAINT pk_depreciacion_bus PRIMARY KEY (id), 
    CONSTRAINT fk_depbus_bus FOREIGN KEY (bus) REFERENCES bus(numero_placa),
    CONSTRAINT fk_depbus_dep FOREIGN KEY (depreciacion) REFERENCES depreciacion(id)
);

CREATE TABLE gasto_taller (
    id INT AUTO_INCREMENT, 
    monto_mano_obra DECIMAL(8,2), 
    monto_repuestos DECIMAL(8,2), 
    fecha_mantenimiento DATE NOT NULL, 
    bus VARCHAR(10) NOT NULL, 
    CONSTRAINT pk_gasto_taller PRIMARY KEY (id),
    CONSTRAINT fk_gt_bus FOREIGN KEY (bus) REFERENCES bus(numero_placa)
);

CREATE TABLE ruta (
    id INT AUTO_INCREMENT, 
    distancia_aproximada INT NOT NULL, 
    precio_boleto DECIMAL(8,2) NOT NULL, 
    -- sucursal_base VARCHAR(20) NOT NULL, 
    sucursal_origen VARCHAR(20) NOT NULL, 
    sucursal_destino VARCHAR(20) NOT NULL, 
    CONSTRAINT pk_ruta PRIMARY KEY (id),
    -- CONSTRAINT fk_ruta_base FOREIGN KEY (sucursal_base) REFERENCES sucursal(codigo_sucursal), 
    CONSTRAINT fk_ruta_origen FOREIGN KEY (sucursal_origen) REFERENCES sucursal(codigo_sucursal), 
    CONSTRAINT fk_ruta_destino FOREIGN KEY (sucursal_destino) REFERENCES sucursal(codigo_sucursal)
);

CREATE TABLE horario_ruta (
    id INT AUTO_INCREMENT, 
    hora_salida TIME NOT NULL, 
    hora_aprox_llegada TIME NOT NULL, 
    ruta_habilitada DEFAULT TRUE, 
    ruta INT NOT NULL, 
    CONSTRAINT pk_horario PRIMARY KEY (id), 
    CONSTRAINT fk_hr_ruta FOREIGN KEY (ruta) REFERENCES ruta(id)
);

CREATE TABLE viaje (
    id INT AUTO_INCREMENT, 
    chofer VARCHAR(13) NOT NULL, 
    bus VARCHAR(10) NOT NULL, 
    CONSTRAINT pk_viaje PRIMARY KEY (id),
    CONSTRAINT fk_v_chofer FOREIGN KEY (chofer) REFERENCES chofer(dpi),
    CONSTRAINT fk_v_bus FOREIGN KEY (bus) REFERENCES bus(numero_placa)
);

CREATE TABLE viaje_pubico (
    id_viaje INT NOT NULL, 
    fecha_salida DATE, 
    horario INT NOT NULL, 
    CONSTRAINT pk_viaje_publico PRIMARY KEY (id_viaje),
    CONSTRAINT fk_vpub_id_viaje FOREIGN KEY (id_viaje) REFERENCES viaje(id),
    CONSTRAINT fk_vpub_horario FOREIGN KEY (horario) REFERENCES horario_ruta(id)
); 

CREATE TABLE viaje_privado (
    id_viaje INT NOT NULL, 
    cantidad_pasajeros INT NOT NULL, 
    origen VARCHAR(40) NOT NULL, 
    destino VARCHAR(40) NOT NULL, 
    distancia_aproximada INT NOT NULL, 
    hora_salida TIME NOT NULL, 
    hora_aprox_llegada TIME NOT NULL, 
    fecha_salida DATE NOT NULL, 
    fecha_llegada DATE, 
    costo DECIMAL(8,2) NOT NULL, 
    estado_pago DECIMAL(8,2) NOT NULL, 
    CONSTRAINT pk_viaje_privado PRIMARY KEY (id_viaje),
    CONSTRAINT fk_vpriv_id_viaje FOREIGN KEY (id_viaje) REFERENCES viaje(id),
); 

CREATE TABLE viaje_ejecucion (
    id INT AUTO_INCREMENT, 
    hora_salida TIME DEFAULT CURRENT_TIME,
    kilometraje_salida INT NOT NULL, 
    hora_llegada TIME, 
    kilometraje_llegada INT NOT NULL, 
    gasto_combustible DECIMAL(8,2),
    viaje_id INT NOT NULL, 
    CONSTRAINT pk_viaje_ejecucion PRIMARY KEY (id), 
    CONSTRAINT fk_veje_viaje_id FOREIGN KEY (viaje_id) REFERENCES viaje(id)
);

CREATE TABLE usuario (
    dpi VARCHAR(13) NOT NULL, 
    nombre VARCHAR(50) NOT NULL, 
    nit VARCHAR(13) NOT NULL, 
    telefono VARCHAR(10) NOT NULL, 
    direccion VARCHAR(25) NOT NULL, 
    credito_disponible DECIMAL(8,2) DEFAULT 0.00, 
    estado BOOLEAN DEFAULT TRUE,
    CONSTRAINT pk_usuario PRIMARY KEY (dpi)
);

CREATE TABLE viaje_usuario (
    usuario VARCHAR(13) NOT NULL, 
    viaje INT NOT NULL, 
    asiento INT NOT NULL, 
    monto_pagado DECIMAL(8,2) NOT NULL, 
    fecha DATE NOT NULL, 
    CONSTRAINT pk_viaje_usuario PRIMARY KEY (usuario, viaje), 
    CONSTRAINT fk_vusu_usuario FOREIGN KEY (usuario) REFERENCES usuario(dpi),
    CONSTRAINT fk_vusu_viaje FOREIGN KEY (viaje) REFERENCES viaje(id) 
);
