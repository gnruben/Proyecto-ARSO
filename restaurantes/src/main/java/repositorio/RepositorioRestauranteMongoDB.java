package repositorio;

import java.util.LinkedList;
import java.util.List;

import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.codecs.configuration.CodecRegistries;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import restaurantes.modelo.Restaurante;
import utils.Utils;

public class RepositorioRestauranteMongoDB implements RepositorioString<Restaurante> {

	MongoCollection<Restaurante> collection;

	public RepositorioRestauranteMongoDB() {

		CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
		CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				CodecRegistries.fromProviders(pojoCodecProvider));
		
		//String connection = "mongodb://arso:arso@ac-fpzdwce-shard-00-00.akucgja.mongodb.net:27017,ac-fpzdwce-shard-00-01.akucgja.mongodb.net:27017,ac-fpzdwce-shard-00-02.akucgja.mongodb.net:27017/?ssl=true&replicaSet=atlas-fdh11t-shard-0&authSource=admin&retryWrites=true&w=majority";
		String connection = System.getenv("MONGODB_URL");
		
		ConnectionString connectionString = new ConnectionString(connection);
		MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connectionString)
				.serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).build();
		MongoClient mongoClient = MongoClients.create(settings);
		MongoDatabase database = mongoClient.getDatabase("arso").withCodecRegistry(pojoCodecRegistry);
		;

		collection = database.getCollection("Restaurantes", Restaurante.class);
	}

	@Override
	public String add(Restaurante restaurante) throws RepositorioException {

		String id = Utils.createId();
		restaurante.setId(id);
		collection.insertOne(restaurante);

		return id;
	}

	@Override
	public void update(Restaurante restaurante) throws RepositorioException, EntidadNoEncontrada {

		Bson filter = Filters.eq("_id", restaurante.getId());

		if (collection.find(filter) == null) {
			throw new EntidadNoEncontrada(restaurante.getId() + " no existe en el repositorio");
		}
		collection.replaceOne(filter, restaurante);
	}

	@Override
	public void delete(Restaurante restaurante) throws RepositorioException, EntidadNoEncontrada {

		Bson filter = Filters.eq("_id", restaurante.getId());

		if (collection.find(filter) == null) {
			throw new EntidadNoEncontrada(restaurante.getId() + " no existe en el repositorio");
		}
		collection.deleteOne(filter);
	}

	@Override
	public Restaurante getById(String id) throws RepositorioException, EntidadNoEncontrada {
		
		Bson filter = Filters.eq("_id", id);
		Restaurante restaurante = (Restaurante) collection.find(filter).first();

		if (restaurante == null) {
			throw new EntidadNoEncontrada(id + " no existe en el repositorio");
		}
		return restaurante;
	}

	@Override
	public List<Restaurante> getAll() throws RepositorioException {
		
		List<Restaurante> restaurantes = new LinkedList<Restaurante>();
		FindIterable<Restaurante> iterable = collection.find();
		
		iterable.forEach(restaurantes::add);
		
		return restaurantes;
	}

	@Override
	public List<String> getIds() throws RepositorioException {
		
		List<String> ids = new LinkedList<String>();
		FindIterable<Restaurante> iterable = collection.find();
		
		for (Restaurante r : iterable) {
			ids.add(r.getId());
		}
		
		return ids;
	}

}
