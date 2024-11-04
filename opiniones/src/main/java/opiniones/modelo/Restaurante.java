package opiniones.modelo;

public class Restaurante {
	private String id;
	private String nombre;
	private Opinion opinion;
	
	public Restaurante(String nombre) {
		this.nombre=nombre;
		this.opinion=new Opinion(nombre);
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Opinion getOpinion() {
		return opinion;
	}
	public void setOpinion(Opinion opinion) {
		this.opinion = opinion;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

}
