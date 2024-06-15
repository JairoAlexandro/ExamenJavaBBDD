
-- Insertar datos en la tabla Estudiante
INSERT INTO Estudiante (ID_Estudiante, DNI, Nombre, Apellido, Fecha_Nacimiento, Dirección, Email) VALUES
(1, '12345678A', 'Juan', 'Pérez', '2000-01-01', 'Calle Falsa 123', 'juan.perez@example.com'),
(2, '87654321B', 'María', 'Gómez', '2001-02-02', 'Avenida Siempre Viva 742', 'maria.gomez@example.com'),
(3, '23456789C', 'Luis', 'Rodríguez', '1999-03-03', 'Calle del Sol 45', 'luis.rodriguez@example.com'),
(4, '34567890D', 'Ana', 'Martín', '2002-04-04', 'Calle Luna 56', 'ana.martin@example.com'),
(5, '45678901E', 'José', 'Hernández', '1998-05-05', 'Avenida Estrella 67', 'jose.hernandez@example.com'),
(6, '56789012F', 'Lucía', 'Fernández', '2000-06-06', 'Plaza Mayor 78', 'lucia.fernandez@example.com'),
(7, '67890123G', 'Carlos', 'García', '1997-07-07', 'Calle Rosa 89', 'carlos.garcia@example.com'),
(8, '78901234H', 'Sofía', 'Martínez', '2001-08-08', 'Calle Jazmín 90', 'sofia.martinez@example.com'),
(9, '89012345I', 'Miguel', 'López', '1996-09-09', 'Calle Violeta 101', 'miguel.lopez@example.com'),
(10, '90123456J', 'Elena', 'Sánchez', '2003-10-10', 'Avenida Tulipán 112', 'elena.sanchez@example.com');

-- Insertar datos en la tabla Curso
INSERT INTO Curso (ID_Curso, Nombre, Descripción, Créditos, Duración) VALUES
(1, 'Matemáticas', 'Curso de Matemáticas Básicas', 5, 12),
(2, 'Programación', 'Introducción a la Programación', 6, 14),
(3, 'Historia', 'Historia Universal', 4, 10);

-- Insertar datos en la tabla Profesor
INSERT INTO Profesor (ID_Profesor, DNI, Nombre, Apellido, Email, Teléfono) VALUES
(1, '11223344C', 'Carlos', 'López', 'carlos.lopez@example.com', '123456789'),
(2, '22334455D', 'Ana', 'Martínez', 'ana.martinez@example.com', '987654321'),
(3, '33445566E', 'Pedro', 'González', 'pedro.gonzalez@example.com', '456789012');

-- Insertar datos en la tabla Departamento
INSERT INTO Departamento (ID_Departamento, Nombre, Ubicación) VALUES
(1, 'Ciencias', 'Edificio A'),
(2, 'Ingeniería', 'Edificio B');

-- Insertar datos en la tabla intermedia Estudiante_Curso
INSERT INTO Estudiante_Curso (ID_Estudiante, ID_Curso, Fecha_Inscripción) VALUES
(1, 1, '2023-09-01'),
(2, 1, '2023-09-02'),
(3, 1, '2023-09-03'),
(4, 2, '2023-09-04'),
(5, 2, '2023-09-05'),
(6, 2, '2023-09-06'),
(7, 3, '2023-09-07'),
(8, 3, '2023-09-08'),
(9, 3, '2023-09-09'),
(10, 1, '2023-09-10'),
(1, 2, '2023-09-11'),
(2, 3, '2023-09-12'),
(3, 2, '2023-09-13'),
(4, 3, '2023-09-14'),
(5, 1, '2023-09-15'),
(6, 3, '2023-09-16'),
(7, 1, '2023-09-17'),
(8, 2, '2023-09-18'),
(9, 1, '2023-09-19'),
(10, 2, '2023-09-20');

-- Insertar datos en la tabla intermedia Curso_Profesor
INSERT INTO Curso_Profesor (ID_Curso, ID_Profesor, Fecha_Asignación) VALUES
(1, 1, '2023-09-01'),
(2, 2, '2023-09-02'),
(3, 3, '2023-09-03'),
(1, 2, '2023-09-11'),
(2, 3, '2023-09-12');

-- Insertar datos en la tabla intermedia Profesor_Departamento
INSERT INTO Profesor_Departamento (ID_Profesor, ID_Departamento) VALUES
(1, 1),
(2, 2),
(3, 1);

-- Insertar datos en la tabla intermedia Departamento_Curso
INSERT INTO Departamento_Curso (ID_Departamento, ID_Curso) VALUES
(1, 1),
(2, 2),
(1, 3);

-- Insertar datos en la tabla Clase
INSERT INTO Clase (Nombre, Aula, Horario) VALUES
('Clase de Álgebra', 'Aula 101', 'Lunes 10:00-12:00'),
('Clase de Programación', 'Aula 102', 'Miércoles 14:00-16:00'),
('Clase de Historia', 'Aula 103', 'Martes 10:00-12:00');

-- Insertar datos en la tabla intermedia Clase_Profesor_Curso
INSERT INTO Clase_Profesor_Curso (ID_Clase, ID_Profesor, ID_Curso) VALUES
(1, 1, 1),
(2, 2, 2),
(3, 3, 3),
(1, 2, 1),
(2, 3, 2);
