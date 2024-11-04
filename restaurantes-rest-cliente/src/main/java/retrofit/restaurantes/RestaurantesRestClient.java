package retrofit.restaurantes;

import restaurantes.modelo.Restaurante;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RestaurantesRestClient {

	@POST("restaurantes")
	Call<Void> createRestaurante(@Body Restaurante restaurante);
	
	@GET("restaurantes/{id}")
	Call<Restaurante> getResturante(@Path("id") String id);
	
	@PUT("restaurantes/{id}")
	Call<Void> updateResturante(@Path("id") String id, @Body Restaurante restaurante);

	@DELETE("restaurantes/{id}")
	Call<Void> removeRestaurante(@Path("id") String id);
	
	@FormUrlEncoded
	@POST("actividades/{id}/platos")
	Call<Void> addPlato(@Path("id") String id, @Field("nombre") String nombre, @Field("precio") float precio);
	
	@GET("restaurantes")
	Call<Listado> getListado();
}
