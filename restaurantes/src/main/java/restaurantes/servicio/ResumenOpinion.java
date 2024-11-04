package restaurantes.servicio;

public class ResumenOpinion {
	private String id;
	private float calificacionMedia;
	private int numeroValoraciones;
	
	public float getCalificacionMedia() {
		return calificacionMedia;
	}
	public void setCalificacionMedia(float calificacionMedia) {
		this.calificacionMedia = calificacionMedia;
	}
	public int getNumeroValoraciones() {
		return numeroValoraciones;
	}
	public void setNumeroValoraciones(int numeroValoraciones) {
		this.numeroValoraciones = numeroValoraciones;
	}
	public String getId() {
		return id;
	}
}
