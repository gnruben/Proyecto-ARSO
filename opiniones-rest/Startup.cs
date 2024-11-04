using System;
using Opiniones.Modelo;
using Opiniones.Repositorio;
using Opiniones.Servicio;

using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Filters;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.OpenApi.Models;
using Repositorio;


namespace OpinionesRest
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration =configuration;

        }

        public IConfiguration Configuration{get;}

        public void ConfigureServices(IServiceCollection services)
        {
            

            services.AddSingleton<Repositorio<Opinion,String>, RepositorioOpinionesMongoDB>();

            services.AddSingleton<IServicioOpiniones,ServicioOpiniones>();

            services.AddControllers(options =>
            {
                options.Filters.Add(typeof(ManejadorGlobalErrores));
            });

            services.AddControllers();

            services.AddSwaggerGen(c =>
            {
                c.SwaggerDoc("v1", new OpenApiInfo{Title="OpinionesRest", Version ="v1"});
            });
        }

        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
                        if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
                app.UseSwagger();
                app.UseSwaggerUI(c => c.SwaggerEndpoint("/swagger/v1/swagger.json", "OpinionesRest v1"));
            }

            // app.UseHttpsRedirection();

            app.UseRouting();

            app.UseAuthorization();

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapControllers();
            });
        }
    }
}


public class ManejadorGlobalErrores : ExceptionFilterAttribute
{
    public override void OnException(ExceptionContext context)
    {
        base.OnException(context);

        if (context.Exception is ArgumentException) {
            context.Result = new BadRequestResult();
        }         
    }
}