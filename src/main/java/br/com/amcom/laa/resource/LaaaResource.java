package br.com.amcom.laa.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/laaa")
public class LaaaResource {

    @GET
    @Path("/health")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getHealth() {
        // TODO: testar a conexão com o servidor de log se não estiver rodando retorntar 200 ok
        return Response.serverError().build();
    }
}
