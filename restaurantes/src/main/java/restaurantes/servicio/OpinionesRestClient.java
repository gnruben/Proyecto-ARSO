package restaurantes.servicio;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface OpinionesRestClient {

	@FormUrlEncoded
	@POST("opiniones")
	Call<Void> createOpinion(@Field("nombreRecurso") String nombre);
}
