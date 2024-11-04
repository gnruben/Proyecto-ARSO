package restaurantes.modelo;

import java.time.LocalDate;

public class Usuario {

	private String nombre;
	private String apellidos;
	private String email;
	private String clave;
	private LocalDate fechaNacimiento;
	
	public Usuario(String _nombre, String _apellidos, String _email, String _clave, LocalDate fecha) {
		nombre = _nombre;
		apellidos = _apellidos;
		email = _email;
		clave = _clave;
		fechaNacimiento = fecha;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

}
