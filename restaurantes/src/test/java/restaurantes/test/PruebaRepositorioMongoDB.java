package restaurantes.test;


import java.util.List;

import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;
import restaurantes.modelo.Restaurante;
import restaurantes.modelo.SitioTuristico;

public class PruebaRepositorioMongoDB {

	public static void main(String[] args) throws RepositorioException, EntidadNoEncontrada {

		Repositorio<Restaurante, String> repositorio = FactoriaRepositorios.getRepositorio(Restaurante.class);

		// Crear un restaurante
		Restaurante res = new Restaurante();
		res.setCoordenadas("33-43");
		res.setNombre("Restaurante 3");
		// Sitio 1
		SitioTuristico sitio1 = new SitioTuristico();
		sitio1.setResumen("sitio 3 ...");
		sitio1.setWikipedia("https://es.wikipedia.org/wiki/Catedral_de_Murcia");
		res.getSitios().add(sitio1);

		repositorio.add(res);

		// El objeto restaurante recibie el id tras la inserción

		//System.out.println("Id asignado: " + res.getId());

		res.setCoordenadas("55-75");

		repositorio.update(res);
		//repositorio.delete(res);
		
		Restaurante rest = repositorio.getById("0934e2e0-95f6-48d8-98d8-4071ef0fa770");
		
		System.out.println(rest);
		
	
		List<String> lista = repositorio.getIds();
		
		for (String id : lista) {
			System.out.println(id);
		}
		

	}
}
