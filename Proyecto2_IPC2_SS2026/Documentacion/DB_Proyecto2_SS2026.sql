CREATE DATABASE IF NOT EXISTS Proyecto2_SS2026;

USE Proyecto2_SS2026;

CREATE TABLE usuario (
    dpi VARCHAR(13) NOT NULL,
    nombre VARCHAR(60) NOT NULL UNIQUE,
    contrasenia VARCHAR(50) NOT NULL,
    rol VARCHAR(25) NOT NULL, 
    CONSTRAINT pk_usuario PRIMARY KEY(dpi)
);

INSERT INTO usuario (dpi, nombre, contrasenia, rol) VALUES ('202610001', 'supadm1', '1234', 'SUPER_ADMINISTRADOR');

CREATE TABLE empleado (
    dpi_empleado VARCHAR(13) NOT NULL, 
    salario DECIMAL(8,2) NOT NULL, 
    fecha_contratacion DATE, 
    CONSTRAINT pk_empleado PRIMARY KEY(dpi_empleado), 
    CONSTRAINT fk_emp_dpi FOREIGN KEY (dpi_empleado) REFERENCES usuario(dpi)
);

CREATE TABLE estudiante (
    dpi_estudiante VARCHAR(13) NOT NULL, 
    numero_encargado VARCHAR(10) NOT NULL, 
    numero_estudiante VARCHAR(10) NOT NULL, 
    estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVO', 
    CONSTRAINT pk_estudiante PRIMARY KEy (dpi_estudiante), 
    CONSTRAINT fk_est_dpi FOREIGN KEY (dpi_estudiante) REFERENCES usuario(dpi)
);

CREATE TABLE informacion_medica (
    dpi_estudiante VARCHAR(13) NOT NULL, 
    condicion_medica VARCHAR(300), 
    alergias VARCHAR(300), 
    medicamentos VARCHAR(300), 
    CONSTRAINT pk_informacion_medica PRIMARY KEY (dpi_estudiante), 
    CONSTRAINT fk_infmed_dpi FOREIGN KEY (dpi_estudiante) REFERENCES estudiante(dpi_estudiante) 
);

CREATE TABLE contrasenias (
    id INT AUTO_INCREMENT,
    usuario_dpi VARCHAR(13) NOT NULL, 
    contrasenia VARCHAR(50) NOT NULL, 
    CONSTRAINT pk_contrasenias PRIMARY KEY (id), 
    CONSTRAINT fk_con_usuario FOREIGN KEY (usuario_dpi) REFERENCES usuario(dpi) 
);

CREATE TABLE ciclo_escolar (
    id INT AUTO_INCREMENT, 
    anio INT NOT NULL, 
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    estado BOOLEAN DEFAULT FALSE,
    CONSTRAINT pk_ciclo_escolar PRIMARY KEY(id)
);

CREATE TABLE libro (
    codigo_isbn VARCHAR(20) NOT NULL,
    titulo VARCHAR(60) NOT NULL, 
    autor VARCHAR(60) NOT NULL, 
    editorial VARCHAR(60) NOT NULL, 
    cantidad_disponible INT NOT NULL DEFAULT 0,
    CONSTRAINT pk_libro PRIMARY KEY(codigo_isbn)
);

CREATE TABLE prestamo_libro (
    id INT AUTO_INCREMENT, 
    fecha_prestamo DATE NOT NULL, 
    fecha_devolucion_esperada DATE NOT NULL, 
    fecha_devolucion_completada DATE, 
    prestamista VARCHAR(13) NOT NULL, 
    libro VARCHAR(20) NOT NULL, 
    CONSTRAINT pk_prestamo_libro PRIMARY KEY(id), 
    CONSTRAINT fk_pl_prestamista FOREIGN KEY (prestamista) REFERENCES usuario(dpi), 
    CONSTRAINT fk_pl_libro FOREIGN KEY (libro) REFERENCES libro(codigo_isbn)
);

CREATE TABLE multa (
    id INT AUTO_INCREMENT, 
    monto DECIMAL(8,2) NOT NULL, 
    estado BOOLEAN DEFAULT FALSE, 
    prestamo_id INT NOT NULL, 
    CONSTRAINT pk_multa PRIMARY KEY(id), 
    CONSTRAINT fk_mul_prestamo FOREIGN KEY(prestamo_id) REFERENCES prestamo_libro(id)
);

CREATE TABLE nivel_academico (
    id INT AUTO_INCREMENT, 
    nombre VARCHAR(60), 
    CONSTRAINT pk_nivel_academico PRIMARY KEY(id) 
);

INSERT INTO nivel_academico (nombre) VALUES 
('PRE-PRIMARIA'), 
('PRIMARIA'), 
('BASICO'), 
('DIVERSIFICADO');

CREATE TABLE grado (
    id INT AUTO_INCREMENT, 
    nombre VARCHAR(60) NOT NULL, 
    costo_colegiatura DECIMAL(8,2), 
    nivel_id INT NOT NULL, 
    CONSTRAINT pk_carrera PRIMARY KEY(id),
    CONSTRAINT fk_gra_nivel_id FOREIGN KEY (nivel_id) REFERENCES nivel_academico(id),
    UNIQUE (nombre, nivel_id) 
);

INSERT INTO grado (nombre, nivel_id) VALUES 
('PARVULOS 1', 1), 
('PARVULOS 2', 1), 
('PRIMERO', 2), 
('SEGUNDO', 2), 
('TERCERO', 2), 
('CUARTO', 2), 
('QUINTO', 2), 
('SEXTO', 2),
('PRIMERO', 3), 
('SEGUNDO', 3), 
('TERCERO', 3), 
('CUARTO', 4), 
('QUINTO', 4), 
('SEXTO', 4);

CREATE TABLE carrera (
    codigo VARCHAR(20) NOT NULL, 
    nombre VARCHAR(60) NOT NULL, 
    estado BOOLEAN NOT NULL DEFAULT TRUE, 
    grado_id INT NOT NULL, 
    CONSTRAINT pk_carrera PRIMARY KEY(codigo), 
    CONSTRAINT fK_carre_grado_id FOREIGN KEY (grado_id) REFERENCES grado(id) 
);

CREATE TABLE seccion (
    id INT AUTO_INCREMENT, 
    nombre VARCHAR(1) NOT NULL, 
    CONSTRAINT pk_seccion PRIMARY KEY(id) 
);

INSERT INTO seccion (nombre) VALUES ('A'), ('B'), ('C'), ('D');

CREATE TABLE grupo_ciclo_escolar (
    id INT AUTO_INCREMENT, 
    ciclo_escolar_id INT NOT NULL, 
    grado_id INT NOT NULL, 
    carrera_id VARCHAR(20), 
    seccion_id INT NOT NULL, 
    CONSTRAINT pk_grupo_cliclo_escolar PRIMARY KEY (id), 
    CONSTRAINT fk_gce_ciclo FOREIGN KEY (ciclo_escolar_id) REFERENCES ciclo_escolar(id), 
    CONSTRAINT fk_gce_grado FOREIGN KEY (grado_id) REFERENCES grado(id), 
    CONSTRAINT fk_gce_carrera FOREIGN KEY (carrera_id) REFERENCES carrera(codigo), 
    CONSTRAINT fk_ins_seccion FOREIGN KEY (seccion_id) REFERENCES seccion(id) 
);

CREATE TABLE inscripcion (
    id INT AUTO_INCREMENT, 
    estudiante VARCHAR(13) NOT NULL, 
    grupo_id INT NOT NULL, 
    CONSTRAINT pk_inscripcion PRIMARY KEY(id), 
    CONSTRAINT fk_ins_estudiante FOREIGN KEY (estudiante) REFERENCES estudiante(dpi_estudiante), 
    CONSTRAINT fk_ins_grupo FOREIGN KEY (grupo_id) REFERENCES grupo_ciclo_escolar(id), 
    UNIQUE (estudiante, grupo_id) 
);

CREATE TABLE curso (
    id INT AUTO_INCREMENT, 
    nombre VARCHAR(60) NOT NULL, 
    descripcion VARCHAR(300), 
    CONSTRAINT pk_curso PRIMARY KEY(id) 
); 

CREATE TABLE curriculo (
    id INT AUTO_INCREMENT, 
    curso_id INT NOT NULL, 
    grado_id INT NOT NULL, 
    carrera_id VARCHAR(20), 
    CONSTRAINT pk_curriculo PRIMARY KEY(id), 
    CONSTRAINT fk_curri_curso FOREIGN KEY (curso_id) REFERENCES curso(id), 
    CONSTRAINT fk_curri_grado FOREIGN KEY (grado_id) REFERENCES grado(id), 
    CONSTRAINT fk_curri_carrera FOREIGN KEY (carrera_id) REFERENCES carrera(codigo)
); 

CREATE TABLE cursos_estudiante (
    id INT AUTO_INCREMENT, 
    estudiante_id VARCHAR(13) NOT NULL, 
    curso_id INT NOT NULL, 
    CONSTRAINT pk_cursos_estudiante PRIMARY KEY(id), 
    CONSTRAINT fk_cures_estudiante FOREIGN KEY (estudiante_id) REFERENCES estudiante(dpi_estudiante), 
    CONSTRAINT fk_cures_curso FOREIGN KEY (curso_id) REFERENCES curso(id), 
    UNIQUE (estudiante_id, curso_id) 
);

CREATE TABLE cursos_maestro (
    id INT AUTO_INCREMENT, 
    maestro_id VARCHAR(13) NOT NULL, 
    curso_id INT NOT NULL, 
    CONSTRAINT pk_cursos_maestro PRIMARY KEY (id), 
    CONSTRAINT fk_curmae_maestro FOREIGN KEY (maestro_id) REFERENCES empleado(dpi_empleado), 
    CONSTRAINT fk_curmae_curso FOREIGN KEY (curso_id) REFERENCES curso(id), 
    UNIQUE (maestro_id, curso_id) 
);

CREATE TABLE asistencia (
    id INT AUTO_INCREMENT, 
    curso_estudiante_id INT NOT NULL, 
    fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, 
    estado BOOLEAN NOT NULL, 
    CONSTRAINT pk_asistencia PRIMARY KEY (id), 
    CONSTRAINT fk_asis_cures FOREIGN KEY (curso_estudiante_id) REFERENCES cursos_estudiante(id), 
    UNIQUE (curso_estudiante_id, fecha) 
); 

CREATE TABLE boleta_pago (
    id INT AUTO_INCREMENT, 
    monto DECIMAL(8,2) NOT NULL, 
    estado BOOLEAN DEFAULT FALSE, 
    tipo_pago VARCHAR(30) NOT NULL, 
    mes_pago TINYINT, 
    ciclo_escolar_id INT, 
    estudiante_dpi VARCHAR(13) NOT NULL, 
    CONSTRAINT pk_boleta_pago PRIMARY KEY (id), 
    CONSTRAINT fk_bolp_ciclo FOREIGN KEY (ciclo_escolar_id) REFERENCES ciclo_escolar(id), 
    CONSTRAINT fk_bolp_estud FOREIGN KEY (estudiante_dpi) REFERENCES estudiante(dpi_estudiante) 
);

CREATE TABLE pago (
    id INT AUTO_INCREMENT, 
    fecha_pago DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, 
    monto_pagado DECIMAL(8,2) NOT NULL, 
    boleta_id INT NOT NULL, 
    secretaria_dpi VARCHAR(13) NOT NULL, 
    CONSTRAINT pk_pago PRIMARY KEY (id), 
    CONSTRAINT fk_pago_boleta FOREIGN KEY (boleta_id) REFERENCES boleta_pago (id), 
    CONSTRAINT fk_pago_secre FOREIGN KEY (secretaria_dpi) REFERENCES empleado(dpi_empleado) 
);
