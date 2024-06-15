-- Creación de la tabla Estudiante con la columna DNI añadida
CREATE TABLE Estudiante (
    ID_Estudiante INT PRIMARY KEY,
	DNI VARCHAR(20),
    Nombre VARCHAR(50),
    Apellido VARCHAR(50),
    Fecha_Nacimiento DATE,
    Dirección VARCHAR(100),
    Email VARCHAR(100)
);


-- Creación de la tabla Curso con restricción de unicidad en el nombre
CREATE TABLE Curso (
    ID_Curso INT PRIMARY KEY,
    Nombre VARCHAR(100) UNIQUE,
    Descripción TEXT,
    Créditos INT,
    Duración INT -- en semanas
);


-- Creación de la tabla Profesor
CREATE TABLE Profesor (
    ID_Profesor INT PRIMARY KEY,
    DNI VARCHAR(20),
    Nombre VARCHAR(50),
    Apellido VARCHAR(50),
    Email VARCHAR(100),
    Teléfono VARCHAR(15)
);

-- Creación de la tabla Departamento
CREATE TABLE Departamento (
    ID_Departamento INT PRIMARY KEY,
    Nombre VARCHAR(50) UNIQUE,
    Ubicación VARCHAR(100)
);

-- Creación de la tabla intermedia Estudiante_Curso
CREATE TABLE Estudiante_Curso (
    ID_Estudiante INT,
    ID_Curso INT,
    Fecha_Inscripción DATE,
    PRIMARY KEY (ID_Estudiante, ID_Curso),
    FOREIGN KEY (ID_Estudiante) REFERENCES Estudiante(ID_Estudiante),
    FOREIGN KEY (ID_Curso) REFERENCES Curso(ID_Curso)
);

-- Creación de la tabla intermedia Curso_Profesor
CREATE TABLE Curso_Profesor (
    ID_Curso INT,
    ID_Profesor INT,
    Fecha_Asignación DATE,
    PRIMARY KEY (ID_Curso, ID_Profesor),
    FOREIGN KEY (ID_Curso) REFERENCES Curso(ID_Curso),
    FOREIGN KEY (ID_Profesor) REFERENCES Profesor(ID_Profesor)
);

-- Creación de la tabla intermedia Profesor_Departamento
CREATE TABLE Profesor_Departamento (
    ID_Profesor INT,
    ID_Departamento INT,
    PRIMARY KEY (ID_Profesor, ID_Departamento),
    FOREIGN KEY (ID_Profesor) REFERENCES Profesor(ID_Profesor),
    FOREIGN KEY (ID_Departamento) REFERENCES Departamento(ID_Departamento)
);

-- Creación de la tabla intermedia Departamento_Curso
CREATE TABLE Departamento_Curso (
    ID_Departamento INT,
    ID_Curso INT,
    PRIMARY KEY (ID_Departamento, ID_Curso),
    FOREIGN KEY (ID_Departamento) REFERENCES Departamento(ID_Departamento),
    FOREIGN KEY (ID_Curso) REFERENCES Curso(ID_Curso)
);

-- Creación de la tabla Clase
CREATE TABLE Clase (
    ID_Clase INT PRIMARY KEY AUTO_INCREMENT,
    Nombre VARCHAR(100),
    Aula VARCHAR(50) UNIQUE,
    Horario VARCHAR(50)
);

-- Creación de la tabla intermedia Clase_Profesor_Curso
CREATE TABLE Clase_Profesor_Curso (
    ID_Clase INT,
    ID_Profesor INT,
    ID_Curso INT,
    PRIMARY KEY (ID_Clase, ID_Profesor, ID_Curso),
    FOREIGN KEY (ID_Clase) REFERENCES Clase(ID_Clase),
    FOREIGN KEY (ID_Profesor) REFERENCES Profesor(ID_Profesor),
    FOREIGN KEY (ID_Curso) REFERENCES Curso(ID_Curso)
);

DELIMITER //

CREATE PROCEDURE consultar_datos(
    IN estudiante_id INT,
    IN curso_id INT,
    IN profesor_id INT,
    IN clase_id INT
)
BEGIN
    DECLARE nombreEstudiante VARCHAR(50);
    DECLARE nombreCurso VARCHAR(100);
    DECLARE nombreProfesor VARCHAR(50);
    DECLARE aulaClase VARCHAR(50);

    -- Seleccionar el nombre de un estudiante
    SELECT Nombre INTO nombreEstudiante
    FROM Estudiante
    WHERE ID_Estudiante = estudiante_id;

    -- Seleccionar el nombre de un curso
    SELECT Nombre INTO nombreCurso
    FROM Curso
    WHERE ID_Curso = curso_id;

    -- Seleccionar el nombre de un profesor
    SELECT Nombre INTO nombreProfesor
    FROM Profesor
    WHERE ID_Profesor = profesor_id;

    -- Seleccionar el nombre del aula de una clase
    SELECT Aula INTO aulaClase
    FROM Clase
    WHERE ID_Clase = clase_id;

    -- Mostrar los resultados
    SELECT nombreEstudiante, nombreCurso, nombreProfesor, aulaClase;
END //

DELIMITER ;
