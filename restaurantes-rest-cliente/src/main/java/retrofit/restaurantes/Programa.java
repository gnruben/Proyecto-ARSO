package retrofit.restaurantes;


import restaurantes.modelo.Plato;
import restaurantes.modelo.Restaurante;
import restaurantes.modelo.SitioTuristico;
import retrofit.restaurantes.Listado.ResumenExtendido;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Programa {

	public static void main(String[] args) throws Exception {

		//String url = "http://localhost:8080/api/";
		String url = System.getenv("RESTAURANTE_API_URL"); 
		
		Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
				.addConverterFactory(GsonConverterFactory.create()).build();

		RestaurantesRestClient service = retrofit.create(RestaurantesRestClient.class);

		// Creación

		// Creación de un restaurante

		Restaurante restaurante = new Restaurante();
		restaurante.setNombre("retrofit");
		restaurante.setCoordenadas("3242-5634");

		// añado plato y sitio al restaurante
		
		Plato plato = new Plato();
		plato.setNombre("plato retrofit");
		plato.setDescripcion("plato de prueba para retrofit");
		plato.setPrecio(3);
		plato.setDisponible(true);
		
		SitioTuristico sitio = new SitioTuristico();
		sitio.setResumen("Sitio de prueba retrofit");
		sitio.setWikipedia("www.wikipedia.com/sitiodepruebaretrofit");

		restaurante.getPlatos().add(plato);
		restaurante.getSitios().add(sitio);

		Response<Void> resultado = service.createRestaurante(restaurante).execute();

		String url1 = resultado.headers().get("Location");

		String id1 = url1.substring(url1.lastIndexOf("/") + 1);

		System.out.println("Restaurante creado: " + url1);
		System.out.println("Id: " + id1);

		// Recuperación

		Restaurante restaurante2 = service.getResturante(id1).execute().body();

		System.out.println("Restaurante: " + restaurante2.getNombre() + " - " + restaurante2.getId());

		// Actualización

		restaurante2.setCoordenadas("5555555555-5555555555555");

		service.updateResturante(id1, restaurante2).execute();

		System.out.println("Restaurante actualizado");

		// Añadir plato

		Response<Void> respuesta = service.addPlato(id1, "plato de prueba retrofit 2", 5).execute();

		System.out.println("Plato añadido al restaurante: " + id1);
		System.out.println("Código de respuesta: " + respuesta.message());

		// Listado

		Listado listado = service.getListado().execute().body();

		System.out.println("Listado:");
		for (ResumenExtendido restauranteResumen : listado.getRestaurante()) {
			System.out.println("\t" + restauranteResumen.getResumen().getNombre());
			System.out.println("\t" + restauranteResumen.getUrl());
		}

		System.out.println("fin.");

	}
}
