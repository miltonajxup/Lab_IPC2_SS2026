DROP DATABASE IF EXISTS Proyecto2_SS2026;
CREATE DATABASE IF NOT EXISTS Proyecto2_SS2026;

USE Proyecto2_SS2026;

CREATE TABLE ciclo_escolar (
    anio INT NOT NULL, 
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    estado BOOLEAN DEFAULT FALSE,
    CONSTRAINT pk_ciclo_escolar PRIMARY KEY(anio) 
);

CREATE TABLE usuario (
    dpi VARCHAR(13) NOT NULL,
    nombre VARCHAR(60) NOT NULL UNIQUE,
    contrasenia VARCHAR(255),
    rol VARCHAR(25) NOT NULL, 
    CONSTRAINT pk_usuario PRIMARY KEY(dpi)
);

INSERT INTO usuario (dpi, nombre, rol) VALUES ('202600001', 'supadm1', 'SUPER_ADMINISTRADOR');

CREATE TABLE empleado (
    dpi_empleado VARCHAR(13) NOT NULL, 
    CONSTRAINT pk_empleado PRIMARY KEY(dpi_empleado), 
    CONSTRAINT fk_emp_dpi FOREIGN KEY (dpi_empleado) REFERENCES usuario(dpi)
);

CREATE TABLE contrato_empleado (
    empleado_dpi VARCHAR(13) NOT NULL, 
    ciclo_escolar_id INT NOT NULL, 
    fecha_contratacion DATE NOT NULL DEFAULT (CURRENT_DATE), 
    salario DECIMAL(8,2) NOT NULL, 
    CONSTRAINT pk_contrato_empleado PRIMARY KEY (empleado_dpi, ciclo_escolar_id), 
    CONSTRAINT fk_ce_empleado FOREIGN KEY (empleado_dpi) REFERENCES empleado(dpi_empleado), 
    CONSTRAINT fk_ce_ciclo_escolar FOREIGN KEY (ciclo_escolar_id) REFERENCES ciclo_escolar(anio) 
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
    condicion_medica VARCHAR(300) NOT NULL DEFAULT '', 
    alergias VARCHAR(300) NOT NULL DEFAULT '', 
    medicamentos VARCHAR(300) NOT NULL DEFAULT '', 
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

CREATE TABLE libro (
    codigo_isbn VARCHAR(20) NOT NULL,
    titulo VARCHAR(60) NOT NULL, 
    autor VARCHAR(60) NOT NULL, 
    editorial VARCHAR(60) NOT NULL, 
    cantidad_disponible INT NOT NULL DEFAULT 0,
    CONSTRAINT pk_libro PRIMARY KEY(codigo_isbn)
);

CREATE TABLE prestamo_biblioteca (
    id INT AUTO_INCREMENT, 
    fecha_prestamo DATE NOT NULL, 
    fecha_devolucion_esperada DATE NOT NULL, 
    fecha_devolucion_completada DATE, 
    prestamista VARCHAR(13) NOT NULL, 
    CONSTRAINT pk_prestamo_biblioteca PRIMARY KEY(id), 
    CONSTRAINT fk_pb_prestamista FOREIGN KEY (prestamista) REFERENCES usuario(dpi)
);

CREATE TABLE libro_prestamo (
    id_prestamo INT NOT NULL, 
    codigo_libro VARCHAR(20) NOT NULL, 
    cantidad INT NOT NULL, 
    CONSTRAINT pk_libro_prestamo PRIMARY KEY (id_prestamo, codigo_libro), 
    CONSTRAINT fk_lp_prestamo FOREIGN KEY (id_prestamo) REFERENCES prestamo_biblioteca(id), 
    CONSTRAINT fk_lp_libro FOREIGN KEY (codigo_libro) REFERENCES libro(codigo_isbn) 
);

CREATE TABLE multa (
    id INT AUTO_INCREMENT, 
    monto DECIMAL(8,2) NOT NULL, 
    estado BOOLEAN DEFAULT FALSE, 
    fecha_pago DATE, 
    prestamo_id INT NOT NULL, 
    CONSTRAINT pk_multa PRIMARY KEY(id), 
    CONSTRAINT fk_mul_prestamo FOREIGN KEY(prestamo_id) REFERENCES prestamo_biblioteca(id)
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
    costo_inscripcion DECIMAL(8,2), 
    costo_colegiatura DECIMAL(8,2), 
    nivel_id INT NOT NULL, 
    CONSTRAINT pk_carrera PRIMARY KEY(id),
    CONSTRAINT fk_gra_nivel_id FOREIGN KEY (nivel_id) REFERENCES nivel_academico(id),
    UNIQUE uk_grado (nombre, nivel_id) 
);

INSERT INTO grado (nombre, costo_inscripcion, costo_colegiatura, nivel_id) VALUES 
('PARVULOS 1', 300, 400, 1),  -- 1
('PARVULOS 2', 300, 400, 1),  -- 2

('PRIMERO', 400, 500, 2),     -- 3
('SEGUNDO', 400, 500, 2),     -- 4
('TERCERO', 400, 500, 2),     -- 5
('CUARTO', 450, 600, 2),      -- 6
('QUINTO', 450, 600, 2),      -- 7
('SEXTO', 450, 600, 2),       -- 8

('PRIMERO', 500, 600, 3),     -- 9
('SEGUNDO', 550, 700, 3),     -- 10
('TERCERO', 550, 700, 3),     -- 11

('CUARTO', 600, 800, 4),      -- 12
('QUINTO', 700, 900, 4),      -- 13
('SEXTO', 800, 1000, 4);      -- 14

CREATE TABLE carrera (
    codigo VARCHAR(20) NOT NULL, 
    nombre VARCHAR(60) NOT NULL, 
    estado BOOLEAN NOT NULL DEFAULT TRUE, 
    CONSTRAINT pk_carrera PRIMARY KEY(codigo), 
    UNIQUE uk_carrera (nombre) 
);

CREATE TABLE grado_carrera (
    carrera_id VARCHAR(20) NOT NULL, 
    grado_id INT NOT NULL, 
    CONSTRAINT pk_grado_carrera PRIMARY KEY (carrera_id, grado_id), 
    CONSTRAINT fk_grac_carrera FOREIGN KEY (carrera_id) REFERENCES carrera(codigo), 
    CONSTRAINT fk_grac_grado FOREIGN KEY (grado_id) REFERENCES grado(id)
);

CREATE TABLE seccion (
    nombre VARCHAR(1) NOT NULL, 
    CONSTRAINT pk_seccion PRIMARY KEY(nombre) 
);

INSERT INTO seccion (nombre) VALUES ('A'), ('B'), ('C'), ('D');

CREATE TABLE grupo_ciclo_escolar (
    id INT AUTO_INCREMENT, 
    ciclo_escolar_id INT NOT NULL, 
    grado_id INT NOT NULL, 
    carrera_id VARCHAR(20), 
    seccion_id VARCHAR(1) NOT NULL, 
    CONSTRAINT pk_grupo_cliclo_escolar PRIMARY KEY (id), 
    CONSTRAINT fk_gce_ciclo FOREIGN KEY (ciclo_escolar_id) REFERENCES ciclo_escolar(anio), 
    CONSTRAINT fk_gce_grado FOREIGN KEY (grado_id) REFERENCES grado(id), 
    CONSTRAINT fk_gce_carrera FOREIGN KEY (carrera_id) REFERENCES carrera(codigo), 
    CONSTRAINT fk_ins_seccion FOREIGN KEY (seccion_id) REFERENCES seccion(nombre) 
);

CREATE TABLE inscripcion (
    id INT AUTO_INCREMENT, 
    estudiante VARCHAR(13) NOT NULL, 
    grupo_id INT NOT NULL, 
    estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVO', 
    CONSTRAINT pk_inscripcion PRIMARY KEY(id), 
    CONSTRAINT fk_ins_estudiante FOREIGN KEY (estudiante) REFERENCES estudiante(dpi_estudiante), 
    CONSTRAINT fk_ins_grupo FOREIGN KEY (grupo_id) REFERENCES grupo_ciclo_escolar(id), 
    UNIQUE uk_inscripcion (estudiante, grupo_id) 
);

CREATE TABLE curso (
    id INT AUTO_INCREMENT, 
    nombre VARCHAR(60) NOT NULL, 
    descripcion VARCHAR(300) DEFAULT '', 
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

CREATE TABLE curso_estudiante (
    id INT AUTO_INCREMENT, 
    estudiante_id VARCHAR(13) NOT NULL, 
    curso_id INT NOT NULL, 
    inscripcion_id INT NOT NULL, 
    CONSTRAINT pk_cursos_estudiante PRIMARY KEY(id), 
    CONSTRAINT fk_cures_estudiante FOREIGN KEY (estudiante_id) REFERENCES estudiante(dpi_estudiante), 
    CONSTRAINT fk_cures_curso FOREIGN KEY (curso_id) REFERENCES curso(id), 
    CONSTRAINT fk_cures_inscripcion FOREIGN KEY (inscripcion_id) REFERENCES inscripcion(id), 
    UNIQUE uk_curso_estudiante (estudiante_id, curso_id, inscripcion_id) 
);

CREATE TABLE curso_maestro (
    id INT AUTO_INCREMENT, 
    maestro_id VARCHAR(13) NOT NULL, 
    curso_id INT NOT NULL, 
    grupo_id INT NOT NULL, 
    CONSTRAINT pk_cursos_maestro PRIMARY KEY (id), 
    CONSTRAINT fk_curmae_maestro FOREIGN KEY (maestro_id) REFERENCES empleado(dpi_empleado), 
    CONSTRAINT fk_curmae_curso FOREIGN KEY (curso_id) REFERENCES curso(id), 
    CONSTRAINT fk_curmae_grupo FOREIGN KEY (grupo_id) REFERENCES grupo_ciclo_escolar(id), 
    UNIQUE uk_curso_maestro (maestro_id, curso_id, grupo_id) 
);

CREATE TABLE asistencia (
    id INT AUTO_INCREMENT, 
    curso_estudiante_id INT NOT NULL, 
    fecha DATE NOT NULL, 
    estado BOOLEAN NOT NULL DEFAULT FALSE,  
    CONSTRAINT pk_asistencia PRIMARY KEY (id), 
    CONSTRAINT fk_asis_cures FOREIGN KEY (curso_estudiante_id) REFERENCES curso_estudiante(id), 
    UNIQUE uk_asistencia (curso_estudiante_id, fecha) 
); 

CREATE TABLE notas (
    id INT AUTO_INCREMENT, 
    zona INT NOT NULL DEFAULT 0, 
    examen_final INT NOT NULL DEFAULT 0, 
    curso_estudiante_id INT NOT NULL, 
    CONSTRAINT pk_notas PRIMARY KEY (id), 
    CONSTRAINT fk_notas_cur_estudiante FOREIGN KEY (curso_estudiante_id) REFERENCES curso_estudiante(id) 
);

CREATE TABLE boleta_pago (
    id INT AUTO_INCREMENT, 
    monto DECIMAL(8,2) NOT NULL, 
    estado BOOLEAN DEFAULT FALSE, 
    tipo_pago VARCHAR(30) NOT NULL, 
    mes_pago VARCHAR(20), 
    ciclo_escolar_id INT NOT NULL, 
    estudiante_dpi VARCHAR(13) NOT NULL, 
    CONSTRAINT pk_boleta_pago PRIMARY KEY (id), 
    CONSTRAINT fk_bolp_ciclo FOREIGN KEY (ciclo_escolar_id) REFERENCES ciclo_escolar(anio), 
    CONSTRAINT fk_bolp_estud FOREIGN KEY (estudiante_dpi) REFERENCES estudiante(dpi_estudiante), 
    UNIQUE uk_boleta_estudiante (ciclo_escolar_id, mes_pago, estudiante_dpi, tipo_pago) 
);

CREATE TABLE pago (
    id INT AUTO_INCREMENT, 
    fecha_pago DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, 
    boleta_id INT NOT NULL, 
    secretaria_dpi VARCHAR(13) NOT NULL, 
    CONSTRAINT pk_pago PRIMARY KEY (id), 
    CONSTRAINT fk_pago_boleta FOREIGN KEY (boleta_id) REFERENCES boleta_pago (id), 
    CONSTRAINT fk_pago_secre FOREIGN KEY (secretaria_dpi) REFERENCES empleado(dpi_empleado) 
);

CREATE TABLE pago_empleado (
    id INT AUTO_INCREMENT, 
    fecha_pago DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, 
    monto_pagado DECIMAL(8,2) NOT NULL, 
    mes_pago VARCHAR(20) NOT NULL, 
    ciclo_escolar_id INT NOT NULL, 
    empleado_pagado VARCHAR(13), 
    CONSTRAINT pk_pago_empleado PRIMARY KEY (id), 
    CONSTRAINT fk_paemp_ciclo FOREIGN KEY (ciclo_escolar_id) REFERENCES ciclo_escolar(anio), 
    CONSTRAINT fk_paemp_empleado FOREIGN KEY (empleado_pagado) REFERENCES empleado(dpi_empleado) 
);


-- ==========================================
-- 10 ALUMNOS
-- ==========================================

INSERT INTO usuario (dpi, nombre, rol) VALUES
('202610001', 'juan perez', 'ESTUDIANTE'),
('202610002', 'maria lopez', 'ESTUDIANTE'),
('202610003', 'carlos gomez', 'ESTUDIANTE'),
('202610004', 'ana martinez', 'ESTUDIANTE'),
('202610005', 'pedro hernandez', 'ESTUDIANTE'),
('202610006', 'sofia ramirez', 'ESTUDIANTE'),
('202610007', 'diego castillo', 'ESTUDIANTE'),
('202610008', 'lucia morales', 'ESTUDIANTE'),
('202610009', 'andres velasquez', 'ESTUDIANTE'),
('202610010', 'ana rodriguez', 'ESTUDIANTE');

-- ==========================================
-- 2 MAESTROS
-- ==========================================

INSERT INTO usuario (dpi, nombre, rol) VALUES
('202612001', 'profesor carlos', 'MAESTRO'),
('202612002', 'profesora ana', 'MAESTRO');

-- ==========================================
-- DATOS DE LOS 10 ALUMNOS
-- ==========================================

INSERT INTO estudiante 
    (dpi_estudiante, numero_encargado, numero_estudiante, estado) 
VALUES
('202610001', '55550001', '55560001', 'ACTIVO'),
('202610002', '55550002', '55560002', 'ACTIVO'),
('202610003', '55550003', '55560003', 'ACTIVO'),
('202610004', '55550004', '55560004', 'ACTIVO'),
('202610005', '55550005', '55560005', 'ACTIVO'),
('202610006', '55550006', '55560006', 'ACTIVO'),
('202610007', '55550007', '55560007', 'ACTIVO'),
('202610008', '55550008', '55560008', 'ACTIVO'),
('202610009', '55550009', '55560009', 'ACTIVO'),
('202610010', '55550010', '55560010', 'ACTIVO');


-- ==========================================
-- DATOS DE LOS 2 MAESTROS
-- ==========================================

INSERT INTO empleado (dpi_empleado) VALUES ('202612001'), ('202612002');

INSERT INTO ciclo_escolar (anio, fecha_inicio, fecha_fin, estado) VALUES (2024, '2024-01-15', '2024-10-17', TRUE);
INSERT INTO grupo_ciclo_escolar (ciclo_escolar_id, grado_id, seccion_id) VALUES (2024, 11, 'A');
INSERT INTO inscripcion (estudiante, grupo_id) VALUES ('202610002', 1);
INSERT INTO curso (nombre, descripcion) VALUES ('Matemáticas 3', 'Curso de Matemáticas ');
INSERT INTO curriculo (curso_id, grado_id) VALUES (1, 11);
-- INSERT INTO curso_estudiante (estudiante_id, curso_curriculo) VALUES ('202610002', 1);

INSERT INTO curso (nombre, descripcion) VALUES
('Comunicación y Lenguaje', 'Curso de comunicación y lenguaje'),
('Ciencias Naturales', 'Curso de ciencias naturales'),
('Ciencias Sociales', 'Curso de ciencias sociales'),
('Formación Ciudadana', 'Curso de formación ciudadana'),
('Educación Física', 'Curso de educación física'),
('Expresión Artística', 'Curso de expresión artística'),
('Medio Social y Natural', 'Curso de medio social y natural'),
('Destrezas de Aprendizaje', 'Curso de destrezas de aprendizaje'),
('Inglés', 'Curso de idioma inglés'),
('Computación', 'Curso de computación'),
('Ortografía', 'Curso de ortografía'),
('Lectura', 'Curso de lectura'),
('Escritura', 'Curso de escritura'),
('Geometría', 'Curso de geometría'),
('Álgebra', 'Curso de álgebra'),
('Estadística', 'Curso de estadística'),
('Contabilidad Básica', 'Curso de contabilidad básica'),
('Productividad y Desarrollo', 'Curso de productividad y desarrollo'),
('Ética', 'Curso de ética'),
('Música', 'Curso de música'),
('Dibujo', 'Curso de dibujo'),
('Redacción', 'Curso de redacción'),
('Lógica', 'Curso de lógica'),
('Investigación', 'Curso de investigación'), 
('Filosofía', 'Curso de filosofía'),
('Psicología', 'Curso de psicología'),
('Educación Ambiental', 'Curso de educación ambiental');

INSERT INTO carrera (codigo, nombre, estado) VALUES
('ELECTRICIDAD', 'Perito en Electricidad', TRUE);

INSERT INTO grado_carrera (carrera_id, grado_id) VALUES
('ELECTRICIDAD', 12),
('ELECTRICIDAD', 13),
('ELECTRICIDAD', 14);

INSERT INTO curriculo (id, curso_id, grado_id, carrera_id) VALUES
(1,  1, 11, NULL),
(2,  2, 11, NULL),
(3,  3, 12, 'ELECTRICIDAD'),
(5,  1,  3, NULL),
(6,  1,  5, NULL),
(7,  1,  7, NULL),
(8,  2,  4, NULL),
(9,  2,  6, NULL),
(10, 2,  8, NULL),
(11, 4,  1, NULL),
(12, 4,  2, NULL),
(13, 4,  3, NULL),
(14, 4,  4, NULL),
(15, 4,  5, NULL),
(16, 4,  6, NULL),
(17, 4,  7, NULL),
(18, 4,  8, NULL),
(19, 4,  9, NULL),
(20, 4, 10, NULL),
(21, 4, 11, NULL),
(22, 5,  3, NULL),
(23, 5,  4, NULL),
(24, 5,  5, NULL),
(25, 5,  6, NULL),
(26, 5,  7, NULL),
(27, 5,  8, NULL),
(28, 5,  9, NULL),
(29, 5, 10, NULL),
(30, 5, 11, NULL),
(31, 6,  3, NULL),
(32, 6,  4, NULL),
(33, 6,  5, NULL),
(34, 6,  6, NULL),
(35, 6,  7, NULL),
(36, 6,  8, NULL),
(37, 6,  9, NULL),
(38, 6, 10, NULL),
(39, 6, 11, NULL),
(40, 7,  3, NULL),
(41, 7,  4, NULL),
(42, 8,  1, NULL),
(43, 8,  2, NULL),
(44, 8,  3, NULL),
(45, 8,  4, NULL),
(46, 9,  1, NULL),
(47, 9,  2, NULL),
(48, 9,  3, NULL),
(49, 10, 1, NULL),
(50, 10, 2, NULL),
(51, 11, 1, NULL),
(52, 11, 2, NULL),
(53, 12, 3, NULL),
(54, 12, 5, NULL),
(55, 12, 6, NULL),
(56, 12, 7, NULL),
(57, 12, 8, NULL),
(58, 12, 9, NULL),
(59, 12, 10, NULL),
(60, 12, 11, NULL),
(61, 13, 4, NULL),
(62, 13, 7, NULL),
(63, 13, 9, NULL),
(64, 14, 6, NULL),
(65, 15, 5, NULL),
(66, 16, 5, NULL),
(67, 17, 6, NULL),
(68, 17, 10, NULL),
(69, 18, 9, NULL),
(70, 18, 10, NULL),
(71, 19, 8, NULL),
(72, 19, 11, NULL),
(73, 21, 7, NULL),
(74, 22, 9, NULL),
(75, 25, 11, NULL),
(76, 26, 10, NULL),
(77, 27, 8, NULL),
(78, 27, 11, NULL);

INSERT INTO libro (codigo_isbn, titulo, autor, editorial, cantidad_disponible)
VALUES
('978-376', 'Cien años de soledad', 'Gabriel García Márquez', 'Cátedra', 10),
('978-6638', 'Don Quijote de la Mancha', 'Miguel de Cervantes', 'Punto de Lectura', 8),
('978-975', 'El principito', 'Antoine de Saint-Exupéry', 'Salamandra', 15),
('978-206', 'La sombra del viento', 'Carlos Ruiz Zafón', 'Planeta', 6),
('978-0722', 'Harry Potter y la piedra filosofal', 'J. K. Rowling', 'Salamandra', 12),
('978-663', '1984', 'George Orwell', 'Debolsillo', 9),
('978-670', 'El alquimista', 'Paulo Coelho', 'Planeta', 7),
('978-9759', 'Orgullo y prejuicio', 'Jane Austen', 'Salamandra', 5),
('978-450', 'Los juegos del hambre', 'Suzanne Collins', 'Molino', 11),
('978-84', 'Crónica de una muerte anunciada', 'Gabriel García Márquez', 'Debolsillo', 4);
