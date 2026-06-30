-- ============================================================
-- BASE DE DATOS DEL SISTEMA PBA
-- Los registros incluidos son DATOS DE EJEMPLO.
-- Reemplazarlos por datos reales autorizados por la empresa.
-- ============================================================

CREATE DATABASE IF NOT EXISTS pba_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE pba_db;

CREATE TABLE IF NOT EXISTS categorias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(60) NOT NULL UNIQUE,
    descripcion VARCHAR(250)
);

CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    usuario VARCHAR(40) NOT NULL UNIQUE,
    contrasena VARCHAR(100) NOT NULL,
    rol VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS proveedores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    ruc VARCHAR(11) NOT NULL UNIQUE,
    telefono VARCHAR(20) NOT NULL,
    tipoInsumoQueProvee VARCHAR(80) NOT NULL
);

CREATE TABLE IF NOT EXISTS productos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(500),
    categoria_id BIGINT NOT NULL,
    unidadMedida VARCHAR(30) NOT NULL,
    precioUnitario DECIMAL(10,2) NOT NULL,
    stockActual INT NOT NULL DEFAULT 0,
    stockMinimo INT NOT NULL DEFAULT 0,
    cantidadVendida INT NOT NULL DEFAULT 0,
    CONSTRAINT fk_producto_categoria
        FOREIGN KEY (categoria_id) REFERENCES categorias(id)
);

CREATE TABLE IF NOT EXISTS insumos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    stockActual INT NOT NULL DEFAULT 0,
    stockMinimo INT NOT NULL DEFAULT 0,
    proveedor_id BIGINT NOT NULL,
    CONSTRAINT fk_insumo_proveedor
        FOREIGN KEY (proveedor_id) REFERENCES proveedores(id)
);

CREATE TABLE IF NOT EXISTS clientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    razonSocial VARCHAR(120) NOT NULL,
    tipo VARCHAR(25) NOT NULL,
    region VARCHAR(60) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    correo VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS contactos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    cargo VARCHAR(60) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    correo VARCHAR(100),
    cliente_id BIGINT NOT NULL,
    CONSTRAINT fk_contacto_cliente
        FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);

CREATE TABLE IF NOT EXISTS pedidos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    cliente_id BIGINT NOT NULL,
    canalVenta VARCHAR(20) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    total DECIMAL(12,2) NOT NULL DEFAULT 0,
    CONSTRAINT fk_pedido_cliente
        FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);

CREATE TABLE IF NOT EXISTS detalles_pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pedido_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    cantidad INT NOT NULL,
    precioUnitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(12,2) NOT NULL,
    CONSTRAINT fk_detalle_pedido
        FOREIGN KEY (pedido_id) REFERENCES pedidos(id),
    CONSTRAINT fk_detalle_producto
        FOREIGN KEY (producto_id) REFERENCES productos(id)
);

CREATE TABLE IF NOT EXISTS postventas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    cliente_id BIGINT NOT NULL,
    tipo VARCHAR(30) NOT NULL,
    descripcion VARCHAR(1000) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    fechaResolucion DATE,
    CONSTRAINT fk_postventa_cliente
        FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);

-- DATOS DE EJEMPLO: categorías
INSERT IGNORE INTO categorias (id, nombre, descripcion) VALUES
(1, 'Bioinoculante', 'Productos con microorganismos benéficos'),
(2, 'Bioinsecticida', 'Productos para control biológico de insectos'),
(3, 'Abono foliar', 'Nutrientes aplicados sobre las hojas'),
(4, 'Sustrato sólido', 'Sustratos para crecimiento y producción agrícola');

-- DATOS DE EJEMPLO: productos. Reemplazar por el catálogo real de PBA.
INSERT INTO productos
    (nombre, descripcion, categoria_id, unidadMedida, precioUnitario, stockActual, stockMinimo, cantidadVendida)
SELECT 'Bio Raíz', 'Inoculante para favorecer el desarrollo de raíces', 1, 'Frasco 1 L', 42.00, 35, 10, 0
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE nombre = 'Bio Raíz');

INSERT INTO productos
    (nombre, descripcion, categoria_id, unidadMedida, precioUnitario, stockActual, stockMinimo, cantidadVendida)
SELECT 'Micorriza PBA', 'Bioinoculante de aplicación al suelo', 1, 'Bolsa 1 kg', 38.50, 28, 8, 0
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE nombre = 'Micorriza PBA');

INSERT INTO productos
    (nombre, descripcion, categoria_id, unidadMedida, precioUnitario, stockActual, stockMinimo, cantidadVendida)
SELECT 'Bio Control', 'Bioinsecticida de uso agrícola', 2, 'Frasco 1 L', 55.00, 22, 7, 0
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE nombre = 'Bio Control');

INSERT INTO productos
    (nombre, descripcion, categoria_id, unidadMedida, precioUnitario, stockActual, stockMinimo, cantidadVendida)
SELECT 'Tricho PBA', 'Producto biológico a base de Trichoderma', 2, 'Bolsa 500 g', 47.00, 18, 6, 0
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE nombre = 'Tricho PBA');

INSERT INTO productos
    (nombre, descripcion, categoria_id, unidadMedida, precioUnitario, stockActual, stockMinimo, cantidadVendida)
SELECT 'Foliar Crecimiento', 'Abono foliar para etapa de crecimiento', 3, 'Frasco 1 L', 31.90, 40, 12, 0
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE nombre = 'Foliar Crecimiento');

INSERT INTO productos
    (nombre, descripcion, categoria_id, unidadMedida, precioUnitario, stockActual, stockMinimo, cantidadVendida)
SELECT 'Foliar Floración', 'Abono foliar para floración y fructificación', 3, 'Frasco 1 L', 34.90, 32, 10, 0
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE nombre = 'Foliar Floración');

INSERT INTO productos
    (nombre, descripcion, categoria_id, unidadMedida, precioUnitario, stockActual, stockMinimo, cantidadVendida)
SELECT 'Sustrato Vivero', 'Mezcla sólida para producción de plantones', 4, 'Saco 25 kg', 29.00, 50, 15, 0
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE nombre = 'Sustrato Vivero');

INSERT INTO productos
    (nombre, descripcion, categoria_id, unidadMedida, precioUnitario, stockActual, stockMinimo, cantidadVendida)
SELECT 'Compost Mejorado', 'Sustrato orgánico para cultivos', 4, 'Saco 40 kg', 36.00, 45, 12, 0
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE nombre = 'Compost Mejorado');

-- DATOS DE EJEMPLO: proveedores. Reemplazar por proveedores reales.
INSERT INTO proveedores (nombre, ruc, telefono, tipoInsumoQueProvee)
SELECT 'Biolab Amazonas SAC', '20123456789', '987654321', 'Cepas y microorganismos'
WHERE NOT EXISTS (SELECT 1 FROM proveedores WHERE ruc = '20123456789');

INSERT INTO proveedores (nombre, ruc, telefono, tipoInsumoQueProvee)
SELECT 'Envases del Norte EIRL', '20456789123', '976543210', 'Envases y etiquetas'
WHERE NOT EXISTS (SELECT 1 FROM proveedores WHERE ruc = '20456789123');

INSERT INTO proveedores (nombre, ruc, telefono, tipoInsumoQueProvee)
SELECT 'Nutrientes Andinos SAC', '20678912345', '965432109', 'Nutrientes y minerales'
WHERE NOT EXISTS (SELECT 1 FROM proveedores WHERE ruc = '20678912345');

-- DATOS DE EJEMPLO: clientes. Reemplazar por clientes reales autorizados.
INSERT INTO clientes (razonSocial, tipo, region, telefono, correo)
SELECT 'Asociación Agraria Utcubamba', 'AGRICULTOR', 'Amazonas', '954111222', 'contacto@utcubamba-ejemplo.pe'
WHERE NOT EXISTS (SELECT 1 FROM clientes WHERE razonSocial = 'Asociación Agraria Utcubamba');

INSERT INTO clientes (razonSocial, tipo, region, telefono, correo)
SELECT 'Agrodistribuciones Norte SAC', 'DISTRIBUIDOR', 'Lambayeque', '954333444', 'ventas@agronorte-ejemplo.pe'
WHERE NOT EXISTS (SELECT 1 FROM clientes WHERE razonSocial = 'Agrodistribuciones Norte SAC');

INSERT INTO clientes (razonSocial, tipo, region, telefono, correo)
SELECT 'Tienda Agrícola El Campo', 'TIENDA_AGRICOLA', 'Cajamarca', '954555666', 'pedidos@elcampo-ejemplo.pe'
WHERE NOT EXISTS (SELECT 1 FROM clientes WHERE razonSocial = 'Tienda Agrícola El Campo');
