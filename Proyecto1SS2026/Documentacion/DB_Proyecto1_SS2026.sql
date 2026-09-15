DROP DATABASE IF EXISTS Proyecto1_SS2026;
CREATE DATABASE IF NOT EXISTS Proyecto1_SS2026;

USE Proyecto1_SS2026;

CREATE TABLE sucursal (
    codigo_sucursal VARCHAR(20),
    nombre VARCHAR (40) NOT NULL,
    ciudad VARCHAR (60) NOT NULL, 
    CONSTRAINT pk_sucursal PRIMARY KEY (codigo_sucursal)
);

CREATE TABLE chofer (
    numero_de_licencia VARCHAR(13) NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    foto MEDIUMBLOB,
    tipo_de_licencia VARCHAR(10) NOT NULL UNIQUE, 
    fecha_vencimiento DATE NOT NULL,
    numero_telefono VARCHAR(10) NOT NULL,
    salario_por_viaje DECIMAL(8,2) DEFAULT 0.00,
    saldo_disponible DECIMAL(10,2) DEFAULT 0.00, 
    estado_operativo BOOLEAN DEFAULT TRUE,
    sucursal_base VARCHAR(40) NOT NULL,
    sucursal_actual VARCHAR(40) NOT NULL,
    CONSTRAINT pk_chofer PRIMARY KEY (numero_de_licencia),
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
    estado_operativo BOOLEAN DEFAULT TRUE, 
    sucursal_base VARCHAR(40) NOT NULL,
    sucursal_actual VARCHAR(40) NOT NULL,
    CONSTRAINT pk_bus PRIMARY KEY (numero_placa),
    CONSTRAINT fk_bus_sucursal_base FOREIGN KEY (sucursal_base) REFERENCES sucursal(codigo_sucursal),
    CONSTRAINT fk_bus_sucursal_actual FOREIGN KEY (sucursal_actual) REFERENCES sucursal(codigo_sucursal)
);

CREATE TABLE depreciacion (
    id INT AUTO_INCREMENT, 
    monto_depreciacion DECIMAL(8,2), 
    CONSTRAINT pk_depreciacion PRIMARY KEY (id)
);

INSERT INTO depreciacion (monto_depreciacion) VALUES (0.5);

CREATE TABLE depreciacion_bus (
    id INT AUTO_INCREMENT, 
    fecha_registro DATE, 
    kilometros_recorridos INT NOT NULL, 
    depreciacion_id INT NOT NULL, 
    monto_depreciado DECIMAL(8,2) NOT NULL, 
    bus VARCHAR(10) NOT NULL, 
    CONSTRAINT pk_depreciacion_bus PRIMARY KEY (id), 
    CONSTRAINT fk_depbus_depid FOREIGN KEY (depreciacion_id) REFERENCES depreciacion(id), 
    CONSTRAINT fk_depbus_bus FOREIGN KEY (bus) REFERENCES bus(numero_placa)
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
    sucursal_registro VARCHAR(40) NOT NULL, 
    sucursal_origen VARCHAR(40) NOT NULL, 
    sucursal_destino VARCHAR(40) NOT NULL, 
    ruta_habilitada BOOLEAN DEFAULT TRUE, 
    CONSTRAINT pk_ruta PRIMARY KEY (id),
    CONSTRAINT fk_ruta_registro FOREIGN KEY (sucursal_registro) REFERENCES sucursal(codigo_sucursal), 
    CONSTRAINT fk_ruta_origen FOREIGN KEY (sucursal_origen) REFERENCES sucursal(codigo_sucursal), 
    CONSTRAINT fk_ruta_destino FOREIGN KEY (sucursal_destino) REFERENCES sucursal(codigo_sucursal)
);

CREATE TABLE horario_ruta (
    id INT AUTO_INCREMENT, 
    hora_salida TIME NOT NULL, 
    hora_aprox_llegada TIME NOT NULL, 
    ruta INT NOT NULL, 
    CONSTRAINT pk_horario PRIMARY KEY (id), 
    CONSTRAINT fk_hr_ruta FOREIGN KEY (ruta) REFERENCES ruta(id)
);

CREATE TABLE viaje (
    id INT AUTO_INCREMENT, 
    chofer VARCHAR(13) NOT NULL, 
    bus VARCHAR(10) NOT NULL, 
    CONSTRAINT pk_viaje PRIMARY KEY (id),
    CONSTRAINT fk_v_chofer FOREIGN KEY (chofer) REFERENCES chofer(numero_de_licencia),
    CONSTRAINT fk_v_bus FOREIGN KEY (bus) REFERENCES bus(numero_placa)
);

CREATE TABLE viaje_publico (
    id_viaje INT NOT NULL, 
    fecha_salida DATE, 
    horario INT NOT NULL, 
    CONSTRAINT pk_viaje_publico PRIMARY KEY (id_viaje),
    CONSTRAINT fk_vpub_id_viaje FOREIGN KEY (id_viaje) REFERENCES viaje(id) ON DELETE CASCADE,
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
    usuario_solicitante VARCHAR(13) NOT NULL, 
    estado_viaje VARCHAR(20) DEFAULT 'EN_REVISION', 
    CONSTRAINT pk_viaje_privado PRIMARY KEY (id_viaje),
    CONSTRAINT fk_vpriv_id_viaje FOREIGN KEY (id_viaje) REFERENCES viaje(id) ON DELETE CASCADE
); 

CREATE TABLE viaje_ejecucion (
    id INT AUTO_INCREMENT, 
    hora_salida TIME DEFAULT (CURRENT_TIME),
    kilometraje_salida INT NOT NULL, 
    hora_llegada TIME, 
    kilometraje_llegada INT, 
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
    direccion VARCHAR(75) NOT NULL, 
    credito_disponible DECIMAL(8,2) DEFAULT 0.00, 
    estado BOOLEAN DEFAULT TRUE,
    rol VARCHAR(30) NOT NULL,
    CONSTRAINT pk_usuario PRIMARY KEY (dpi)
);

INSERT INTO usuario (dpi, nombre, nit, telefono, direccion, rol) VALUES ('1111111111111', 'admin1', '1111111111110', '12345678', 'guatemala', 'ADMINISTRADOR');

CREATE TABLE admin_sucursal (
    dpi VARCHAR(13) NOT NULL, 
    sucursal VARCHAR(40) NOT NULL, 
    CONSTRAINT pk_admin_sucursal PRIMARY KEY (dpi), 
    CONSTRAINT fk_as_dpi FOREIGN KEY (dpi) REFERENCES usuario(dpi) ON DELETE CASCADE, 
    CONSTRAINT fk_as_sucursal FOREIGN KEY (sucursal) REFERENCES sucursal(codigo_sucursal)
);

CREATE TABLE boleto_viaje (
    id INT AUTO_INCREMENT, 
    usuario VARCHAR(13) NOT NULL, 
    viaje INT NOT NULL, 
    asiento INT NOT NULL, 
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP, 
    CONSTRAINT pk_viaje_usuario PRIMARY KEY (id), 
    CONSTRAINT fk_vusu_usuario FOREIGN KEY (usuario) REFERENCES usuario(dpi),
    CONSTRAINT fk_vusu_viaje FOREIGN KEY (viaje) REFERENCES viaje(id) 
);
