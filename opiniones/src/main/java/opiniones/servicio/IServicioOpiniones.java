package opiniones.servicio;

import java.util.List;

import opiniones.modelo.*;
import repositorio.RepositorioException;

public interface IServicioOpiniones {
	/*
	 * Registrar un recurso (con un nombre) para ser valorado (crea una opinión). Esta operación retorna un
	identificador de la opinión que se utilizará en el resto de operaciones.
	
	Añadir una valoración sobre un recurso. Si un usuario registra una segunda valoración para el mismo
	recurso, ésta reemplazará a la primera.
	
	Recuperar la opinión de un recurso.
	
	Eliminar una opinión del servicio (elimina una opinión y sus valoraciones).*/

	String crearRecurso( String nombreRecurso) throws RepositorioException;
	
	boolean addValoracion(String idOpinion, String email, String fecha, int calificacion, String comentario) throws RepositorioException;
	
	Opinion getOpinion(String idOpinion) throws RepositorioException;

	boolean removeOpinion(String id) throws RepositorioException;
	
	
	List<Valoracion> findAll(String idOpinion) throws RepositorioException;	
}
