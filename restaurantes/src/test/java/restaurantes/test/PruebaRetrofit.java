package restaurantes.test;

import java.io.IOException;


import restaurantes.servicio.OpinionesRestClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PruebaRetrofit {

	public static void main(String[] args) {
		
		//String url = "http://localhost:5000/api/";
		String url = System.getenv("OPINIONES_API_URL");
		
		Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
				.addConverterFactory(GsonConverterFactory.create()).build();
		
		OpinionesRestClient service = retrofit.create(OpinionesRestClient.class);
		
		String nombre = "retrofit";
		
		try {
			 retrofit2.Response<Void> respuesta = service.createOpinion(nombre).execute();
			 
			 respuesta.code();
			
			System.out.println(respuesta.code());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
}
