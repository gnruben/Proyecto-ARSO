using Repositorio;
using MongoDB.Driver;
using System.Collections.Generic;
using System.Linq;
using Opiniones.Modelo;

namespace Opiniones.Repositorio
{
    public class RepositorioOpinionesMongoDB : Repositorio<Opinion, string>
    {
        private readonly IMongoCollection<Opinion> opiniones;

        public RepositorioOpinionesMongoDB()
        {
            //Environment.SetEnvironmentVariable("MONGODB_URL", "mongodb://arso:arso@ac-fpzdwce-shard-00-00.akucgja.mongodb.net:27017,ac-fpzdwce-shard-00-01.akucgja.mongodb.net:27017,ac-fpzdwce-shard-00-02.akucgja.mongodb.net:27017/?ssl=true&replicaSet=atlas-fdh11t-shard-0&authSource=admin&retryWrites=true&w=majority");
            //string url="amqps://hygkafmz:bg8J4YyAUyaY-m2GFtYEcuNhX49cAjpW@rattlesnake.rmq.cloudamqp.com/hygkafmz";
            string url = Environment.GetEnvironmentVariable("MONGODB_URL");

            var client = new MongoClient(url);
            var database = client.GetDatabase("arso");
            opiniones = database.GetCollection<Opinion>("opiniones");

        }

        public string Add(Opinion entity)
        {
            opiniones.InsertOne(entity);
            return entity.Id;
            //throw new NotImplementedException();
        }

        public void Delete(Opinion entity)
        {
            opiniones.DeleteOne(opinion => opinion.Id == entity.Id);

            //throw new NotImplementedException();
        }

        public List<Opinion> GetAll()
        {
            return opiniones.Find(_ => true).ToList();

            //throw new NotImplementedException();
        }

        public Opinion GetById(string id)
        {
            return opiniones.Find(opinion => opinion.Id == id).FirstOrDefault();
            //throw new NotImplementedException();
        }

        public List<string> GetIds()
        {
            var all = opiniones.Find(_ => true).ToList();
            return all.Select(p => p.Id).ToList();
            //throw new NotImplementedException();
        }

        public void Update(Opinion entity)
        {
            opiniones.ReplaceOne(opinion => opinion.Id == entity.Id, entity);

            //throw new NotImplementedException();
        }
    }

}