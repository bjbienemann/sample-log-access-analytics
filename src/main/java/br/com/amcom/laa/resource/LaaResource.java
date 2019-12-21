package br.com.amcom.laa.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/laa/metrics")
public class LaaResource {

    @GET
    @Path("/top")
    @Produces("application/json")
    public Response getTopAccess() {
        return Response.serverError().build();
    }

    @GET
    @Path("/less")
    @Produces("application/json")
    public Response getLessAccess() {
        return Response.serverError().build();
    }

    @GET
    @Path("/minute")
    @Produces("application/json")
    public Response getMinuteMoreAccess() {
        return Response.serverError().build();
    }
}
