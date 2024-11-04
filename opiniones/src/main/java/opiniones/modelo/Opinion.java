package opiniones.modelo;

import java.util.LinkedList;
import java.util.List;

import repositorio.Identificable;

/**
 * opinión de un restaurante.
La opinión refleja qué usuario esta valorando a qué restaurante, además de
contener al menos la siguiente información: un valor de opinión en una escala
numérica y un texto que describa la opinión.
 * */
public class Opinion implements Identificable{
	private String id;
	private String nombreRecurso;
	private List<Valoracion> valoraciones;
	
	public Opinion() {
		this.valoraciones= new LinkedList<Valoracion>();
	}
	public Opinion(String nombreRecurso) {
		super();
		//this.user=user;
		this.valoraciones= new LinkedList<Valoracion>();
		this.nombreRecurso=nombreRecurso;
	}
	
	
	public List<Valoracion> getValoraciones() {
		return valoraciones;
	}
	public void addAllValoracion(List<Valoracion> valoraciones) {
		this.valoraciones.addAll(valoraciones);
	}
	public void addValoracion(String email, String fecha, int calificacion, String comentario) {
		Valoracion v=new Valoracion(email, fecha, calificacion);
		
			v.setComentario(comentario);
		
		this.removeValoracion(email);
		this.valoraciones.add(v);
	}
	public void removeValoracion(String userEmail){
		for(Valoracion i:valoraciones) {
			if(i.getUserEmail().equals(userEmail)) {
				valoraciones.remove(i);
				break;
			}		
		}
	}
	public String getNombreRecurso() {
		return nombreRecurso;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		String s="";
		s+=this.id+",Valoraciones: [";
		for(Valoracion v:this.valoraciones) {
			s+=v.getUserEmail()+", ";
		}
		s+="]";
		return s;
	}
}
