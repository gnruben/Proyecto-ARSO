FROM tomcat:9.0.58-jdk11
WORKDIR /usr/local/tomcat/webapps/
COPY target/restaurantes-rest.war ROOT.war

EXPOSE 8080

ENV OPINIONES_API_URL=http://opiniones:5000/api/opiniones
ENV RESTAURANTE_API_URL=http://localhost:8080/api/
ENV MONGODB_URL=mongodb://arso:arso@ac-fpzdwce-shard-00-00.akucgja.mongodb.net:27017,ac-fpzdwce-shard-00-01.akucgja.mongodb.net:27017,ac-fpzdwce-shard-00-02.akucgja.mongodb.net:27017/?ssl=true&replicaSet=atlas-fdh11t-shard-0&authSource=admin&retryWrites=true&w=majority
ENV _URL_AMQP=amqps://hygkafmz:bg8J4YyAUyaY-m2GFtYEcuNhX49cAjpW@rattlesnake.rmq.cloudamqp.com/hygkafmz
 
CMD ["catalina.sh", "run"]
