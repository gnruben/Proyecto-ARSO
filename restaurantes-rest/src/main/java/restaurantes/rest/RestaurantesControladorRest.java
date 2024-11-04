package restaurantes.rest;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import restaurantes.modelo.Plato;
import restaurantes.modelo.Restaurante;
import restaurantes.rest.Listado.ResumenExtendido;
import restaurantes.rest.seguridad.AvailableRoles;
import restaurantes.rest.seguridad.Secured;
import restaurantes.servicio.IServicioRestaurante;
import restaurantes.servicio.RestauranteResumen;
import servicio.FactoriaServicios;

@Api
@Path("restaurantes")
public class RestaurantesControladorRest {

	private IServicioRestaurante servicio = FactoriaServicios.getServicio(IServicioRestaurante.class);

	@Context
	private UriInfo uriInfo;

	@Context
	private SecurityContext securityContext;

	// Ejemplo: http://localhost:8080/api/restaurantes/1

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	@ApiOperation(value = "Consulta un restaurante", notes = "Retorna un restaurante utilizando su id", response = Restaurante.class)
	@ApiResponses(value = { @ApiResponse(code = HttpServletResponse.SC_OK, message = ""),
			@ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Restaurante no encontrado") })
	public Response getRestaurante(@ApiParam(value = "id del restaurante", required = true) @PathParam("id") String id)
			throws Exception {

		return Response.status(Response.Status.OK).entity(servicio.getRestaurante(id)).build();
	}

	// Utiliza un fichero de prueba disponible en el proyecto
	// curl -i -X POST -H "Content-type: application/json" -d @test-files/1.json
	// http://localhost:8080/api/restaurantes/
	// No hay que agregar ningún fragmento al path

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(Restaurante restaurante) throws Exception {

		String id = servicio.create(restaurante);

		URI nuevaURL = uriInfo.getAbsolutePathBuilder().path(id).build();

		return Response.created(nuevaURL).build();
	}

	// Utiliza un fichero de prueba disponible en el proyecto
	// curl -i -X PUT -H "Content-type: application/json" -d @test-files/1.json
	// http://localhost:8080/api/restaurantes/1

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Secured(AvailableRoles.GESTOR)
	public Response update(@PathParam("id") String id, Restaurante restaurante) throws Exception {

		if (!id.equals(restaurante.getId()))
			throw new IllegalArgumentException("El identificador no coincide: " + id);

		servicio.update(restaurante);

		return Response.status(Response.Status.NO_CONTENT).build();

	}

	// curl -i -X DELETE http://localhost:8080/api/restaurantes/1

	@DELETE
	@Path("/{id}")
	@Secured(AvailableRoles.GESTOR)
	public Response removeRestaurante(@PathParam("id") String id) throws Exception {

		servicio.removeRestaurante(id);

		return Response.status(Response.Status.NO_CONTENT).build();

	}

	// Si no se especifica la cabecera "Accept", se retorna en el primer formato
	// (XML)
	// curl http://localhost:8080/api/restaurantes

	// curl -H "Accept: application/json" http://localhost:8080/api/restaurantes

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getListadoRestaurantes() throws Exception {

		List<RestauranteResumen> resultado = servicio.getListadoRestaurantes();

		LinkedList<ResumenExtendido> extendido = new LinkedList<Listado.ResumenExtendido>();

		for (RestauranteResumen restauranteResumen : resultado) {

			ResumenExtendido resumenExtendido = new ResumenExtendido();

			resumenExtendido.setResumen(restauranteResumen);

			// URL

			String id = restauranteResumen.getId();
			URI nuevaURL = uriInfo.getAbsolutePathBuilder().path(id).build();

			resumenExtendido.setUrl(nuevaURL.toString()); // string

			extendido.add(resumenExtendido);

		}

		// Una lista no es un documento válido en XML

		// Creamos un documento con un envoltorio

		Listado listado = new Listado();

		listado.setRestaurante(extendido);

		return Response.ok(listado).build();

	}

	// curl -i -X POST --data "nombre=plato1&precio=7.9"
	// http://localhost:8080/api/restaurantes/1/platos

	@POST
	@Path("/{id}/platos")
	@Secured(AvailableRoles.GESTOR)
	public Response addPlato(@PathParam("id") String id, @FormParam("nombre") String nombre,
			@FormParam("precio") float precio) throws Exception {

		Plato plato = new Plato();
		plato.setNombre(nombre);
		plato.setPrecio(precio);
		plato.setDisponible(true);

		servicio.addPlato(id, plato);

		return Response.status(Response.Status.NO_CONTENT).build();

	}

	// curl -i -X DELETE http://localhost:8080/api/restaurantes/1/platos/plato1

	@DELETE
	@Path("/{id}/platos/{plato}")
	@Secured(AvailableRoles.GESTOR)
	public Response removePlato(@PathParam("id") String id, @PathParam("plato") String plato) throws Exception {

		servicio.removePlato(id, plato);

		return Response.status(Response.Status.NO_CONTENT).build();

	}
	
	//TODO: 
	// Utiliza un fichero de prueba disponible en el proyecto
	// curl -i -X PUT -H "Content-type: application/json" -d @test-files/2.json http://localhost:8080/api/restaurantes/1/platos

	@PUT
	@Path("/{id}/platos")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updatePlato(@PathParam("id") String id, Plato plato) throws Exception {

		servicio.updatePlato(id, plato);

		return Response.status(Response.Status.NO_CONTENT).build();

	}

}
