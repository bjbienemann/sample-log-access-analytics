package br.com.amcom.laa.resource;

import br.com.amcom.laa.domain.Log;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/laar")
public class LaarResource {

    @POST
    @Path("/ingest")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postIngest(Log log) {
        return Response.serverError().build();
    }
}
