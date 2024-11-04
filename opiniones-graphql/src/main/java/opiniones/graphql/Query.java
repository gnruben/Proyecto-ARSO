package opiniones.graphql;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLRootResolver;

import opiniones.modelo.*;
import opiniones.servicio.IServicioOpiniones;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

public class Query implements GraphQLRootResolver {
    
	private IServicioOpiniones servicio = FactoriaServicios.getServicio(IServicioOpiniones.class);
	
	public Opinion getOpinion(String idOpinion) throws RepositorioException {
		return servicio.getOpinion(idOpinion);
	}
    public List<Valoracion> findAll(String idOpinion) throws RepositorioException {
        return servicio.findAll(idOpinion);
    }
    
    
}