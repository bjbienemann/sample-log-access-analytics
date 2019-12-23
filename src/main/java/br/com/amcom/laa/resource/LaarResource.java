package br.com.amcom.laa.resource;

import br.com.amcom.laa.domain.Log;
import br.com.amcom.laa.exception.ElasticSearchClientException;
import br.com.amcom.laa.service.InjestService;
import org.elasticsearch.action.index.IndexResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/laar")
public class LaarResource {

    @POST
    @Path("/ingest")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postIngest(Log log) {
        InjestService injestService = new InjestService();
        try {
            IndexResponse response = injestService.newIndexRequest(log);
            return Response.status(response.status().getStatus()).build();
        } catch (ElasticSearchClientException e) {
            return e.getResponse();
        }
    }
}
