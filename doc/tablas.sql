

CREATE TABLE Pedido (
    id INTEGER PRIMARY KEY,
    cliente VARCHAR(100) NOT NULL,
    estado VARCHAR(100) NOT NULL,
);

CREATE TABLE OpcionEntrada (
    id INTEGER PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio INTEGER NOT NULL,
    CONSTRAINT OpcionEntrada_nombre_UQ UNIQUE (nombre)
);

CREATE TABLE OpcionPlatoFuerte (
    id INTEGER PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio INTEGER NOT NULL,
    CONSTRAINT OpcionPlatoFuerte_nombre_UQ UNIQUE (nombre)
);

CREATE TABLE OpcionPostre (
    id INTEGER PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio INTEGER NOT NULL,
    CONSTRAINT OpcionPostre_nombre_UQ UNIQUE (nombre)
);

CREATE TABLE OpcionBebida (
    id INTEGER PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio INTEGER NOT NULL,
    CONSTRAINT OpcionBebida_nombre_UQ UNIQUE (nombre)
);

CREATE TABLE Menu(
    id_pedido INTEGER PRIMARY KEY,
    precio_total INTEGER, 
    id_entrada INTEGER,
    id_plato_fuerte INTEGER,
    id_postre INTEGER,
    id_bebida INTEGER,
    CONSTRAINT Menu_id_pedido_PK FOREIGN KEY(id_pedido) REFERENCES Pedido(id),
    CONSTRAINT Menu_id_entrada_pedido_PK FOREIGN KEY(id_entrada) REFERENCES OpcionEntrada(id),
    CONSTRAINT Menu_id_plato_fuerte_pedido_PK FOREIGN KEY(id_plato_fuerte) REFERENCES OpcionPlatoFuerte(id),
    CONSTRAINT Menu_id_postre_pedido_PK FOREIGN KEY(id_postre) REFERENCES OpcionPostre(id),
    CONSTRAINT Menu_id_bebida_pedido_PK FOREIGN KEY(id_bebida) REFERENCES OpcionBebida(id),
);
