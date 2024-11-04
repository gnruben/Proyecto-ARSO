package restaurantes.servicio;

import java.util.LinkedList;
import java.util.List;

import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import restaurantes.modelo.Plato;
import restaurantes.modelo.Restaurante;
import restaurantes.modelo.SitioTuristico;

public interface IServicioRestaurante {

	
	/** 
	 * Metodo de alta de una restaurante.
	 * 
	 * @param restaurante debe ser valido respecto al modelo de dominio
	 * @return identificador de la actividad
	 */
	String create(Restaurante restaurante) throws RepositorioException;
	
	/**
	 * Actualiza un restaurante.
	 * @param restaurante debe ser valido respecto al modelo de dominio
	 */
	void update(Restaurante restaurante) throws RepositorioException, EntidadNoEncontrada;
	
	/**
	 * Recupera un restaurante utilizando el identificador. 	
	 */
	Restaurante getRestaurante(String id) throws RepositorioException, EntidadNoEncontrada;
	
	/**
	 * Elimina un restaurante utilizando el identificador.
	 */
	void removeRestaurante(String id) throws RepositorioException, EntidadNoEncontrada;
	
	/**
	 * Retorna un resumen de todas los restaurantes.	
	 */
	List<RestauranteResumen> getListadoRestaurantes() throws RepositorioException;
	
	/**
	 *  Retorna una lista de sitios turísticos.	
	 */
	List<SitioTuristico> getSitiosTuristicos(String id) throws RepositorioException, EntidadNoEncontrada;
	
	/**
	 *  Esta operación establece como sitios turísticos del restaurante los que se establecen como parámetro.
	 */
	void setSitiosTuristicos(String id, LinkedList<SitioTuristico> lista) throws RepositorioException, EntidadNoEncontrada;
	
	/**
	 *  Añadir un plato al restaurante.
	 *  @param el nombre del plato debe ser único en cada restaurante
	 */
	void addPlato(String id, Plato plato) throws RepositorioException, EntidadNoEncontrada;
	
	/**
	 *  Elimina un plato del restaurante.
	 *  @param el nombre del plato debe ser único en cada restaurante
	 */
	void removePlato(String id, String nombrePlato) throws RepositorioException, EntidadNoEncontrada;
	
	/**
	 * Actualiza un plato del restaurante.
	 * @param el nombre del plato debe formar parte del restaurante.
	 */
	void updatePlato(String id, Plato plato) throws RepositorioException, EntidadNoEncontrada;
}
