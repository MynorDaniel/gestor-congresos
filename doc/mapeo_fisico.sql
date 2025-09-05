-- Esquema

CREATE DATABASE gestor_congresos;

USE gestor_congresos;

CREATE TABLE usuario (
    id VARCHAR(30),
    nombre VARCHAR(200) NOT NULL,
    numero VARCHAR(30) NOT NULL,
    activado BOOL DEFAULT 1,
    foto LONGBLOB,
    correo VARCHAR(350) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE congreso (
    nombre VARCHAR(200),
    creador VARCHAR(30),
    precio DOUBLE NOT NULL,
    convocando BOOL DEFAULT 0,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    descripcion TEXT,
    activado BOOL DEFAULT 1,
    PRIMARY KEY (nombre),
    CONSTRAINT fk_usuario_congreso FOREIGN KEY (creador) REFERENCES usuario (id)
);

CREATE TABLE actividad (
    nombre VARCHAR(200),
    congreso VARCHAR(200),
    cupo INT NOT NULL,
    estado ENUM('APROBADA', 'PENDIENTE', 'RECHAZADA') NOT NULL,
    descripcion TEXT,
    tipo ENUM('PONENCIA', 'TALLER') NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    PRIMARY KEY (nombre, congreso),
    CONSTRAINT fk_congreso_actividad FOREIGN KEY (congreso) REFERENCES congreso (nombre)
);

CREATE TABLE cartera (
    usuario VARCHAR(30),
    saldo DOUBLE DEFAULT 0.00,
    PRIMARY KEY (usuario),
    CONSTRAINT fk_usuario_cartera FOREIGN KEY (usuario) REFERENCES usuario (id)
);

CREATE TABLE institucion (
    id INT AUTO_INCREMENT,
    nombre VARCHAR(200) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE afiliacion (
    usuario VARCHAR(30),
    institucion INT,
    PRIMARY KEY (usuario, institucion),
    CONSTRAINT fk_usuario_afiliacion FOREIGN KEY (usuario) REFERENCES usuario (id),
    CONSTRAINT fk_institucion_afiliacion FOREIGN KEY (institucion) REFERENCES institucion (id) ON DELETE CASCADE
);

CREATE TABLE inscripcion (
    usuario VARCHAR(30),
    congreso VARCHAR(200),
    PRIMARY KEY (usuario, congreso),
    CONSTRAINT fk_usuario_inscripcion FOREIGN KEY (usuario) REFERENCES usuario (id),
    CONSTRAINT fk_congreso_inscripcion FOREIGN KEY (congreso) REFERENCES congreso (nombre)
);

CREATE TABLE rol (
    valor ENUM('ASISTENTE', 'TALLERISTA', 'PONENTE', 'PONENTE_INVITADO') NOT NULL,
    PRIMARY KEY (valor)
);

CREATE TABLE participacion (
    usuario VARCHAR(30),
    congreso VARCHAR(200),
    rol ENUM('ASISTENTE', 'TALLERISTA', 'PONENTE', 'PONENTE_INVITADO') NOT NULL,
    PRIMARY KEY (usuario, congreso, rol),
    CONSTRAINT fk_usuario_participacion FOREIGN KEY (usuario) REFERENCES usuario (id),
    CONSTRAINT fk_congreso_participacion FOREIGN KEY (congreso) REFERENCES congreso (nombre),
    CONSTRAINT fk_rol_participacion FOREIGN KEY (rol) REFERENCES rol (valor)
);

CREATE TABLE pago (
    usuario VARCHAR(30),
    congreso VARCHAR(200),
    monto DOUBLE NOT NULL,
    fecha DATE NOT NULL,
    comision_cobrada DOUBLE NOT NULL,
    PRIMARY KEY (usuario, congreso),
    CONSTRAINT fk_inscripcion_pago FOREIGN KEY (usuario, congreso) REFERENCES inscripcion (usuario, congreso)
);

CREATE TABLE instalacion (
    id INT AUTO_INCREMENT,
    nombre VARCHAR(200) NOT NULL,
    ubicacion TEXT,
    PRIMARY KEY (id)
);

CREATE TABLE salon (
    nombre VARCHAR(200),
    instalacion INT,
    PRIMARY KEY (nombre, instalacion),
    CONSTRAINT fk_instalacion_salon FOREIGN KEY (instalacion) REFERENCES instalacion (id) ON DELETE CASCADE
);

CREATE TABLE reserva (
    usuario VARCHAR(30),
    actividad_nombre VARCHAR(200),
    actividad_congreso VARCHAR(200),
    PRIMARY KEY (usuario, actividad_nombre, actividad_congreso),
    CONSTRAINT fk_usuario_reserva FOREIGN KEY (usuario) REFERENCES usuario (id),
    CONSTRAINT fk_actividad_reserva FOREIGN KEY (actividad_nombre, actividad_congreso) REFERENCES actividad (nombre, congreso) ON DELETE CASCADE
);

CREATE TABLE asistencia (
    usuario VARCHAR(30),
    actividad_nombre VARCHAR(200),
    actividad_congreso VARCHAR(200),
    PRIMARY KEY (usuario, actividad_nombre, actividad_congreso),
    CONSTRAINT fk_usuario_asistencia FOREIGN KEY (usuario) REFERENCES usuario (id),
    CONSTRAINT fk_actividad_asistencia FOREIGN KEY (actividad_nombre, actividad_congreso) REFERENCES actividad (nombre, congreso) ON DELETE CASCADE
);

CREATE TABLE encargado_actividad (
    usuario VARCHAR(30),
    actividad_nombre VARCHAR(200),
    actividad_congreso VARCHAR(200),
    PRIMARY KEY (usuario, actividad_nombre, actividad_congreso),
    CONSTRAINT fk_usuario_encargado_actividad FOREIGN KEY (usuario) REFERENCES usuario (id),
    CONSTRAINT fk_actividad_encargado_actividad FOREIGN KEY (actividad_nombre, actividad_congreso) REFERENCES actividad (nombre, congreso) ON DELETE CASCADE
);

CREATE TABLE configuracion_pago (
    tipo ENUM('comision') NOT NULL,
    valor DOUBLE NOT NULL,
    PRIMARY KEY (tipo)
);

INSERT INTO usuario VALUES ('0', 'Sistema', '0', 1, null, '0@0.0');
INSERT INTO cartera (usuario) VALUES ('0');





