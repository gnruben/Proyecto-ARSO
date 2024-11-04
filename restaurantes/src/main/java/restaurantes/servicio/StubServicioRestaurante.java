package restaurantes.servicio;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import restaurantes.modelo.Plato;
import restaurantes.modelo.Restaurante;
import restaurantes.modelo.SitioTuristico;

public class StubServicioRestaurante implements IServicioRestaurante{

	private HashMap<String, Restaurante> restaurantes=new HashMap<String, Restaurante>();
	private int id=1;
	@Override
	public String create(Restaurante restaurante) throws RepositorioException {
		String s_id=""+id;
		restaurante.setId(s_id);
		restaurantes.put(s_id, restaurante);
		id++;
		return s_id;
	}

	@Override
	public void update(Restaurante restaurante) throws RepositorioException, EntidadNoEncontrada {
		if(restaurantes.containsKey(restaurante.getId())) {
			restaurantes.put(restaurante.getId(), restaurante);
		}else {
			throw new EntidadNoEncontrada(restaurante.getId()+" no existe en el repositorio");
		}
		
	}

	@Override
	public Restaurante getRestaurante(String id) throws RepositorioException, EntidadNoEncontrada {
		if(restaurantes.containsKey(id)) {
			return restaurantes.get(id);
		}else {
			throw new EntidadNoEncontrada(id+" no existe en el repositorio");
		}		
		
	}

	@Override
	public void removeRestaurante(String id) throws RepositorioException, EntidadNoEncontrada {
		if(restaurantes.containsKey(id)) {
			restaurantes.remove(id);
		}else {
			throw new EntidadNoEncontrada(id+" no existe en el repositorio");
		}
		
	}

	@Override
	public List<RestauranteResumen> getListadoRestaurantes() throws RepositorioException {
		List<RestauranteResumen> lr=new LinkedList<RestauranteResumen>();
		RestauranteResumen rr;
		for(Restaurante r:restaurantes.values()) {
			rr=new RestauranteResumen();
			rr.setId(r.getId());
			rr.setCoordenadas(r.getCoordenadas());
			rr.setNombre(r.getNombre());
			lr.add(rr);
		}
		return lr;
	}

	@Override
	public List<SitioTuristico> getSitiosTuristicos(String id) throws RepositorioException, EntidadNoEncontrada {
		Restaurante r;
		if(restaurantes.containsKey(id)) {
			r=restaurantes.get(id);
			return r.getSitios();
			
		}else {
			throw new EntidadNoEncontrada(id+" no existe en el repositorio");
		}
		
	}

	@Override
	public void setSitiosTuristicos(String id, LinkedList<SitioTuristico> lista)
			throws RepositorioException, EntidadNoEncontrada {
		Restaurante r;
		if(restaurantes.containsKey(id)) {
			r=restaurantes.get(id);
			r.setSitios(lista);
		}else {
			throw new EntidadNoEncontrada(id+" no existe en el repositorio");
		}		
		
	}

	@Override
	public void addPlato(String id, Plato plato) throws RepositorioException, EntidadNoEncontrada {
		Restaurante r;
		if(restaurantes.containsKey(id)) {
			r=restaurantes.get(id);
			r.addPlato(plato);
		}else {
			throw new EntidadNoEncontrada(id+" no existe en el repositorio");
		}		
		
	}

	@Override
	public void removePlato(String id, String nombrePlato) throws RepositorioException, EntidadNoEncontrada {
		Restaurante r;
		if(restaurantes.containsKey(id)) {
			r=restaurantes.get(id);
			r.removePlato(nombrePlato);
		}else {
			throw new EntidadNoEncontrada(id+" no existe en el repositorio");
		}				
	}

	@Override
	public void updatePlato(String id, Plato plato) throws RepositorioException, EntidadNoEncontrada {
		Restaurante r;
		if(restaurantes.containsKey(id)) {
			r=restaurantes.get(id);
			r.removePlato(plato.getNombre());
			r.addPlato(plato);
		}else {
			throw new EntidadNoEncontrada(id+" no existe en el repositorio");
		}		
	}

}
