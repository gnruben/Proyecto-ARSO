using Opiniones.Modelo;
using Opiniones.Servicio;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;

namespace Opiniones.Controllers
{
    [Route("api/opiniones")]
    [ApiController]
    public class OpinionesController : ControllerBase
    {
        private readonly IServicioOpiniones servicio;

        public OpinionesController(IServicioOpiniones so)
        {
            this.servicio = so;

        }
        /*
        curl -X 'GET' \
          'http://localhost:5000/api/opiniones/idOpinion' \
          -H 'accept: text/plain'
                */

        [HttpGet("{id}", Name = "GetOpinion")]
        public ActionResult<Opinion> Get(string id)
        {
            var entidad = servicio.GetOpinion(id);
            if (entidad == null)
            {
                return NotFound();
            }

            return entidad;
        }


        /*curl -X 'POST' \
          'https://localhost:5000/api/opiniones/recursos' \
          -H 'accept: text/plain' \
          -H 'Content-Type: multipart/form-data' \
          -F _id= '\
          -F 'nombreRecurso= */

        [HttpPost()]

        public ActionResult Create([FromForm] string nombreRecurso)
        {
            string idOpinion = servicio.RegistrarOpinion(nombreRecurso);
            //Environment.SetEnvironmentVariable("OPINIONES_API_URL","http://localhost:5000/api/opiniones/" );
            //string url="amqps://hygkafmz:bg8J4YyAUyaY-m2GFtYEcuNhX49cAjpW@rattlesnake.rmq.cloudamqp.com/hygkafmz";
            string url = Environment.GetEnvironmentVariable("OPINIONES_API_URL");
            return Created(url + idOpinion, idOpinion);
        }

        [HttpGet("{id}/valoraciones")]
        public ActionResult<List<Valoracion>> GetAllValoraciones(string id)
        {
            var entidad = servicio.GetAllValoraciones(id);
            if (entidad == null)
            {
                return NotFound();
            }

            return entidad;

        }

        [HttpDelete("{id}")]
        public IActionResult DeleteOpinion(string id)
        {
            var op = servicio.GetOpinion(id);
            if (op == null)
            {
                return NotFound();
            }
            servicio.RemoveOpinion(id);

            return NoContent();
        }

        [HttpPost("{id}/valoraciones")]
        public IActionResult Valorar(string id, [FromForm] string date, [FromForm] string email,
        [FromForm] int calificacion, [FromForm] string comentario)
        {
            Opinion op = servicio.GetOpinion(id);
            if (op == null)
            {
                return NotFound();
            }
            servicio.Valorar(id, email, calificacion, date, comentario);

            return NoContent();
        }

    }
}