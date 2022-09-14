-----------------------------------Creación de tablas---------------------------------

CREATE TABLE Usuario (
    id INTEGER PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    contrasena VARCHAR(100) NOT NULL,
    telefono VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL,
    tipo_usuario VARCHAR(100) NOT NULL
);

CREATE TABLE Pedido (
    id INTEGER PRIMARY KEY,
    fecha_reserva VARCHAR(100) NOT NULL,
    hora_reserva VARCHAR(100) NOT NULL,
    estado_pedido VARCHAR(100) NOT NULL,
    precio_total INTEGER NOT NULL,
    id_usuario INTEGER NOT NULL,
    CONSTRAINT Pedido_id_usuario_PK FOREIGN KEY(id_usuario) REFERENCES Usuario(id)
);

CREATE TABLE OpcionEntrada (
    id INTEGER PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(100) NOT NULL,
    imagen VARCHAR(100) NOT NULL,
    precio INTEGER NOT NULL
);

CREATE TABLE OpcionPlatoFuerte (
    id INTEGER PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(100) NOT NULL,
    imagen VARCHAR(100) NOT NULL,
    precio INTEGER NOT NULL
);

CREATE TABLE OpcionPostre (
    id INTEGER PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(100) NOT NULL,
    imagen VARCHAR(100) NOT NULL,
    precio INTEGER NOT NULL
);

CREATE TABLE OpcionBebida (
    id INTEGER PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(100) NOT NULL,
    imagen VARCHAR(100) NOT NULL,
    precio INTEGER NOT NULL
);

CREATE TABLE SeleccionEntrada(
    id_pedido INTEGER,
    id_entrada INTEGER,
    CONSTRAINT SeleccionEntrada_PK PRIMARY KEY(id_pedido, id_entrada),
    CONSTRAINT SeleccionEntrada_Pedido_FK FOREIGN KEY (id_pedido) REFERENCES Pedido(id),
    CONSTRAINT SeleccionEntrada_Entrada_FK FOREIGN KEY (id_entrada) REFERENCES OpcionEntrada(id)
);

CREATE TABLE SeleccionPlatoFuerte(
    id_pedido INTEGER,
    id_plato_fuerte INTEGER,
    CONSTRAINT SeleccionPlatoFuerte_PK PRIMARY KEY(id_pedido, id_plato_fuerte),
    CONSTRAINT SeleccionPlatoFuerte_Pedido_FK FOREIGN KEY (id_pedido) REFERENCES Pedido(id),
    CONSTRAINT SeleccionPlatoFuerte_PlatoFuerte_FK FOREIGN KEY (id_plato_fuerte) REFERENCES OpcionPlatoFuerte(id)
);

CREATE TABLE SeleccionPostre(
    id_pedido INTEGER,
    id_postre INTEGER,
    CONSTRAINT SeleccionPostre_PK PRIMARY KEY(id_pedido, id_postre),
    CONSTRAINT SeleccionPostre_Pedido_FK FOREIGN KEY (id_pedido) REFERENCES Pedido(id),
    CONSTRAINT SeleccionPostre_Postre_FK FOREIGN KEY (id_postre) REFERENCES OpcionPostre(id)
);

CREATE TABLE SeleccionBebida(
    id_pedido INTEGER,
    id_bebida INTEGER,
    CONSTRAINT SeleccionBebida_PK PRIMARY KEY(id_pedido, id_bebida),
    CONSTRAINT SeleccionBebida_Pedido_FK FOREIGN KEY (id_pedido) REFERENCES Pedido(id),
    CONSTRAINT SeleccionBebida_Bebida_FK FOREIGN KEY (id_bebida) REFERENCES OpcionBebida(id)
);

-----------------------------------Insertar información a las tablas---------------------------------

INSERT INTO OpcionEntrada (id, nombre, descripcion, imagen, precio)
    VALUES(1, 'Pan de higos', '2 porciones de pan artesanal recubierto con higos, queso mozzarella y queso crema.', '/images/entrada1.jpg', 12000);

