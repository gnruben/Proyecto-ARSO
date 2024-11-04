package opiniones.graphql;

import com.coxautodev.graphql.tools.GraphQLRootResolver;


import opiniones.modelo.*;
import opiniones.servicio.IServicioOpiniones;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

public class Mutation implements GraphQLRootResolver {
    
	private IServicioOpiniones servicio = FactoriaServicios.getServicio(IServicioOpiniones.class);
	//crearOpinion(user: Usuario!, valoracion: Int!, opinion: String!): Opinion
	
    public String crearRecurso(String nombreRecurso ) throws RepositorioException {
        
    	return servicio.crearRecurso(nombreRecurso);
    	
    }
    
    public boolean addValoracion(String idOpinion, String email, String fecha, int calificacion, String comentario) throws RepositorioException {
    	return servicio.addValoracion(idOpinion, email,fecha, calificacion, comentario);
    }
    
    public boolean removeOpinion(String id) throws RepositorioException {
    	return servicio.removeOpinion(id);
    }
    
    
}
