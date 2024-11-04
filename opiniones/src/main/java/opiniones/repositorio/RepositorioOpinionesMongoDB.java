package opiniones.repositorio;

import java.util.LinkedList;
import java.util.List;

import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

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

import opiniones.modelo.Opinion;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import repositorio.RepositorioString;
import utils.Utils;

public class RepositorioOpinionesMongoDB implements RepositorioString<Opinion> {

	MongoCollection<Opinion> collection;

	public RepositorioOpinionesMongoDB() {

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
		

		collection = database.getCollection("Opiniones", Opinion.class);
	}

	@Override
	public String add(Opinion opinion) throws RepositorioException {

		String id = Utils.createId();
		opinion.setId(id);
		collection.insertOne(opinion);

		return id;
	}

	@Override
	public void update(Opinion opinion) throws RepositorioException, EntidadNoEncontrada {

		Bson filter = Filters.eq("_id", opinion.getId());

		if (collection.find(filter) == null) {
			throw new EntidadNoEncontrada(opinion.getId() + " no existe en el repositorio");
		}
		collection.replaceOne(filter, opinion);
	}

	@Override
	public void delete(Opinion opinion) throws RepositorioException, EntidadNoEncontrada {

		Bson filter = Filters.eq("_id", opinion.getId());

		if (collection.find(filter) == null) {
			throw new EntidadNoEncontrada(opinion.getId() + " no existe en el repositorio");
		}
		collection.deleteOne(filter);
	}

	@Override
	public Opinion getById(String id) throws RepositorioException, EntidadNoEncontrada {
		
		Bson filter = Filters.eq("_id", id);
		Opinion opinion = (Opinion) collection.find(filter).first();

		if (opinion == null) {
			throw new EntidadNoEncontrada(id + " no existe en el repositorio");
		}
		System.out.println(opinion.toString());
		return opinion;
	}

	@Override
	public List<Opinion> getAll() throws RepositorioException {
		
		List<Opinion> opiniones= new LinkedList<Opinion>();
		FindIterable<Opinion> iterable = collection.find();
		
		iterable.forEach(opiniones::add);
		
		return opiniones;
	}

	@Override
	public List<String> getIds() throws RepositorioException {
		
		List<String> ids = new LinkedList<String>();
		FindIterable<Opinion> iterable = collection.find();
		
		for (Opinion r : iterable) {
			ids.add(r.getId());
		}
		
		return ids;
	}

}
