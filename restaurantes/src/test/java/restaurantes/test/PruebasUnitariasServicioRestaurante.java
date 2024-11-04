package restaurantes.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import restaurantes.modelo.Plato;
import restaurantes.modelo.Restaurante;
import restaurantes.modelo.SitioTuristico;
import restaurantes.servicio.IServicioRestaurante;
import restaurantes.servicio.RestauranteResumen;
import restaurantes.servicio.ServicioRestaurante;

@SuppressWarnings("unused")
public class PruebasUnitariasServicioRestaurante {
	private IServicioRestaurante sr=new ServicioRestaurante();
	private List<String> ids=new LinkedList<String>();
	
	@BeforeAll
	public void test() {}
	
	@Test
	public void createTest1() throws RepositorioException {
		Restaurante r=new Restaurante();
		r.setNombre("T1");
		
		ids.add(sr.create(r));
		assertTrue(ids.get(0)!=null);
	}
//	void update(Restaurante restaurante) throws RepositorioException, EntidadNoEncontrada;
	
	@Test
	public void updateRestauranteTest() throws RepositorioException, EntidadNoEncontrada {

		Restaurante r=new Restaurante();
		r.setNombre("T1");
		ids.add(sr.create(r));
		
		sr.update(r);
	}
	
	//prueba que lanza la excepción adecuada si el restaurante no está en el repositorio
	@Test
	public void updateRestauranteTest2() throws RepositorioException{
		Restaurante r=new Restaurante();
		r.setNombre("T1");
		

		assertThrows(EntidadNoEncontrada.class, ()->{sr.update(r);});

	}
	//######################## updateRestaurante DONE #####################
	
	
	/**
	 * Recupera un restaurante utilizando el identificador. 	
	 * @throws RepositorioException 
	 * @throws EntidadNoEncontrada 
	 */
	//Restaurante getRestaurante(String id) throws RepositorioException, EntidadNoEncontrada;
	@Test
	public void getRestauranteTest() throws RepositorioException, EntidadNoEncontrada {
		
		Restaurante r1,r=new Restaurante();
		r.setNombre("T1");
		ids.add(sr.create(r));
		
		r1=sr.getRestaurante(ids.get(0));
		
		assertTrue(r1.equals(r));
		
	}
	

	//comprueba si se lanza la excepción adecuada al hacer la petición sobre un restaurante que no está
	//en el repositorio
	@Test
	public void getRestauranteTest2() throws RepositorioException{
		
		assertThrows(EntidadNoEncontrada.class, ()->{sr.getRestaurante("no existe");});
	}
	//############################### removeRestaurante ####################
	
	
	
	/**
	 * Elimina un restaurante utilizando el identificador.
	 * @throws RepositorioException 
	 * @throws EntidadNoEncontrada 
	 */
	//void removeRestaurante(String id) throws RepositorioException, EntidadNoEncontrada;
	
	@Test
	public void removeRestauranteTest() throws RepositorioException, EntidadNoEncontrada {
		Restaurante r=new Restaurante();
		r.setNombre("T1");
		ids.add(sr.create(r));
		
		sr.removeRestaurante(ids.get(0));
	}
	
	//TODO: debe lanzar una excepción si el restaurante no existe?
	//comprueba si lanza la excepción EntidadNoEncontrada al intentar eliminar un restaurante que no
	//está en el repositorio
	
	@Test
	public void removeRestauranteTest2() throws RepositorioException{
	
		assertThrows(EntidadNoEncontrada.class,()->{ sr.removeRestaurante("no existe");});
	}	
	
	
	//############################### getListadoRestaurantes ####################
	
	
	/**
	 * Retorna un resumen de todas los restaurantes.	
	 * @throws RepositorioException 
	 */
	//List<RestauranteResumen> getListadoRestaurantes() throws RepositorioException;

	@Test
	public void getListadoRestaurantesTest() throws RepositorioException {
		Restaurante r=new Restaurante();
		r.setNombre("T1");
		ids.add(sr.create(r));		
		
		List<RestauranteResumen> rr=sr.getListadoRestaurantes();
		assertTrue(rr.size()>0);
	}

	//############################### getSitiosTuristicos ####################
	
	/**
	 *  Retorna una lista de sitios turísticos.	
	 * @throws RepositorioException 
	 * @throws EntidadNoEncontrada 
	 */
	//List<SitioTuristico> getSitiosTuristicos(String id) throws RepositorioException, EntidadNoEncontrada;
	
	@Test
	public void getSitiosTuristicosTest() throws RepositorioException, EntidadNoEncontrada {
		Restaurante r=new Restaurante();
		r.setNombre("T1");
		
		LinkedList<SitioTuristico> l1=new LinkedList<SitioTuristico>();
		List<?> l2;
		SitioTuristico s=new SitioTuristico();
		s.setResumen("Desc1");
		l1.add(s);
		r.setSitios(l1);
		
		ids.add(sr.create(r));//creamos el restaurante en el repositorio con el sitio turístico 
		
		l2=sr.getSitiosTuristicos(ids.get(0));
		assertTrue(l2.size()==1);
			
	}

	
	//comprueba que se lanza la excepción EntidadNoEncontrada cuando no existe el restaurante
	@Test
	public void getSitiosTuristicosTest2() throws RepositorioException, EntidadNoEncontrada {

		assertThrows(EntidadNoEncontrada.class, ()->{sr.getSitiosTuristicos("no existe");});

			
	}
	
	//############################### setSitiosTuristicos ####################
	
	
	/**
	 *  Esta operación establece como sitios turísticos del restaurante los que se establecen como parámetro.
	 * @throws RepositorioException 
	 * @throws EntidadNoEncontrada 
	 */
	//void setSitiosTuristicos(String id, LinkedList<SitioTuristico> lista) throws RepositorioException, EntidadNoEncontrada;

	@Test
	public void setSitiosTuristicosTest() throws RepositorioException, EntidadNoEncontrada {

		
		Restaurante r=new Restaurante();
		r.setNombre("T1");
		ids.add(sr.create(r));
		
		
		LinkedList<SitioTuristico> l1=new LinkedList<SitioTuristico>();
		LinkedList<SitioTuristico> l2;
		SitioTuristico s=new SitioTuristico();
		s.setResumen("Desc1");
		l1.add(s);
		
		sr.setSitiosTuristicos(ids.get(0), l1);
		
		l2=(LinkedList<SitioTuristico>) sr.getSitiosTuristicos(ids.get(0));
		
		assertTrue(
				l2!=null&&
				l2.size()==1&&
				l2.getFirst().equals(s));
		
	}
	

	
	//Comprueba que se lanza la excepción EntidadNoEncontrada si se ejecuta setSitiosTuristicos 
	//sobre un restaurante que no está en el repositorio
	@Test
	public void setSitiosTuristicosTest2() throws RepositorioException, EntidadNoEncontrada {
		
		LinkedList<SitioTuristico> l1=new LinkedList<SitioTuristico>();

		SitioTuristico s=new SitioTuristico();
		s.setResumen("Desc1");
		l1.add(s);

		assertThrows(EntidadNoEncontrada.class, ()->{sr.setSitiosTuristicos(ids.get(0), l1);});

		
	}	
	
	//############################### addPlato ####################	
	
	
	/**
	 *  Añadir un plato al restaurante.
	 *  @param el nombre del plato debe ser único en cada restaurante
	 */
//	void addPlato(String id, Plato plato) throws RepositorioException, EntidadNoEncontrada;

	
	@Test
	public void addPlatoTest() throws RepositorioException, EntidadNoEncontrada {
		Restaurante r1=new Restaurante();
		Restaurante r2;
		r1.setNombre("T1");
		Plato p=new Plato();
		p.setNombre("PTest1");
		ids.add(sr.create(r1));
		
		
		
		sr.addPlato(ids.get(0), p);
		r2=sr.getRestaurante(ids.get(0));
		assertTrue(r2.getPlato(p.getNombre()).getNombre().equals(p.getNombre()));
		
		
	}
	
	//Comprueba si se lanza la excepción EntidadNoEncontrada al añadir un plato a un Restaurante 
	//que no está en el repositorio
	@Test
	public void addPlatoTest2() throws RepositorioException, EntidadNoEncontrada {

		Plato p=new Plato();
		p.setNombre("PTest1");

		assertThrows(EntidadNoEncontrada.class, ()->{sr.addPlato("no existe", p);});

		
	}
	
	
	//TODO: se debe preguntar cómo hay que tratar el caso de que el nombre del plato coincida
	//es posible que tengamos que lanzar un error, en cuyo caso hay que cambiar el test de abajo
	
	@Test
	public void addPlatoTest3() throws RepositorioException, EntidadNoEncontrada {
		
		//se crea el restaurante en el repositorio
		Restaurante r1=new Restaurante();
		Restaurante r2;
		r1.setNombre("T1");
		ids.add(sr.create(r1));
		
		//se añade el plato al restaurante 
		Plato p=new Plato();
		p.setNombre("PTest1");		
		sr.addPlato(ids.get(0), p);
		
		//se vuelve a añadir el mismo plato
		sr.addPlato(ids.get(0), p);
		
				
		r2=sr.getRestaurante(ids.get(0));
		assertTrue(r2.getPlatos().size()==1);//sólo debe estar el plato original
		
		
	}

	
	//############################### removePlato ####################
	
	
	/**
	 *  Elimina un plato del restaurante.
	 *  @param el nombre del plato debe ser único en cada restaurante
	 * @throws RepositorioException 
	 * @throws EntidadNoEncontrada 
	 */
	//void removePlato(String id, Plato plato) throws RepositorioException, EntidadNoEncontrada;
	
	@Test
	public void removePlatoTest() throws RepositorioException, EntidadNoEncontrada {
		//se crea el restaurante
		Restaurante r1=new Restaurante();
		Restaurante r2;
		r1.setNombre("T1");
		LinkedList<Plato> lp=new LinkedList<Plato>();
		
		//se crea el plato y se añade a una lista
		Plato p=new Plato();
		p.setNombre("PTest1");
		lp.add(p);
		r1.setPlatos(lp);		
		
		ids.add(sr.create(r1));//se crea el restaurante
		
		sr.removePlato(ids.get(0), p.getNombre());
		
		r2=sr.getRestaurante(ids.get(0));
		
		assertFalse(r2.existePlato(p.getNombre()));	 //no debe existir el plato después de eliminarlos
		
	}

	//TODO: Prueba si devuelve EntidadNoEncontrada si no existe el restaurante en el repositorio
	
	@Test
	public void removePlatoTest2() throws RepositorioException, EntidadNoEncontrada {

		Plato p=new Plato();
		p.setNombre("PTest1");
		
		assertThrows(EntidadNoEncontrada.class, ()->{sr.removePlato("no existe", p.getNombre());});
		
	}
	
	//TODO:Preguntar qué debe hacer el método si el plato no está 
	//pueba si devuelve EntidadNoEncontrada si el plato no existe
	@Test
	public void removePlatoTest3() throws RepositorioException, EntidadNoEncontrada {
		//se crea el restaurante
		Restaurante r1=new Restaurante();
		Restaurante r2;
		r1.setNombre("T1");

		ids.add(sr.create(r1));//se crea el restaurante en el repositorio
		
		assertThrows(EntidadNoEncontrada.class, ()->{
			sr.removePlato(ids.get(0), "no existe");	
		});
		
	}
	
	//############################### UpdatePlato ####################
	
	
	/**
	 * Actualiza un plato del restaurante.
	 * @param el nombre del plato debe formar parte del restaurante.
	 * @throws RepositorioException 
	 * @throws EntidadNoEncontrada 
	 */
	//void updatePlato(String id, Plato plato) throws RepositorioException, EntidadNoEncontrada;
	@Test
	public void updatePlatoTest() throws RepositorioException, EntidadNoEncontrada {
		Restaurante r1=new Restaurante();
		Restaurante r2;
		r1.setNombre("T1");
		
		
		Plato p=new Plato();
		p.setNombre("PTest1");
		
		LinkedList<Plato> lp=new LinkedList<Plato>();
		lp.add(p);
		r1.setPlatos(lp);
		
		
		ids.add(sr.create(r1));
		p.setDescripcion("Plato hecho con reducción de salsa de salmón con esferificaciones de fresa");
		sr.updatePlato(ids.get(0), p);
		
		
		r2=sr.getRestaurante(ids.get(0));
		
		assertTrue(r2.getPlato(p.getNombre()).getDescripcion().equals(p.getDescripcion()));

	}


	//Comprueba si se lanza la excepción EntidadNoEncontrada si el restaurante no existe 
	@Test
	public void updatePlatoTest2() throws RepositorioException, EntidadNoEncontrada {
		
		
		
		Plato p=new Plato();
		p.setNombre("PTest1");

		p.setDescripcion("Plato hecho con reducción de salsa de salmón con esferificaciones de fresa");
		
		
		assertThrows(EntidadNoEncontrada.class, ()->{sr.updatePlato("no existe", p);});
		

	}

	//Comprueba si lanza el error EntidadNoEncontrada si el plato no existe
	@Test
	public void updatePlatoTest3() throws RepositorioException, EntidadNoEncontrada {
		Restaurante r1=new Restaurante();
		
		r1.setNombre("T1");
		
		
		LinkedList<Plato> lp=new LinkedList<Plato>();
		
		r1.setPlatos(lp);
		
		Plato p=new Plato();
		p.setNombre("PTest1");
		
		ids.add(sr.create(r1));
		p.setDescripcion("Plato hecho con reducción de salsa de salmón con esferificaciones de fresa");
		
		assertThrows(EntidadNoEncontrada.class, ()->{sr.updatePlato(ids.get(0), p);});
	}

	//después de cada test elimina cada restaurante creado en el repositorio
	@AfterAll
	public void delete() throws RepositorioException, EntidadNoEncontrada {
		for(String i:ids) {
			sr.removeRestaurante(i);
		}
	}
	
	

}
