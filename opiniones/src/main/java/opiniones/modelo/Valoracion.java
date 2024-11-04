package opiniones.modelo;

public class Valoracion {
	private String userEmail;
	private String fecha;
	private int calificacion;
	private String comentario;//opcional
	
	public Valoracion() {}
	public Valoracion(String userEmail,String fecha, int calificacion) {
		this.userEmail=userEmail;
		this.fecha=fecha;
		this.calificacion=calificacion;
	}
	
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public int getCalificacion() {
		return calificacion;
	}
	public void setCalificacion(int calificacion) {
		this.calificacion = calificacion;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	

}
