package restaurantes.modelo;

import java.util.LinkedList;
import java.util.List;

public class SitioTuristico {
	
	private String nombre;
	private String wikipedia;
	private String resumen;
	private String imagen;
	private List<String> categorias = new LinkedList<String>();
	private List<String> enlaces = new LinkedList<String>();
	
	
	public String getWikipedia() {
		return wikipedia;
	}
	public void setWikipedia(String wikipedia) {
		this.wikipedia = wikipedia;
	}
	public String getResumen() {
		return resumen;
	}
	public void setResumen(String resumen) {
		this.resumen = resumen;
	}
	public List<String> getCategorias() {
		return categorias;
	}
	public void setCategorias(List<String> categorias2) {
		this.categorias = categorias2;
	}
	public List<String> getEnlaces() {
		return enlaces;
	}
	public void setEnlaces(List<String> enlaces) {
		this.enlaces = enlaces;
	}
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	
}
