package opiniones.servicio;

import java.util.LinkedList;
import java.util.List;

import opiniones.modelo.*;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;

public class ServicioOpiniones implements IServicioOpiniones {

	private Repositorio<Opinion, String> repositorioOpiniones = FactoriaRepositorios.getRepositorio(Opinion.class);

	// devolver id Opinion

	@Override
	public String crearRecurso(String nombreRecurso) throws RepositorioException {

		Opinion op = new Opinion(nombreRecurso);

		String id = repositorioOpiniones.add(op);

		return id;

	}

	@Override
	public List<Valoracion> findAll(String idOpinion) throws RepositorioException {
		@SuppressWarnings("unused")
		List<Valoracion> vs = new LinkedList<Valoracion>();

		Opinion op;
		try {
			op = repositorioOpiniones.getById(idOpinion);
			return op.getValoraciones();

		} catch (EntidadNoEncontrada e) {

			e.printStackTrace();
			return null;
		}

	}

	@Override
	public boolean addValoracion(String idOpinion, String email, String fecha, int calificacion, String comentario)
			throws RepositorioException {

		Opinion op;
		try {
			op = repositorioOpiniones.getById(idOpinion);
			op.addValoracion(email, fecha, calificacion, comentario);
			repositorioOpiniones.update(op);
			return true;
		} catch (RepositorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EntidadNoEncontrada e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	@Override
	public Opinion getOpinion(String idOpinion) throws RepositorioException {
		try {
			Opinion op = repositorioOpiniones.getById(idOpinion);
			return op;
		} catch (RepositorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EntidadNoEncontrada e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean removeOpinion(String idOpinion) throws RepositorioException {
		Opinion op;
		try {
			op = repositorioOpiniones.getById(idOpinion);
			repositorioOpiniones.delete(op);
			return true;
		} catch (RepositorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EntidadNoEncontrada e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

}
