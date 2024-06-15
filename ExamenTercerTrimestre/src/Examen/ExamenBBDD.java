package Examen;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamenBBDD {
    public static Connection getConnection() throws SQLException {
	String url = "jdbc:mysql://localhost/examen";
	String user = "root";
	String password = "";
	return DriverManager.getConnection(url, user, password);
    }
    //Primer ejercicio
    public Map<Integer, String> obtenerEstudiantesPorID(List<Integer> estudiantesIDs){
	Map<Integer, String> res = new HashMap<Integer, String>();
	try(Connection cnx = getConnection();
		PreparedStatement psSeleEstu = cnx.prepareStatement("Select * from estudiante where ID_Estudiante = ?");) {
	    for(Integer ids: estudiantesIDs) {
		psSeleEstu.setInt(1, ids);
		psSeleEstu.addBatch();
	    }
	    psSeleEstu.executeBatch();
	    ResultSet rs = psSeleEstu.getResultSet();
	    while(rs.next()) {
		res.put(rs.getInt("ID_Estudiante"), rs.getString("Nombre"));
	    }
	} catch (SQLException e) {
	    
	    e.printStackTrace();
	}
	return res;
    }
    //Segundo ejercicio
    @SuppressWarnings("unchecked")
    public boolean actualizarEmailEstudiantes(Map<Integer, String> estudiantes) {
	boolean res = false;
	try(Connection cnx = getConnection();
		    PreparedStatement psUpdate = cnx.prepareStatement("Update estudiante SET Email = ? where ID_Estudiante = ?");) {
	    List<Integer> ids = (List<Integer>) estudiantes.keySet();
	    for(Integer id: ids) {
		psUpdate.setString(1, estudiantes.get(id));
		psUpdate.setInt(2, id);
		psUpdate.addBatch();
	    }
	    psUpdate.executeBatch();
	    res = true;
	} catch (SQLException e) {
	    System.out.println("Error actualizando email");
	    e.printStackTrace();
	}
	return res;
    }
    //Tercer ejercicio
    
    public List<Clase> obtenerClaseDeCursoPorProfesor(int idProfesor, String nombreCurso){
	List<Clase> clase = new ArrayList<Clase>();
	Clase clases = null;
	try(Connection cnx = getConnection();
		PreparedStatement psIDCurso = cnx.prepareStatement("Select * from clase_profesor_curso where ID_Profesor = ? and ID_Curso = ?");
		PreparedStatement psNombreCurso = cnx.prepareStatement("Select * from Clase where ID_Clase = ?");
		PreparedStatement psCursoNombre = cnx.prepareStatement("Select * from curso where Nombre = ?")){
	    ResultSet rsNombreId = psCursoNombre.executeQuery();
	    int idCurso = 0;
	    if(rsNombreId.next()) {
		idCurso = rsNombreId.getInt("ID_Clase");
	    }
	    psIDCurso.setInt(1, idProfesor);
	    psIDCurso.setInt(2, idCurso);
	    ResultSet rs = psIDCurso.executeQuery();
	    int idClase = 0;
	    if(rs.next()) {
		idClase = rs.getInt("ID_Clase");
	    }
	    psNombreCurso.setInt(1, idClase);
	    ResultSet rs2 = psNombreCurso.executeQuery();
	    while(rs2.next()) {
		clases = new Clase(rs2.getInt("ID_Clase"), rs2.getString("Nombre"));
		clase.add(clases);
	    }
	    
	} catch (SQLException e) {
	    
	    e.printStackTrace();
	}
	return clase;
    }
    //Cuarto ejercicio
    //Método para obtener los estudiantes que reciben clases de un profesor para un curso concreto
    public List<Estudiante> obtenerEstudiantesPorProfesorYCurso(int idProfesor, int idCurso){
	List<Estudiante> res = new ArrayList<Estudiante>();
	Estudiante estudiante = null;
	try(Connection cnx = getConnection();
		PreparedStatement psComprobar = cnx.prepareStatement("Select * from Curso_Profesor where ID_Profesor = ?");
		PreparedStatement psObtIDEstu = cnx.prepareStatement("Select * from Estudiante_Curso where ID_Curso = ?");
		PreparedStatement psEstudiantes = cnx.prepareStatement("Select * from Estudiante where ID_Estudiante = ?");) {
	    psComprobar.setInt(1, idProfesor);
	    ResultSet rs = psComprobar.executeQuery();
	    int idCompueba = 0;
	    if(rs.next()) {
		idCompueba = rs.getInt("ID_Curso");
		if(idCurso != idCompueba) {
		    System.out.println("El profesor no da ese curso");
		    return res;
		}
	    }
	    psObtIDEstu.setInt(1, idCurso);
	    ResultSet rs2 = psObtIDEstu.executeQuery();
	    int idEstudiante = 0;
	    if(rs2.next()) {
		idEstudiante = rs2.getInt("ID_Estudiante");
	    }
	    psEstudiantes.setInt(1, idEstudiante);
	    ResultSet rs3 = psEstudiantes.executeQuery();
	    while(rs3.next()) {
		estudiante = new Estudiante(rs3.getInt("ID_Estudiante"), rs3.getString("Nombre"));
		res.add(estudiante);
	    }
	} catch (SQLException e) {

	    e.printStackTrace();
	}
	return res;
    }
    //Quinto ejercicio
    //Método que intercambia dos profesores de curso, por ejemplo, Carlos pasa de dar el curso de 
    //matemáticas básicas a dar el de historia universal y Pedro pasa de dar historia universal a dar
    //el de matemáticas básicas
    public void intercambiarProfesoresDeCurso(int idProfesor1, int idProfesor2, int cursoProfesor1, int cursoProfesor2) {
	boolean salir1 = true;
	boolean salir2 = true;
	boolean salir3 = true;
	boolean salir4 = true;
	try(Connection cnx = getConnection();) {
	    
	    if(cursoProfesor1 == cursoProfesor2 || idProfesor1 == idProfesor2) {
		return;
	    }
	    PreparedStatement psComprobarEstan = cnx.prepareStatement("Select * from Curso_Profesor");
	    ResultSet rs1 = psComprobarEstan.executeQuery();
	    while(rs1.next()) {
		if(rs1.getInt("ID_Curso") == cursoProfesor1) {
		    salir1 = false;
		}else if(rs1.getInt("ID_Curso") == cursoProfesor2) {
		    salir2 = false;
		}else if(rs1.getInt("ID_Profresor") == idProfesor1) {
		    salir3 = false;
		}else if(rs1.getInt("ID_Profresor") == idProfesor2) {
		    salir4 = false;
		}
	    }
	    if(salir1 == true || salir2 == true || salir3 == true || salir4 == true ) {
		return;
	    }
	    salir1 = true;
	    salir2 = true;
	    salir3 = true;
	    salir4 = true;
	    
	    PreparedStatement psPrimerCambio = cnx.prepareStatement("Update Curso_Profesor Set ID_Profesor = ? where ID_Curso = ?");
	    psPrimerCambio.setInt(1, idProfesor1);
	    psPrimerCambio.setInt(2, cursoProfesor2);
	    psPrimerCambio.executeUpdate();
	    psPrimerCambio.close();
	    
	    PreparedStatement psSegundoCambio = cnx.prepareStatement("Update Curso_Profesor Set ID_Profesor = ? where ID_Curso = ?");
	    psSegundoCambio.setInt(1, idProfesor2);
	    psSegundoCambio.setInt(2, cursoProfesor1);
	    psSegundoCambio.executeUpdate();
	    psSegundoCambio.close();
	    
	    PreparedStatement psComprobarEstan2 = cnx.prepareStatement("Select * from Clase_Profesor_Curso");
	    ResultSet rs2 = psComprobarEstan2.executeQuery();
	    while(rs2.next()) {
		if(rs2.getInt("ID_Curso") == cursoProfesor1) {
		    salir1 = false;
		}else if(rs1.getInt("ID_Curso") == cursoProfesor2) {
		    salir2 = false;
		}else if(rs1.getInt("ID_Profresor") == idProfesor1) {
		    salir3 = false;
		}else if(rs1.getInt("ID_Profresor") == idProfesor2) {
		    salir4 = false;
		}
	    }
	    if(salir1 == true || salir2 == true || salir3 == true || salir4 == true ) {
		return;
	    }
	    PreparedStatement psTercerCambio = cnx.prepareStatement("Update Clase_Profesor_Curso Set ID_Profesor = ? where ID_Curso = ?");
	    psTercerCambio.setInt(1, idProfesor1);
	    psTercerCambio.setInt(2, cursoProfesor2);
	    psTercerCambio.executeUpdate();
	    psTercerCambio.close();
	    
	    PreparedStatement psCuartoCambio = cnx.prepareStatement("Update Clase_Profesor_Curso Set ID_Profesor = ? where ID_Curso = ?");
	    psCuartoCambio.setInt(1, idProfesor2);
	    psCuartoCambio.setInt(2, cursoProfesor1);
	    psCuartoCambio.executeUpdate();
	    psCuartoCambio.close();
	   
	} catch (SQLException e) {
	    
	    e.printStackTrace();
	}
    }
    
    //Sexto ejercicio
    //Método que muestra por consola los valores que selecciona el procedimiento consultar_datos almacenado en la BBDD
    public static void llamarProcedimiento(int estudianteId, int cursoId, int profesorId, int claseId) {
	try(Connection cnx = getConnection();
		CallableStatement cll = cnx.prepareCall("{call consultar_datos(?,?,?,?,?,?,?,?)}")) {
	    cll.setInt(1, estudianteId);
	    cll.setInt(2, cursoId);
	    cll.setInt(3, profesorId);
	    cll.setInt(4, claseId);
	    cll.registerOutParameter(5, Types.VARCHAR);
	    cll.registerOutParameter(6, Types.VARCHAR);
	    cll.registerOutParameter(7, Types.VARCHAR);
	    cll.registerOutParameter(8, Types.VARCHAR);
	    cll.execute();
	    
	    //Como devuelve un Select, tendriamos que hacer un un prepareStatement, y ya coger el dato, pasarlo
	    //a String y hacer los syso para que te muestre por pantalla el resultado.
	    String nombreEstudiante;
	    String nombreCurso;
	    String nombreProfesor;
	    String nombreClase;
	    
	    
	    
	} catch (SQLException e) {
	    
	    e.printStackTrace();
	}
    }
    
    //Septimo ejercicio
    //Método que busca un número “n“ de clases aleatorias y devuelve la lista de 
    //estudiantes que reciben cursos en dichas clases (no deben repetirse estudiantes)
//    public List<Estudiante> buscarEstudiantesEnClasesAleatorias(int n){
//	List<Estudiante> estudiante = new ArrayList<Estudiante>();
//	
//    } 
    
    //Octabo ejercicio
    //Me falta mas comprobaciones
    public void dividirCurso(int idCurso, int n) {
	try {
	    Connection cnx = getConnection();
	    PreparedStatement psNumeroAlumnosCurso = cnx.prepareStatement("Select * from Estudiante_Curso where ID_Curso = ?");
	    psNumeroAlumnosCurso.setInt(1, idCurso);
	    ResultSet rsNumeroAlumnos = psNumeroAlumnosCurso.executeQuery();
	    int numeroAlumnos = rsNumeroAlumnos.getRow();
	    
	    
	    
	    PreparedStatement psProfesorCurso = cnx.prepareStatement("Select * from Curso_Profesor");
	    ResultSet rs1 = psProfesorCurso.executeQuery();
	    List<Integer> idProfesCodigo = new ArrayList<Integer>();
	    while(rs1.next()) {
		idProfesCodigo.add(rs1.getInt("ID_Profesor"));
	    }
	    PreparedStatement psDespartamentoProfe = cnx.prepareStatement("Select * from Profesor_Departamento");
	    ResultSet rs3 = psDespartamentoProfe.executeQuery();
	    List<Integer> idProfesDepart = new ArrayList<Integer>();
	    while(rs3.next()) {
		idProfesDepart.add(rs3.getInt("ID_Profesor"));
	    }
	    
	    PreparedStatement psProfesores = cnx.prepareStatement("Select * from Profesor");
	    ResultSet rs2 = psProfesores.executeQuery();
	    List<Integer> idProfesSinCodigo = new ArrayList<Integer>();
	    while(rs2.next()) {
		idProfesSinCodigo.add(rs2.getInt("ID_Profesor"));
		for(Integer ids: idProfesSinCodigo) {
		    for(Integer idCodigo: idProfesCodigo) {
			if(ids == idCodigo) {
			    idProfesSinCodigo.remove(ids);
			}
		    }
		    for(Integer idDep: idProfesDepart) {
			if(ids == idDep) {
			    idProfesSinCodigo.remove(ids);
			}
		    }
		    
		}
	    }
	    if(idProfesSinCodigo.isEmpty()) {
		System.out.println("No hay profesores disponibles");
	    }
	    PreparedStatement psNombreAsignatura = cnx.prepareStatement("Select * from curso where ID_Curso = ?");
	    psNombreAsignatura.setInt(1, idCurso);
	    ResultSet rsNombreAsi =  psNombreAsignatura.executeQuery();
	    PreparedStatement psAddClase = cnx.prepareStatement("Insert into Clase (Nombre) values (?)");
	    
	    if(numeroAlumnos < n) {
		for(int i = 9; i < n; i = i+9) {
		    psAddClase.setString(1, "Aula de " + rsNombreAsi.getString("Nombre") );
		    psAddClase.addBatch();
		}
		psAddClase.executeBatch();
	    }
	    
	} catch (SQLException e) {
	   
	    e.printStackTrace();
	}
	
    }
}
