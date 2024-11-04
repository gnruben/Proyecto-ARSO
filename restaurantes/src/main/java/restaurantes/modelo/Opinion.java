package restaurantes.modelo;

/**
 * opinión de un restaurante.
La opinión refleja qué usuario esta valorando a qué restaurante, además de
contener al menos la siguiente información: un valor de opinión en una escala
numérica y un texto que describa la opinión.
 * */
public class Opinion {
	private String id;
	private Usuario user;
	private int valoracion;
	private String opinion;
	
	
	public Opinion( Usuario user, int valoracion, String opinion) {
		super();
		this.user = user;
		this.valoracion = valoracion;
		this.opinion = opinion;
	}
	public Usuario getUser() {
		return user;
	}
	public void setUser(Usuario user) {
		this.user = user;
	}
	public int getValoracion() {
		return valoracion;
	}
	public void setValoracion(int valoracion) {
		this.valoracion = valoracion;
	}
	public String getOpinion() {
		return opinion;
	}
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
