package restaurantes.modelo;
/**
 *  los platos
que conforman el menú del restaurante: nombre, descripción, precio y
disponibilidad (por si deja de ofrecerse durante algún tiempo)
 * */
public class Plato {
	
	private String nombre;
	private String descripcion;
	private float precio;
	private Boolean disponible;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public float getPrecio() {
		return precio;
	}
	public void setPrecio(float precio) {
		this.precio = precio;
	}
	public Boolean getDisponible() {
		return disponible;
	}
	public void setDisponible(Boolean disponible) {
		this.disponible = disponible;
	};
	

	
}
