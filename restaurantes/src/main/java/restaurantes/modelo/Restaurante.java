package restaurantes.modelo;

import java.util.LinkedList;
import java.util.List;

//TODO: consultar las responsabiliddes de la clase
public class Restaurante {

	private String id;
	private String nombre;
	private String coordenadas;
	private List<SitioTuristico> sitios;
	private List<Plato> platos;
	
	//añadidos por la tarea 9
	private int numValoraciones;
	private float calificacionMedia;
	private String idOpiniones;

	public int getNumValoraciones() {
		return numValoraciones;
	}

	public void setNumValoraciones(int numValoraciones) {
		this.numValoraciones = numValoraciones;
	}

	public float getCalificacionMedia() {
		return calificacionMedia;
	}

	public void setCalificacionMedia(float calificacionMedia) {
		this.calificacionMedia = calificacionMedia;
	}

	public String getIdOpiniones() {
		return idOpiniones;
	}

	public void setIdOpiniones(String idOpiniones) {
		this.idOpiniones = idOpiniones;
	}

	public Restaurante() {
		sitios = new LinkedList<SitioTuristico>();
		platos = new LinkedList<Plato>();
	}

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

	public List<SitioTuristico> getSitios() {
		return sitios;
	}

	public void setSitios(List<SitioTuristico> sitios) {
		this.sitios = sitios;
	}

	public List<Plato> getPlatos() {
		return platos;
	}

	public void setPlatos(List<Plato> platos) {
		this.platos = platos;
	}

	public boolean addPlato(Plato p) {
		if (this.existePlato(p.getNombre())) {
			return false;
		} else {
			platos.add(p);
			return true;
		}

	}

	public Plato getPlato(String nombrePlato) {
		for (Plato p : platos) {
			if (p.getNombre().equals(nombrePlato)) {
				return p;
			}
		}
		return null;
	}

	public boolean existePlato(String plato) {

		for (Plato p : platos) {
			if (p.getNombre().equals(plato))
				return true;
		}
		return false;
	}

	public void removePlato(String nombrePlato) {

		if (this.existePlato(nombrePlato)) {
			for (int i = 0; i < platos.size(); i++) {
				if (platos.get(i).getNombre().equals(nombrePlato)) {
					platos.remove(i);
				}
			}
		}
	}

	public void addSitioTuristico(SitioTuristico st) {
		sitios.add(st);
	}

}
