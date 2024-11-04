package restaurantes.servicio;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;
import restaurantes.eventos.EventoNuevaValoracion;

import restaurantes.modelo.Plato;
import restaurantes.modelo.Restaurante;
import restaurantes.modelo.SitioTuristico;

public class ServicioRestaurante implements IServicioRestaurante {

	private Repositorio<Restaurante, String> repositorio = FactoriaRepositorios.getRepositorio(Restaurante.class);
	//private String uri = "amqps://hygkafmz:bg8J4YyAUyaY-m2GFtYEcuNhX49cAjpW@rattlesnake.rmq.cloudamqp.com/hygkafmz";
	private String uri = System.getenv("_URL_AMQP");
	
	private ConnectionFactory factory;
	private Channel channel;
	
	//
	IMockServicioOpiniones mockOpiniones;
	
	
	public ServicioRestaurante() {
		mockOpiniones=new ServicioOpinionesMock();
		
		// registro del consumidor de eventos

		try {
			factory = new ConnectionFactory();

			// TODO uri
			factory.setUri(uri);

			Connection connection = factory.newConnection();

			channel = connection.createChannel();

			/** Declaración de la cola y enlace con el exchange **/

			final String exchangeName = "evento.nueva.valoracion";
			final String queueName = "valoracion-queue";
			final String bindingKey = "arso";

			boolean durable = true;
			boolean exclusive = false;
			boolean autodelete = false;
			Map<String, Object> properties = null; // sin propiedades
			channel.queueDeclare(queueName, durable, exclusive, autodelete, properties);

			channel.queueBind(queueName, exchangeName, bindingKey);

			/** Configuración del consumidor **/

			boolean autoAck = false;

			String etiquetaConsumidor = "servicio-valoraciones";

			// Consumidor push

			channel.basicConsume(queueName, autoAck, etiquetaConsumidor,

					new DefaultConsumer(channel) {
						@Override
						public void handleDelivery(String consumerTag, Envelope envelope,
								AMQP.BasicProperties properties, byte[] body) throws IOException {

							long deliveryTag = envelope.getDeliveryTag();

							String contenido = new String(body);
							System.out.println(contenido);

							ObjectMapper mapper = new ObjectMapper(); // Jackson

							EventoNuevaValoracion evento = mapper.readValue(contenido, EventoNuevaValoracion.class);

							// Procesamos el evento

							ResumenOpinion resumen = evento.getResumen();

							String idOpinion = resumen.getId();

							// Hay que añadir los cambios que se producen en el restaurante
							
							Restaurante r;
							try {
								//buscamos el restaurante con la id de opinión igual a la del evento
								List<Restaurante> lr=repositorio.getAll();	
								r =lr.stream().filter(restaurante ->restaurante.getIdOpiniones()==idOpinion ).findFirst().get();
								System.out.println(r);
								if(r!=null) {
									r.setNumValoraciones(resumen.getNumeroValoraciones());
									r.setCalificacionMedia(resumen.getCalificacionMedia());
									repositorio.update(r);
								}
							} catch (RepositorioException e) {
								e.printStackTrace();
							} catch (EntidadNoEncontrada e) {
								e.printStackTrace();
							}

							// Confirma el procesamiento
							System.out.println("confirmado");
							channel.basicAck(deliveryTag, false);
						}
					});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public String create(Restaurante restaurante) throws RepositorioException {
		repositorio.add(restaurante);
		String idOpinion;
		try {
			idOpinion = mockOpiniones.registrarRestaurante(restaurante.getNombre());
			restaurante.setIdOpiniones(idOpinion);
			repositorio.update(restaurante);
		} catch (IOException | InterruptedException | EntidadNoEncontrada e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return restaurante.getId();
	}

	@Override
	public void update(Restaurante restaurante) throws RepositorioException, EntidadNoEncontrada {

		repositorio.update(restaurante);
	}

	@Override
	public Restaurante getRestaurante(String id) throws RepositorioException, EntidadNoEncontrada {

		return repositorio.getById(id);
	}

	@Override
	public void removeRestaurante(String id) throws RepositorioException, EntidadNoEncontrada {

		Restaurante restaurante = repositorio.getById(id);
		repositorio.delete(restaurante);
	}

	@Override
	public List<RestauranteResumen> getListadoRestaurantes() throws RepositorioException {

		List<RestauranteResumen> resultado = new LinkedList<>();

		for (String id : repositorio.getIds()) {
			try {
				Restaurante restaurante = getRestaurante(id);
				RestauranteResumen resumen = new RestauranteResumen();
				resumen.setId(restaurante.getId());
				resumen.setNombre(restaurante.getNombre());
				resumen.setCoordenadas(restaurante.getCoordenadas());
				resultado.add(resumen);

			} catch (Exception e) {
				// No debe suceder
				e.printStackTrace(); // para depurar
			}
		}

		return resultado;
	}

	@Override
	public List<SitioTuristico> getSitiosTuristicos(String id) throws RepositorioException, EntidadNoEncontrada {

		Restaurante restaurante = repositorio.getById(id);
		String codigopostal = restaurante.getCoordenadas();

		// String[] parts = coordenadas.split("-");
		// String lat = parts[0];
		// String lng = parts[1];

		List<String> sitios = new LinkedList<String>();
		List<SitioTuristico> sitiosTuristicos = new LinkedList<SitioTuristico>();

		try {
			sitios = utils.Tarea1y2.getSitiosPorCodigoPostal(codigopostal);

			for (String sitio : sitios) {

				System.out.println(sitio);

				String nombre = utils.Tarea1y2.getNombre(sitio);
				String resumen = utils.Tarea1y2.getResumen(sitio);
				String imagen = utils.Tarea1y2.getImagenes(sitio);
				List<String> categorias = utils.Tarea1y2.getCategorias(sitio);
				List<String> enlaces = utils.Tarea1y2.getEnlaces(sitio);

				SitioTuristico sitioTuristico = new SitioTuristico();
				sitioTuristico.setNombre(nombre);
				sitioTuristico.setResumen(resumen);
				sitioTuristico.setImagen(imagen);
				sitioTuristico.setCategorias(categorias);
				sitioTuristico.setEnlaces(enlaces);
				sitioTuristico.setWikipedia(sitio);

				sitiosTuristicos.add(sitioTuristico);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sitiosTuristicos;
	}

	@Override
	public void setSitiosTuristicos(String id, LinkedList<SitioTuristico> lista)
			throws RepositorioException, EntidadNoEncontrada {

		Restaurante restaurante = repositorio.getById(id);
		restaurante.setSitios(lista);

		update(restaurante);
	}

	@Override
	public void addPlato(String id, Plato plato) throws RepositorioException, EntidadNoEncontrada {

		// TODO: que se hace si el nombre del plato ya existe
		Restaurante restaurante = repositorio.getById(id);

		if (!restaurante.existePlato(plato.getNombre())) {
			restaurante.addPlato(plato);
			update(restaurante);
		} else {
			System.out.println("El nombre del plato ya existe, utilize otro distinto");
		}
	}

	@Override
	public void removePlato(String id, String nombrePlato) throws RepositorioException, EntidadNoEncontrada {

		Restaurante restaurante = repositorio.getById(id);

		if (restaurante.existePlato(nombrePlato)) {
			restaurante.removePlato(nombrePlato);
			update(restaurante);
		} else {
			throw new EntidadNoEncontrada(nombrePlato + " no existe en el restaurante");
		}

	}

	// TODO:

	@Override
	public void updatePlato(String id, Plato plato) throws RepositorioException, EntidadNoEncontrada {

		Restaurante restaurante = repositorio.getById(id);

		if (restaurante.existePlato(plato.getNombre())) {
			restaurante.removePlato(plato.getNombre());
			restaurante.addPlato(plato);
			update(restaurante);
		} else {
			throw new EntidadNoEncontrada(plato.getNombre() + " no existe en el restaurante");
		}

	}

}
