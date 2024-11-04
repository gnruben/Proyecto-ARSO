using System;
using System.Collections.Generic;
using Opiniones.Modelo;
using Repositorio;
using RabbitMQ.Client;
using Opiniones.Eventos;
using System.Text;
using System.Text.Json;


/*
- Registrar un recurso (con un nombre) para ser valorado (crea una opinión).
 Esta operación retorna un identificador de la opinión
  que se utilizará en el resto de operaciones.

- Añadir una valoración sobre un recurso.
 Si un usuario registra una segunda valoración para el mismo
recurso, ésta reemplazará a la primera.

- Recuperar la opinión de un recurso.

- Eliminar una opinión del servicio (elimina una opinión y sus valoraciones).
*/

namespace Opiniones.Servicio
{
    //OpinionResumen?
    public interface IServicioOpiniones
    {
        string RegistrarOpinion(string nombreRecurso);



        void Valorar(string idOpinion, string email, int calificacion,
        string date, string comentario);//Valoracion v);


        Opinion GetOpinion(string id);

        List<Valoracion> GetAllValoraciones(string idOpinion);



        void RemoveOpinion(string id);

    }

    public class ServicioOpiniones : IServicioOpiniones
    {

        private Repositorio<Opinion, string> repositorioOpiniones;

        private readonly IConnection _connection;
        private readonly IModel _channel;


        public ServicioOpiniones(Repositorio<Opinion, string> ro)
        {
            //Environment.SetEnvironmentVariable("_URL_AMQP", "amqps://hygkafmz:bg8J4YyAUyaY-m2GFtYEcuNhX49cAjpW@rattlesnake.rmq.cloudamqp.com/hygkafmz");
            //string url="amqps://hygkafmz:bg8J4YyAUyaY-m2GFtYEcuNhX49cAjpW@rattlesnake.rmq.cloudamqp.com/hygkafmz";
            string url = Environment.GetEnvironmentVariable("_URL_AMQP");
            repositorioOpiniones = ro;

            var uri = new Uri(url);
            var factory = new ConnectionFactory()
            {
                Uri = uri
            };
            _connection = factory.CreateConnection();
            _channel = _connection.CreateModel();

        }



        public Opinion GetOpinion(string id)
        {
            return repositorioOpiniones.GetById(id);
        }



        public string RegistrarOpinion(string nombreRecurso)
        {
            Opinion op = new Opinion(nombreRecurso);
            string _id = repositorioOpiniones.Add(op);
            return _id;
        }

        public void RemoveOpinion(string id)
        {
            Opinion op = repositorioOpiniones.GetById(id);
            repositorioOpiniones.Delete(op);
        }

        public void Valorar(string idOpinion, string email, int calificacion,
        string date, string comentario)
        {
            Opinion op = repositorioOpiniones.GetById(idOpinion);



            op.addValoracion(email, calificacion, date, comentario);
            repositorioOpiniones.Update(op);

            //RabbitMQ
            var evento = new EventoNuevaValoracion();
            // se debe obtener el id del recurso y el resumen de opinion.
            ResumenOpinion res = new ResumenOpinion();
            res.id = op.Id;
            res.calificacionMedia = op.valoracionMedia();
            res.numeroValoraciones = op.numeroValoraciones();

            evento.resumen = res;
            //evento.idRestaurante=op.idRestaurante;

            var eventoJson = JsonSerializer.Serialize<EventoNuevaValoracion>(evento);
            var body = Encoding.UTF8.GetBytes(eventoJson);
            _channel.BasicPublish(exchange: "evento.nueva.valoracion",
            routingKey: "arso",
            basicProperties: null,
            body: body);
        }

        public List<Valoracion> GetAllValoraciones(string idOpinion)
        {
            return repositorioOpiniones.GetById(idOpinion).Valoraciones;
        }
    }

}
