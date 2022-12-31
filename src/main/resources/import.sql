/* Poblando la tabla clientes */
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Felipe', 'Jofre', 'test1@test.com', '2022-12-19', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Antonio', 'Quevedo', 'test2@test.com', '2022-12-19', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Romario', 'Quevedo', 'test2@test.com', '2022-12-19', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Andrea', 'Quevedo', 'test2@test.com', '2022-12-19', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Carlota', 'Quevedo', 'test2@test.com', '2022-12-19', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('John', 'Quevedo', 'test2@test.com', '2022-12-19', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Astrid', 'Quevedo', 'test2@test.com', '2022-12-19', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Gonzalo', 'Quevedo', 'test2@test.com', '2022-12-19', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Hugo', 'Quevedo', 'test2@test.com', '2022-12-19', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Fernando', 'Quevedo', 'test2@test.com', '2022-12-19', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Rosario', 'Quevedo', 'test2@test.com', '2022-12-19', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Almendra', 'Quevedo', 'test2@test.com', '2022-12-19', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Valeska', 'Quevedo', 'test2@test.com', '2022-12-19', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Luis', 'Quevedo', 'test2@test.com', '2022-12-19', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Alejandro', 'Quevedo', 'test2@test.com', '2022-12-19', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Rosa', 'Quevedo', 'test2@test.com', '2022-12-19', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('kike', 'Quevedo', 'test2@test.com', '2022-12-19', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Carlota', 'Quevedo', 'test2@test.com', '2022-12-19', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Romualdo', 'Quevedo', 'test2@test.com', '2022-12-19', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Inés', 'Quevedo', 'test2@test.com', '2022-12-19', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Sunilda', 'Quevedo', 'test2@test.com', '2022-12-19', '');

/* Poblando la tabla productos */
INSERT INTO productos (nombre, precio, create_at) VALUES ('Panasonic Pantalla LCD', 259990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES ('Impresora CANON', 159990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES ('Escritorio Económico Ergo', 218990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES ('Papel para impresión', 30500, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES ('Bicicleta Bianchi Aro 26', 359990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES ('iPhone 14 Pro 1TB', 259990, NOW());

/* Creando facturas */
INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES ('Factura equipos de oficina', null, 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (1, 1, 1);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (2, 1, 2);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (5, 1, 3);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (3, 1, 4);

INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES ('Factura Escritorio', 'Es para la oficina de reuniones', 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (3, 2, 3);

/* Poblando usuarios y roles */
INSERT INTO users (username, password, enabled) VALUES ('felipe', '$2a$10$36opVSgy4vKMPk1FFGf0wO6wM93cIXIgXJqkMprVqtQxuMbG8vqP6', 1);
INSERT INTO users (username, password, enabled) VALUES ('admin', '$2a$10$4soRHrwRuwUDSjj25tdoruMgQlqxqgaKCiCYrJozFliJX5u.ARZZi', 1);

INSERT INTO authorities (user_id, authority) VALUES (1, 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (2, 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (2, 'ROLE_ADMIN');