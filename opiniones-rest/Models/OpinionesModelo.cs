using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
using System;
using System.Collections.Generic;

namespace Opiniones.Modelo{
    public class Opinion{
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        public string Id{get;set;}

        public Opinion(string nombreRecurso){
            this.NombreRecurso=nombreRecurso;
        }

        public string NombreRecurso{get;set;}
        

        public List<Valoracion> Valoraciones=new List<Valoracion>();

        public int numeroValoraciones(){
            return Valoraciones.Count;
        }

        public void addValoracion(string email, int calificacion, 
        string date, string comentario){
            Valoracion i=new Valoracion(email,date,calificacion,comentario);
            this.removeValoracion(email);
            Valoraciones.Add(i);

        }
        public void removeValoracion(string userEmail){
            Valoraciones.RemoveAll(x => x.userEmail==userEmail);
        }

        public float valoracionMedia(){
            //TODO:
            float resultado=0;
            foreach(Valoracion i in Valoraciones){
                resultado+=i.Calificacion;
            }
            if(Valoraciones.Count==0){
                return 0;
            }
            return resultado/Valoraciones.Count;
        }
        

    }
    public class Valoracion{

        public Valoracion(string userEmail,string fecha, int calificacion, string comentario){
            this.userEmail=userEmail;
            this.Fecha=fecha;
            this.Calificacion=calificacion;
            if(comentario==""){
                this.Comentario=null;
            }else{
                this.Comentario=comentario;
            }

        }
        public string userEmail{get;set;}

        public string Fecha{get;set;}

        public int Calificacion{get;set;}

        public string Comentario{get;set;}

    }


    public class ResumenOpinion{
        
        public string id{get;set;}
        public float calificacionMedia{get;set;}
        public int numeroValoraciones{get;set;}

    }
}