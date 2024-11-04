package usuarios.repositorio;

import repositorio.RepositorioException;
import repositorio.RepositorioMemoria;
import usuarios.modelo.Rol;
import usuarios.modelo.Usuario;

public class RepositorioUsuariosMemoria extends RepositorioMemoria<Usuario> {

	public RepositorioUsuariosMemoria()  {
		
		// Datos iniciales
		
		try {
			Usuario usuario1 = new Usuario("Marcos", "marcos@um.es", "MarcosMenarguez", Rol.GESTOR);
			this.add(usuario1);
			
			Usuario usuario2 = new Usuario("gnruben", "gnruben@gmail.com", "gnruben", Rol.GESTOR);
			this.add(usuario2);
			
			Usuario usuario3 = new Usuario("EfrosEM", "eduardoespinosamerono@gmail.com", "EfrosEM", Rol.GESTOR);
			this.add(usuario3);
			
		} catch (RepositorioException e) {
			e.printStackTrace(); // no debe suceder en un repositorio en memoria
		}
		
	}
	
}
