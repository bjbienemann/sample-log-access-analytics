package br.com.amcom.laa.resource;

import br.com.amcom.laa.exception.ElasticSearchClientException;
import br.com.amcom.laa.service.HealthService;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.cluster.health.ClusterHealthStatus;

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
        HealthService healthService = new HealthService();
        try {
            ClusterHealthResponse response = healthService.getHealth();
            if (ClusterHealthStatus.RED.equals(response.getStatus())) {
                return Response.serverError().build();
            }

            return Response.ok().build();
        } catch (ElasticSearchClientException e) {
            return e.getResponse();
        }
    }
}
