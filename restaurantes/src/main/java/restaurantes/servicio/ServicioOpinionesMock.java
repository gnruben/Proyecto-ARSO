package restaurantes.servicio;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServicioOpinionesMock implements IMockServicioOpiniones {

	private final Retrofit retrofit;
	private OpinionesRestClient service;
	//String url = "http://localhost:5000/api/";
	private String url = System.getenv("OPINIONES_API_URL");

	public ServicioOpinionesMock() {
		retrofit = new Retrofit.Builder().baseUrl(url)
				.addConverterFactory(GsonConverterFactory.create()).build();
		service = retrofit.create(OpinionesRestClient.class);
	}

	@Override
	public String registrarRestaurante(String nombre) throws IOException, InterruptedException {
		 Response<Void> respuesta = service.createOpinion(nombre).execute();
		
		 String url = respuesta.headers().get("Location");

		 String id = url.substring(url.lastIndexOf("/") + 1);
		 
		 return id;
	}

}
