package restaurantes.test;

import java.util.List;

import restaurantes.modelo.Plato;
import restaurantes.modelo.Restaurante;
import restaurantes.modelo.SitioTuristico;
import restaurantes.servicio.IServicioRestaurante;
import restaurantes.servicio.RestauranteResumen;
import servicio.FactoriaServicios;

public class PruebaServicio {

	public static void main(String[] args) throws Exception {

		IServicioRestaurante servicio = FactoriaServicios.getServicio(IServicioRestaurante.class);

		// 1. Creación de un restaurante

		Restaurante restaurante = new Restaurante();
		restaurante.setNombre("Restaurante Prueba");
		restaurante.setCoordenadas("30009");

		// Plato

		Plato plato = new Plato();
		plato.setNombre("Solomillo");
		plato.setPrecio(9);
		plato.setDisponible(true);
		restaurante.getPlatos().add(plato);

		String id = servicio.create(restaurante);
		System.out.println(id);

		// 2. Actualización

		restaurante = servicio.getRestaurante(id);
		List<SitioTuristico> sitios = servicio.getSitiosTuristicos(id);

		restaurante.setSitios(sitios);

		servicio.update(restaurante);

		// 3. Añadir Plato

		Plato plato2 = new Plato();
		plato2.setNombre("Lentejas");
		plato2.setPrecio(8);
		plato2.setDisponible(true);

		servicio.addPlato(id, plato2);

		// 4. Listado de resúmenes

		System.out.println("Listado:");
		for (RestauranteResumen resumen : servicio.getListadoRestaurantes())
			System.out.println("\t" + resumen);

		System.out.println("Fin.");
	}
}

