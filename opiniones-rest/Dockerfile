FROM mcr.microsoft.com/dotnet/sdk:6.0
WORKDIR /app

COPY . .
# RUN dotnet dev-certs https
RUN dotnet build

ENV ASPNETCORE_KESTREL__ENDPOINTS__HTTP__URL=http://+:5000
ENV OPINIONES_API_URL=http://opiniones:5000/api/opiniones
ENV RESTAURANTE_API_URL=http://restaurantes:8080/api/
ENV MONGODB_URL=mongodb://arso:arso@ac-fpzdwce-shard-00-00.akucgja.mongodb.net:27017,ac-fpzdwce-shard-00-01.akucgja.mongodb.net:27017,ac-fpzdwce-shard-00-02.akucgja.mongodb.net:27017/?ssl=true&replicaSet=atlas-fdh11t-shard-0&authSource=admin&retryWrites=true&w=majority
ENV _URL_AMQP=amqps://hygkafmz:bg8J4YyAUyaY-m2GFtYEcuNhX49cAjpW@rattlesnake.rmq.cloudamqp.com/hygkafmz

CMD ["dotnet", "run"]
EXPOSE 5000

