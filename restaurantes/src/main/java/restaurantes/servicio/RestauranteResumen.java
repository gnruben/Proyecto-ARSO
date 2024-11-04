package restaurantes.servicio;

public class RestauranteResumen {

	private String id;
	private String nombre;
	private String coordenadas;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCoordenadas() {
		return coordenadas;
	}
	public void setCoordenadas(String coordenadas) {
		this.coordenadas = coordenadas;
	}
	
	@Override
	public String toString() {
		return "RestauranteResumen [id=" + id + ", nombre=" + nombre + ", coordenadas=" + coordenadas + "]";
	}
}
