package restaurantes.modelo;

import java.time.LocalDate;

public class Incidencia {

	private Plato plato;
	private LocalDate fecha;
	private String descripcion;
	
	public Incidencia(Plato _plato, String _descripcion) {
		plato = _plato;
		descripcion = _descripcion;
		fecha = LocalDate.now();
	}
	
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Plato getPlato() {
		return plato;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	
	
}
